package e_commerce.controller;


import e_commerce.dto.response.UserResponse;
import e_commerce.model.Cart;
import e_commerce.service.impl.cart.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/")
public class CartController {
    @Autowired
    private ICartService cartService;

    @GetMapping("add-cart")
    @ResponseBody
    public Map<String, String> addCart(@RequestParam("id") Long id, HttpSession session){
        UserResponse userResponse = (UserResponse) session.getAttribute("currentUser");
        Map map = new HashMap<>();
        if (userResponse== null){
            map.put("error","You need to log in to add cart ");
        }else{
            cartService.addCart(userResponse.getId(),id);
            map.put("success", "Cart added successfully");
        }
        return map;
    }

    @GetMapping("remove-cart/{id}")
    public String removeCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return "redirect:/cart";
    }

    @GetMapping("clear")
    public String clearCart(HttpSession session) {
        UserResponse userResponse = (UserResponse) session.getAttribute("currentUser");
        cartService.clear(userResponse.getId());
        return "redirect:/cart";
    }

    @PostMapping("/update/{id}")
    public  String handleUpdate(HttpSession session,@PathVariable("id") Long id, @RequestParam("quantity") int quantity){
        cartService.update(id, quantity);
        return "redirect:/cart";
    }

}
