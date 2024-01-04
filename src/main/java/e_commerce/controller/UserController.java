package e_commerce.controller;

import e_commerce.dto.request.FormLogin;
import e_commerce.dto.request.FormRegister;
import e_commerce.dto.response.UserResponse;
import e_commerce.service.impl.User.IUserService;
import e_commerce.service.impl.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String handleLogin(HttpSession session, @ModelAttribute("login") FormLogin formLogin, BindingResult errors, Model model) {
        // checkk validate
        formLogin.checkValidate(errors,userService);
        // kiểm tra bindingresult có nhận lỗi nào không
        if(errors.hasErrors()){
            return "page/login";
        }
        UserResponse userResponse = userService.login(formLogin);
        if(userResponse.getRole()==2){
            session.setAttribute("currentUser",userResponse);
            return "page/index";
        }else {
            return "admin/index";
        }

    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:/";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("register") FormRegister formRegister, BindingResult errors, Model model) {
        formRegister.checkValidateRegister(errors, userService);
        if (errors.hasErrors()) {
            return "page/signup";
        }
        userService.register(formRegister);
        return "redirect:/login";
    }


    @GetMapping("/admin/user")
    public ModelAndView getAllUser(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "") String name,Model model) {
        model.addAttribute("name",name);
        return new ModelAndView("admin/user","users",userService.findAll(page,name));
    }

    @GetMapping("/lock/{id}")
    public String getLock(@PathVariable Long id){
        userService.lockAndUnlock(id);
        return "redirect:/admin/user";
    }

}
