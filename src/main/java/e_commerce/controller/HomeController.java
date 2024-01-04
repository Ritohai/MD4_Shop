package e_commerce.controller;

import e_commerce.dto.request.FormLogin;
import e_commerce.dto.request.FormRegister;
import e_commerce.dto.response.UserResponse;
import e_commerce.model.Orders;
import e_commerce.service.impl.Order.IOrderService;
import e_commerce.service.impl.cart.ICartService;
import e_commerce.service.impl.category.ICategoryService;
import e_commerce.service.impl.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IOrderService orderService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "page/index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/category")
    public ModelAndView adminCategory(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String name, Model model) {
        model.addAttribute("name", name);
        return new ModelAndView("admin/category", "categories", categoryService.findAll(page, name));
    }

    @GetMapping("/admin/product")
    public ModelAndView adminProduct(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String name, Model model) {
        model.addAttribute("name", name);
        return new ModelAndView("admin/product", "products", productService.findAll(page, name));
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("page/login", "login", new FormLogin());
    }

    @GetMapping("/signup")
    public ModelAndView register() {
        return new ModelAndView("page/signup", "register", new FormRegister());
    }

    @GetMapping("/contact")
    public String getContact() {
        return "page/contact";
    }

    @GetMapping("/product")
    public String getProduct(Model model,@RequestParam(defaultValue = "") String name
            ,@RequestParam(defaultValue = "1") int page
            ,@RequestParam(defaultValue = "0") Long cate_id) {
        model.addAttribute("name", name);
        model.addAttribute("products", productService.findAllShop(page, name,cate_id));
        model.addAttribute("categories", categoryService.getAll());
        return "page/product";
    }

    @GetMapping("/single/{id}")
    public String getSingle(@PathVariable("id") Long id,Model model) {
        model.addAttribute("product",productService.findById(id));
        return "page/single";
    }

    @GetMapping("/order")
    public String getOrder(Model model,@RequestParam(defaultValue = "1") int page,HttpSession session ) {
        UserResponse userResponse = (UserResponse) session.getAttribute("currentUser");
        if (userResponse != null) {
            model.addAttribute("orders", orderService.findAll(userResponse.getId(), page));
        }
        return "page/order";
    }

    @GetMapping("/cart")
    public ModelAndView getCart(@RequestParam(defaultValue = "1") int page, HttpSession session,Model model) {
        UserResponse userResponse = (UserResponse) session.getAttribute("currentUser");
        if (userResponse == null){
            return new ModelAndView("page/cart", "message","You need to log in to view your cart");
        }else {
            model.addAttribute("total", cartService.total(userResponse.getId()));
            return new ModelAndView("page/cart", "carts", cartService.findAll(page, userResponse.getId()));
        }

    }

    @GetMapping("/checkout")
    public String getCheckout(Model model,HttpSession session,@RequestParam(defaultValue = "1") int page) {
        UserResponse userResponse = (UserResponse) session.getAttribute("currentUser");
        model.addAttribute("orders", new Orders());
        model.addAttribute("carts", cartService.findAll(page, userResponse.getId()));
        model.addAttribute("total", cartService.total(userResponse.getId()));
        return "page/checkout";
    }

}
