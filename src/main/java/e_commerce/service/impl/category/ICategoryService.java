package e_commerce.service.impl.category;

import e_commerce.model.Category;
import e_commerce.service.IGenericService;
import org.springframework.stereotype.Service;
@Service
public interface ICategoryService extends IGenericService<Category, Long> {
    void changeStatus(Long id);
}
