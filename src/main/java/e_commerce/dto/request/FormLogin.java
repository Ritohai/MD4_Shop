package e_commerce.dto.request;

import e_commerce.dao.UserDao;
import e_commerce.service.impl.User.UserService;
import org.springframework.validation.Errors;

public class FormLogin {
    private String username;
    private String password;

    public FormLogin() {
    }

    public FormLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void checkValidate(Errors errors, UserService userService) {
        // kiểm tra trường username có để trống hay không
        if(this.username.trim().equals("")){
            errors.rejectValue("username","username.empty");
        }else if(this.password.length()<8){
            errors.rejectValue("password","password.invalid");
        }else if(userService.login(this)==null){
            errors.rejectValue("password","account.invalid");
        }else if(userService.login(this).isStatus()==false){
            errors.rejectValue("password","account.locked");
        }
    }
}
