package e_commerce.dao;


import e_commerce.model.Category;
import e_commerce.ultil.ConnectDB;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Repository
public class CategoryDao implements IGenericDao<Category,Long>{

    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        Connection connection =null;
        try {
            connection = ConnectDB.getConnection();
            CallableStatement callSt = connection.prepareCall("{call get_category()}");
            ResultSet resultSet = callSt.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("category_name"));
                category.setStatus(resultSet.getBoolean("status"));
                categories.add(category);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(connection);
        }
        return categories;
    }

    @Override
    public List<Category> findAll(int page,String name) {
        List<Category> categories = new ArrayList<>();
        Connection connection =null;
        try {
            connection = ConnectDB.getConnection();
            CallableStatement callSt = connection.prepareCall("{call findAll_category(?,?)}");
            callSt.setInt(1,page);
            callSt.setString(2,name);
            ResultSet resultSet = callSt.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("category_name"));
                category.setStatus(resultSet.getBoolean("status"));
                categories.add(category);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(connection);
        }
        return categories;
    }

    @Override
    public Category findById(Long id) {
        Connection connection =null;
        Category category =null;
        try {
            connection = ConnectDB.getConnection();
            CallableStatement callSt = connection.prepareCall("{call find_category(?)}");
            callSt.setLong(1, id);
            ResultSet resultSet = callSt.executeQuery();
            while(resultSet.next()) {
                category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("category_name"));
                category.setStatus(resultSet.getBoolean("status"));
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally{
            ConnectDB.closeConnection(connection);
        }
        return category;
    }

    @Override
    public void save(Category category) {
        Connection conn = null;
        try {
            if (category.getId() == null) {
                conn = ConnectDB.getConnection();
                CallableStatement callSt = conn.prepareCall("{call create_category(?)}");
                callSt.setString(1, category.getName());
                callSt.executeUpdate();
            }else {
                conn = ConnectDB.getConnection();
                CallableStatement callSt = conn.prepareCall("{call edit_category(?,?)}");
                callSt.setLong(1, category.getId());
                callSt.setString(2, category.getName());
                callSt.executeUpdate();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
    }

    @Override
    public void delete(Long id) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
//            CallableStatement callSt1 = conn.prepareCall("{call  deleteProductByCatalog(?)}");
//            callSt1.setLong(1, id);
//            callSt1.executeUpdate();
            CallableStatement callSt = conn.prepareCall("{call delete_category(?)}");
            callSt.setLong(1, id);
            callSt.executeUpdate();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
    }

    public void changeStatus(Long id){
        Connection conn = null;
       try {
           conn = ConnectDB.getConnection();
           CallableStatement callSt = conn.prepareCall("{call change_status_category(?)}");
           callSt.setLong(1, id);
           callSt.executeUpdate();
       }catch (Exception e) {
           throw new RuntimeException(e);
       }finally {
           ConnectDB.closeConnection(conn);
       }
    }

}
