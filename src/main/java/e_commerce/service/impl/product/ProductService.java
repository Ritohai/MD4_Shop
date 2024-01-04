package e_commerce.service.impl.product;

import e_commerce.dao.ProductDao;
import e_commerce.dto.request.ProductDto;
import e_commerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService implements IProductService{

    @Autowired
    private ProductDao productDao;
    @Override
    public List<Product> findAll(int page, String name) {
        return productDao.findAll(page, name);
    }

    @Override
    public List<Product> findAllShop(int page, String name, Long cate_id) {
        return productDao.findAllShop(page, name, cate_id);
    }

    @Override
    public Product findById(Long id) {
        return productDao.findById(id);
    }

    @Override
    public void save(ProductDto productDto) {
        productDao.save(productDto);
    }

    @Override
    public void delete(Long id) {
        productDao.delete(id);
    }
}
