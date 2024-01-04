package e_commerce.service.impl.cart;

import e_commerce.dto.response.CartResponse;
import e_commerce.model.Cart;

import java.util.List;

public interface ICartService {
    List<CartResponse> findAll(int page,Long user_id);

    void update(Long id,int quantity);
    void addCart(Long user_id, Long product_id);
    void deleteCart(Long id);
    void  clear(Long id);
    double total(Long id);
}
