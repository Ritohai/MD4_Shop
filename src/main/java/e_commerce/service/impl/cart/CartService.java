package e_commerce.service.impl.cart;

import e_commerce.dao.CartDao;
import e_commerce.dao.ProductDao;
import e_commerce.dto.response.CartResponse;
import e_commerce.model.Cart;
import e_commerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CartService implements ICartService{
    @Autowired
    private CartDao cartDao;
    @Autowired
    private ProductDao productDao;
    @Override
    public List<CartResponse> findAll(int page,Long user_id) {
        List<Cart> carts = cartDao.getAll(page,user_id);
        List<CartResponse> cartResponses = new ArrayList<>();
        for (Cart c:carts) {
            Product product = productDao.findById(c.getProduct_id());
            CartResponse cartResponse = new CartResponse(c.getId(),c.getUser_id(),product.getId(),product.getName(),product.getImage(),product.getPrice(),c.getQuantity());
            cartResponses.add(cartResponse);
        }
        return cartResponses;
    }

    @Override
    public void update(Long id, int quantity) {
        cartDao.update(id, quantity);
    }

    @Override
    public void addCart(Long user_id, Long product_id) {
        cartDao.save(user_id, product_id);
    }

    @Override
    public void deleteCart(Long id) {
        cartDao.delete(id);
    }

    @Override
    public void clear(Long id) {
        cartDao.clear(id);
    }

    @Override
    public double total(Long id) {
        return cartDao.totalCart(id);
    }
}
