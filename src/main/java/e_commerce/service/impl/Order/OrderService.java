package e_commerce.service.impl.Order;

import e_commerce.dao.OrderDao;
import e_commerce.dao.ProductDao;
import e_commerce.dto.response.CartResponse;
import e_commerce.dto.response.OrDetailResponse;
import e_commerce.model.Cart;
import e_commerce.model.OrderDetail;
import e_commerce.model.Orders;
import e_commerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class OrderService implements IOrderService{
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Override
    public List<Orders> findAll(Long id, int page) {
        return orderDao.findAll(id, page);
    }

    @Override
    public List<Orders> getAll(int page, String name) {
        return orderDao.getAll(page, name);
    }

    @Override
    public List<OrDetailResponse> getOrderDetails(Long id) {
        List<OrDetailResponse> orDetailResponses = new ArrayList<>();
        for (OrderDetail o:orderDao.getOrderDetail(id)) {
            Product product = productDao.findById(o.getProductId());
            OrDetailResponse orDetailResponse = new OrDetailResponse(product.getName(),product.getImage(),o.getPrice(),o.getQuantity());
            orDetailResponses.add(orDetailResponse);
        }
        return orDetailResponses;
    }

    @Override
    public void create(List<CartResponse> carts, Orders orders) {
        orderDao.createOrder(carts, orders);
    }

    @Override
    public void cancel(Long id) {
        orderDao.cancel(id);
    }

    @Override
    public void delivery(Long id) {
        orderDao.delivery(id);
    }

    @Override
    public void confirm(Long id) {
        orderDao.confirm(id);
    }

    @Override
    public double totalOrder(Long id) {
        return orderDao.totalOrder(id);
    }
}
