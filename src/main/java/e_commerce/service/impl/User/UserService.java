package e_commerce.service.impl.User;

import e_commerce.dao.UserDao;
import e_commerce.dto.request.FormLogin;
import e_commerce.dto.request.FormRegister;
import e_commerce.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService implements IUserService{
    @Autowired
    private UserDao userDao;
    @Override
    public List<UserResponse> findAll(int page, String name) {
        return userDao.findAll(page, name);
    }

    @Override
    public void register(FormRegister formRegister) {
        userDao.register(formRegister);
    }

    @Override
    public void changePassword() {
    }

    @Override
    public UserResponse login(FormLogin formLogin) {
        return userDao.login(formLogin);
    }

    @Override
    public UserResponse findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public void lockAndUnlock(Long id) {
        userDao.changeStatus(id);
    }

    @Override
    public boolean checkUsername(String username) {
        return userDao.checkUsername(username);
    }

    @Override
    public boolean checkEmail(String email) {
        return userDao.checkEmail(email);
    }
}
