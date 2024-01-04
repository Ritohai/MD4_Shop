package e_commerce.service.impl.Order;

import e_commerce.dto.response.CartResponse;
import e_commerce.dto.response.OrDetailResponse;
import e_commerce.model.Cart;
import e_commerce.model.OrderDetail;
import e_commerce.model.Orders;

import java.util.List;

public interface IOrderService {
    List<Orders> findAll(Long id, int page);
    List<Orders> getAll( int page,String name);

    List<OrDetailResponse> getOrderDetails(Long id);
    void create(List<CartResponse> carts , Orders orders);
    void cancel(Long id);
    void delivery(Long id);
    void confirm(Long id);

    double totalOrder(Long id);

}
