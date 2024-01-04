package e_commerce.dao;

import e_commerce.dto.response.CartResponse;
import e_commerce.model.Cart;
import e_commerce.model.Category;
import e_commerce.ultil.ConnectDB;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CartDao {
    public List<Cart> getAll(int page,Long id) {
        List<Cart> carts = new ArrayList<>();
        Connection connection =null;
        try {
            connection = ConnectDB.getConnection();
            CallableStatement callSt = connection.prepareCall("{call findAll_cart(?,?)}");
            callSt.setInt(1, page);
            callSt.setLong(2, id);
            ResultSet resultSet = callSt.executeQuery();
            while (resultSet.next()) {
                Cart cart = new Cart();
                cart.setId(resultSet.getLong("id"));
                cart.setUser_id(resultSet.getLong("user_id"));
                cart.setProduct_id(resultSet.getLong("product_id"));
                cart.setQuantity(resultSet.getInt("quantity"));
                carts.add(cart);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(connection);
        }
        return carts;
    }


    public Cart findCart(Long user_id,Long product_id) {
        Connection connection = null;
        Cart cart =null;
        try {
            connection = ConnectDB.getConnection();
            CallableStatement callSt = connection.prepareCall("{call find_cart(?,?)}");
            callSt.setLong(1, user_id);
            callSt.setLong(2, product_id);
            ResultSet resultSet = callSt.executeQuery();
            while(resultSet.next()) {
                cart = new Cart();
                cart.setId(resultSet.getLong("id"));
                cart.setUser_id(resultSet.getLong("user_id"));
                cart.setProduct_id(resultSet.getLong("product_id"));
                cart.setQuantity(resultSet.getInt("quantity"));
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally{
            ConnectDB.closeConnection(connection);
        }
        return cart;
    }

    public void save(Long user_id, Long product_id) {
        Connection conn = null;
        try {
            Cart cart = findCart(user_id, product_id);
            conn = ConnectDB.getConnection();
            CallableStatement callSt;
            if (cart == null) {
                callSt = conn.prepareCall("{call add_to_cart(?,?)}");
                callSt.setLong(1, user_id);
                callSt.setLong(2,product_id);
            }else {
                callSt = conn.prepareCall("{call update_cart(?,?)}");
                callSt.setLong(1, cart.getId());
                callSt.setInt(2, cart.getQuantity()+1);
            }
            callSt.executeUpdate();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
    }

    public void delete(Long id) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call delete_cart(?)}");
            callSt.setLong(1, id);
            callSt.executeUpdate();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
    }

    public void clear(Long id) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call clear(?)}");
            callSt.setLong(1, id);
            callSt.executeUpdate();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
    }

    public Cart findById(Long id) {
        Connection connection =null;
        Cart cart =null;
        try {
            connection = ConnectDB.getConnection();
            CallableStatement callSt = connection.prepareCall("{call findCartById(?)}");
            callSt.setLong(1, id);
            ResultSet resultSet = callSt.executeQuery();
            while(resultSet.next()) {
                cart = new Cart();
                cart.setId(resultSet.getLong("id"));
                cart.setUser_id(resultSet.getLong("user_id"));
                cart.setProduct_id(resultSet.getLong("product_id"));
                cart.setQuantity(resultSet.getInt("quantity"));
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally{
            ConnectDB.closeConnection(connection);
        }
        return cart;
    }
    public void update(Long idCart, int quantity){
        Cart cart = findById(idCart);
        Connection conn = null;
        try {
                conn = ConnectDB.getConnection();
                CallableStatement callSt = conn.prepareCall("{call update_cart(?,?)}");
                callSt.setLong(1, cart.getId());
                callSt.setInt(2, quantity);
                callSt.executeUpdate();

        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
    }

    public double totalCart(Long id){
        double total = 0;
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call total(?)}");
            callSt.setLong(1, id);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return total;
    }

}
