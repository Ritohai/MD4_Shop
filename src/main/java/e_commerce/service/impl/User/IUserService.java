package e_commerce.service.impl.User;

import e_commerce.dto.request.FormLogin;
import e_commerce.dto.request.FormRegister;
import e_commerce.dto.response.UserResponse;

import java.util.List;

public interface IUserService {
    List<UserResponse> findAll(int page, String name);

    void register(FormRegister formRegister);

    void changePassword();

    UserResponse login(FormLogin formLogin);

    UserResponse findById(Long id);

    void lockAndUnlock(Long id);

    boolean checkUsername(String username);

    boolean checkEmail(String email);

}
