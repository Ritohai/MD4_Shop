package e_commerce.dto.request;

import e_commerce.dao.UserDao;
import e_commerce.service.impl.User.UserService;
import org.springframework.validation.Errors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormRegister {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    public FormRegister() {
    }

    public FormRegister(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void checkValidateRegister(Errors errors, UserService userService){

        // kiểm tra trường username có để trống hay không
        if(this.username.trim().equals("")) {
            errors.rejectValue("username", "username.empty");
        }else if(this.username.length()<6){
            errors.rejectValue("password", "password.regex");
        } else if(!userService.checkUsername(this.username)){
            errors.rejectValue("username","username.duplicate");
        }else  if (this.email == null || this.email.trim().isEmpty()) {
            errors.rejectValue("email", "email.empty");
        } else if (!isValidEmail(this.email)) {
            errors.rejectValue("email", "email.invalid");
        }else if(!userService.checkEmail(this.email)){
            errors.rejectValue("email", "email.duplicate");
        }else if (this.password == null ){
            errors.rejectValue("password", "password.empty");
        }else if (!isValidPassword(this.password)) {
            errors.rejectValue("password", "password.invalid");
        }else if (!this.password.equals(this.confirmPassword)){
            errors.rejectValue("confirmPassword", "password.confirm");
        }
    }
}
