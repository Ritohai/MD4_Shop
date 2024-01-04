package e_commerce.controller;

import e_commerce.dto.request.ProductDto;
import e_commerce.service.impl.category.ICategoryService;
import e_commerce.service.impl.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;
    @GetMapping("/create-product")
    public ModelAndView createProduct(Model model){
        model.addAttribute("categories",categoryService.getAll());
        return new ModelAndView("/admin/create","productDto" ,new ProductDto());
    }

    @PostMapping("/create-product")
    public String doCreate(@ModelAttribute("productDto") ProductDto productDto, BindingResult bindingResult){
        productDto.checkValidate(bindingResult);
        if (bindingResult.hasErrors()){
            return "/admin/create";
        }
        productService.save(productDto);
        return "redirect:/admin/product";
    }

    @GetMapping("/edit-product/{id}")
    public ModelAndView editProduct(@PathVariable Long id,Model model){
        model.addAttribute("categories", categoryService.getAll());
        return new ModelAndView("/admin/edit","productDto" ,productService.findById(id));
    }

    @PostMapping("/edit-product")
    public String doEdit(@ModelAttribute("productDto") ProductDto productDto){
        productService.save(productDto);
        return "redirect:/admin/product";
    }

    @GetMapping("delete-product/{id}")
    public String doDelete(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/admin/product";
    }
}
