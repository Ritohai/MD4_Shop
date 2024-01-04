package e_commerce.controller;

import e_commerce.dto.response.CartResponse;
import e_commerce.dto.response.UserResponse;
import e_commerce.model.Orders;
import e_commerce.service.impl.Order.IOrderService;
import e_commerce.service.impl.cart.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ICartService cartService;

    @PostMapping("/create-order")
    public String createOrder(@ModelAttribute("orders") Orders orders, HttpSession session, @RequestParam(defaultValue = "1") int page,
                              BindingResult bindingResult, Model model) {
        orders.checkValidate(bindingResult);
        UserResponse userResponse = (UserResponse) session.getAttribute("currentUser");
        List<CartResponse> cartResponses = cartService.findAll(page, userResponse.getId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("carts", cartResponses);
            model.addAttribute("total", cartService.total(userResponse.getId()));
            return "page/checkout";
        }

        orders.setUser_id(userResponse.getId());
        orderService.create(cartResponses, orders);
        cartService.clear(userResponse.getId());

        return "redirect:/";
    }


    @GetMapping("/admin/orders")
    public String getAllOrders(Model model,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "") String name) {
        model.addAttribute("orders",orderService.getAll(page,name));
        return "admin/order";
    }

    @GetMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id){
        orderService.cancel(id);
        return "redirect:/orders";
    }

    @GetMapping("/delivery/{id}")
    public String delivery(@PathVariable Long id){
        orderService.delivery(id);
        return "redirect:/admin/orders";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(@PathVariable Long id){
        orderService.confirm(id);
        return "redirect:/admin/orders";
    }

    @GetMapping("/order/detail/{id}")
    public String getOrderDetails(@PathVariable Long id,Model model){
        model.addAttribute("orderDetails", orderService.getOrderDetails(id));
        model.addAttribute("total", orderService.totalOrder(id));
        return "page/order-detail";
    }
}
