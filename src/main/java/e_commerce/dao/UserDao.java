package e_commerce.dao;

import e_commerce.dto.request.FormLogin;
import e_commerce.dto.request.FormRegister;
import e_commerce.dto.response.UserResponse;
import e_commerce.model.User;
import e_commerce.ultil.ConnectDB;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class UserDao {

    public List<UserResponse> findAll(int page, String name) {
        List<UserResponse> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call findAll_user(?,?)}");
            callSt.setInt(1, page);
            callSt.setString(2, name);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                UserResponse user = new UserResponse();
                if (user.getRole() != 1) {
                    user.setId(rs.getLong("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getInt("role_id"));
                    user.setStatus(rs.getBoolean("status"));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return list;
    }


    public void register(FormRegister formRegister) {

        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement  callSt = conn.prepareCall("{call create_user(?,?,?)}");
            callSt.setString(1, formRegister.getUsername());
            callSt.setString(2, formRegister.getEmail());
            callSt.setString(3, formRegister.getPassword());
            // thực thi câu lệnh sql
            callSt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }

    }


    public UserResponse findById(Long id) {
        UserResponse userResponse = null;
        Connection conn = null;
        try {
            // mỏ kết nối
            conn = ConnectDB.getConnection();

            // chuẩn bị câu lệnh
            CallableStatement callSt = conn.prepareCall("{call find_user(?)}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                userResponse = new UserResponse();
                userResponse.setId(rs.getLong("id"));
                userResponse.setUsername(rs.getString("username"));
                userResponse.setEmail(rs.getString("email"));
                userResponse.setRole(rs.getInt("role"));
                userResponse.setStatus(rs.getBoolean("status"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return userResponse;
    }


    public UserResponse login(FormLogin formLogin) {
        UserResponse userResponse = null;
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();

            CallableStatement callSt = conn.prepareCall("{call login(?,?)}");
            callSt.setString(1, formLogin.getUsername());
            callSt.setString(2, formLogin.getPassword());
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                userResponse = new UserResponse();
                userResponse.setId(rs.getLong("id"));
                userResponse.setUsername(rs.getString("username"));
                userResponse.setEmail(rs.getString("email"));
                userResponse.setRole(rs.getInt("role_id"));
                userResponse.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return userResponse;
    }

    public void changeStatus(Long id) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call change_status_user(?)}");
            callSt.setLong(1, id);
            callSt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }

    public boolean checkUsername(String username) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call check_username(?)}");
            callSt.setString(1, username);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return true;
    }

    public boolean checkEmail(String email) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call check_email(?)}");
            callSt.setString(1, email);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return true;
    }
}
