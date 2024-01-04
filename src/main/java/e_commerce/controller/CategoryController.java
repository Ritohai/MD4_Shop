package e_commerce.controller;

import e_commerce.model.Category;
import e_commerce.service.impl.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class CategoryController {
@Autowired
private CategoryService categoryService;

    @PostMapping("/create-category")
    public String createCategory(@ModelAttribute("category") Category catalog,
                                 @RequestParam("catalog_name") String catalog_name) {
        if (catalog_name == null){
            return "redirect:/admin/category";
        }
        catalog.setName(catalog_name);
        categoryService.save(catalog);
        return "redirect:/admin/category";
    }


    @PostMapping("edit-category")
    public String editCatalog(@RequestParam("id_edit") Long id
            ,@RequestParam("edit_catalog_name") String name){
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/admin/category";
    }

    @GetMapping("/category/status/{id}")
    public String changeStatus(@PathVariable Long id){
        categoryService.changeStatus(id);
        return "redirect:/admin/category";
    }
}
