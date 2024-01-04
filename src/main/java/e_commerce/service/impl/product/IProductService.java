package e_commerce.service.impl.product;

import e_commerce.dto.request.ProductDto;
import e_commerce.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll(int page, String name);
    List<Product>  findAllShop(int page, String name,Long cate_id);
    Product findById(Long id);
    void save(ProductDto productDto);
    void delete(Long id);
}
