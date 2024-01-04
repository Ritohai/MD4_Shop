package e_commerce.dao;

import e_commerce.dto.response.CartResponse;
import e_commerce.model.Cart;
import e_commerce.model.OrderDetail;
import e_commerce.model.Orders;
import e_commerce.model.Status;
import e_commerce.ultil.ConnectDB;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDao {
    public List<Orders> getAll(int page, String name) {
        List<Orders> orders = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            CallableStatement callSt = connection.prepareCall("{call getAll_order(?,?)}");
            callSt.setInt(1, page);
            callSt.setString(2, name);
            ResultSet resultSet = callSt.executeQuery();
            while (resultSet.next()) {
                Orders order = new Orders();
                order.setId(resultSet.getLong("id"));
                order.setUser_id(resultSet.getLong("user_id"));
                order.setPhone(resultSet.getString("phone"));
                order.setAddress(resultSet.getString("adress"));
                order.setOther(resultSet.getString("other"));
                order.setCreateDate(resultSet.getDate("create_date"));
                order.setStatus(Status.valueOf(resultSet.getString("status")));
                orders.add(order);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return orders;
    }

    public List<Orders> findAll(Long user_id, int page) {
        List<Orders> orders = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            CallableStatement callSt = connection.prepareCall("{call findAll_order(?,?)}");
            callSt.setLong(1, page);
            callSt.setLong(2, user_id);
            ResultSet resultSet = callSt.executeQuery();
            while (resultSet.next()) {
                Orders order = new Orders();
                order.setId(resultSet.getLong("id"));
                order.setUser_id(resultSet.getLong("user_id"));
                order.setPhone(resultSet.getString("phone"));
                order.setAddress(resultSet.getString("adress"));
                order.setOther(resultSet.getString("other"));
                order.setCreateDate(resultSet.getDate("create_date"));
                order.setStatus(Status.valueOf(resultSet.getString("status")));
                orders.add(order);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return orders;
    }


    public Orders findById(Long id) {
        Connection connection = null;
        Orders orders = null;
        try {
            connection = ConnectDB.getConnection();
            CallableStatement callSt = connection.prepareCall("{call find_orderById(?,)}");
            callSt.setLong(1, id);
            ResultSet resultSet = callSt.executeQuery();
            while (resultSet.next()) {
                orders = new Orders();
                orders.setId(resultSet.getLong("id"));
                orders.setUser_id(resultSet.getLong("user_id"));
                orders.setPhone(resultSet.getString("phone"));
                orders.setAddress(resultSet.getString("adress"));
                orders.setOther(resultSet.getString("other"));
                orders.setCreateDate(resultSet.getDate("create_date"));
                orders.setStatus(Status.valueOf(resultSet.getString("status")));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return orders;
    }

    public void createOrder(List<CartResponse> carts, Orders orders) {
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            connection.setAutoCommit(false);

            CallableStatement callSt1 = connection.prepareCall("{call create_orders(?, ?,?,?,?,?)}");
            callSt1.setLong(1, orders.getUser_id());
            callSt1.setString(2, orders.getPhone());
            callSt1.setString(3, orders.getAddress());
            callSt1.setString(4, orders.getOther());
            callSt1.setString(5, String.valueOf(Status.PENDING));
            callSt1.registerOutParameter(6, Types.INTEGER);
            callSt1.executeUpdate();

            Long order_id = callSt1.getLong(6);

            for (CartResponse cart : carts) {
                try {
                    CallableStatement callSt2 = connection.prepareCall("{call create_order_detail(?,?,?,?) }");
                    callSt2.setLong(1, order_id);
                    callSt2.setLong(2, cart.getProduct_id());
                    callSt2.setDouble(3, cart.getPrice());
                    callSt2.setInt(4, cart.getQuantity());
                    callSt2.executeUpdate();
                    CallableStatement callSt3 = connection.prepareCall("{call update_stock(?,?) }");
                    callSt3.setLong(1, cart.getProduct_id());
                    callSt3.setInt(2, cart.getQuantity());
                    callSt3.executeUpdate();
                } catch (Exception e) {
                    connection.rollback();
                    throw new RuntimeException("Error while adding order detail", e);
                }
            }

            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace(); // Print specific error message
            throw new RuntimeException("Error while adding order", e);
        } finally {
            ConnectDB.closeConnection(connection);
        }
    }

    public void cancel(Long idOrder) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call change_status_order(?,?)}");
            callSt.setLong(1, idOrder);
            callSt.setString(2, String.valueOf(Status.CANCEL));
            callSt.executeUpdate();
            for (OrderDetail orderDetail : getOrderDetail(idOrder)) {
                CallableStatement callSt1 = conn.prepareCall("{call refund_stock(?,?)}");
                callSt1.setLong(1, orderDetail.getProductId());
                callSt1.setInt(2, orderDetail.getQuantity());
                callSt1.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }


    public List<OrderDetail> getOrderDetail(Long id) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            CallableStatement callSt = connection.prepareCall("{call find_order_detail(?)}");
            callSt.setLong(1, id);
            ResultSet resultSet = callSt.executeQuery();
            while (resultSet.next()) {
                OrderDetail order = new OrderDetail();
                order.setId(resultSet.getLong("id"));
                order.setOrderId(resultSet.getLong("order_id"));
                order.setPrice(resultSet.getDouble("price"));
                order.setQuantity(resultSet.getInt("quantity"));
                order.setProductId(resultSet.getLong("product_id"));
                orderDetails.add(order);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return orderDetails;
    }

    public void confirm(Long idOrder) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call change_status_order(?,?)}");
            callSt.setLong(1, idOrder);
            callSt.setString(2, String.valueOf(Status.CONFIRM));
            callSt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }


    public void delivery(Long idOrder) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call change_status_order(?,?)}");
            callSt.setLong(1, idOrder);
            callSt.setString(2, String.valueOf(Status.DELIVERY));
            callSt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }

    public double totalOrder(Long id) {
        double total = 0;
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call total_order(?)}");
            callSt.setLong(1, id);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total_order");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return total;
    }
}
