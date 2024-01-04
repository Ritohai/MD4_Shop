package e_commerce.dao;

import e_commerce.dto.request.ProductDto;
import e_commerce.model.Product;
import e_commerce.ultil.ConnectDB;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductDao {
    private final String pathUpload="C:\\Users\\ADMIN\\Desktop\\E-Commerce-main\\src\\main\\webapp\\WEB-INF\\image\\";

    private String uploadFile(MultipartFile multipartFile){
        File file = new File(pathUpload);
        if(!file.exists()) {
            file.mkdir();
        }
        String  urlImg = multipartFile.getOriginalFilename();
        try{
            FileCopyUtils.copy(multipartFile.getBytes(), new File(pathUpload + urlImg));
        }catch (Exception e){
            throw new RuntimeException(e);

        }
        return urlImg;
    }
    public List<Product> findAll(int page, String name) {
        List<Product> products = new ArrayList<>();
        Connection conn =null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt =conn.prepareCall("{call findAll_product(?,?)}");
            callSt.setInt(1,page);
            callSt.setString(2,name);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("product_name"));
                product.setImage(rs.getString("img_url"));
                product.setDescription(rs.getString("description"));
                product.setStock(rs.getInt("stock"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setPrice(rs.getDouble("price"));
                product.setStatus(rs.getBoolean("status"));
                products.add(product);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
        return products;
    }
    public List<Product> findAllShop(int page, String name,Long cate_id) {
        List<Product> products = new ArrayList<>();
        Connection conn =null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt =conn.prepareCall("{call findAll_product_shop(?,?,?)}");
            callSt.setInt(1,page);
            callSt.setString(2,name);
            callSt.setLong(3,cate_id);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("product_name"));
                product.setImage(rs.getString("img_url"));
                product.setDescription(rs.getString("description"));
                product.setStock(rs.getInt("stock"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setPrice(rs.getDouble("price"));
                product.setStatus(rs.getBoolean("status"));
                products.add(product);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
        return products;
    }

    public Product findById(Long id) {
        Product product = null;
        Connection conn =null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call find_product(?)}");
            callSt.setLong(1, id);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("product_name"));
                product.setImage(rs.getString("img_url"));
                product.setDescription(rs.getString("description"));
                product.setStock(rs.getInt("stock"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setPrice(rs.getDouble("price"));
                product.setStatus(rs.getBoolean("status"));
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
        return product;
    }


    public void save(ProductDto productDto) {
        Connection conn = null;
        try {
            if(productDto.getId()==null){
                conn = ConnectDB.getConnection();
                CallableStatement callSt = conn.prepareCall("{call create_product(?,?,?,?,?,?)}");
                callSt.setString(1,productDto.getName());
                callSt.setString(2,uploadFile(productDto.getImage()));
                callSt.setString(3,productDto.getDescription());
                callSt.setInt(4, productDto.getStock());
                callSt.setDouble(5,productDto.getPrice());
                callSt.setInt(6,productDto.getCategory_id());

                callSt.executeUpdate();

            }else {
                String urlImg = null;
                if (productDto.getImage().isEmpty()){
                    Product product = findById(productDto.getId());
                    urlImg = product.getImage();
                }else {
                    urlImg = uploadFile(productDto.getImage());
                }
                conn = ConnectDB.getConnection();
                CallableStatement callSt = conn.prepareCall("{call edit_product(?,?,?,?,?,?,?)}");
                callSt.setLong(1,productDto.getId());
                callSt.setString(2,productDto.getName());
                callSt.setString(3,urlImg);
                callSt.setString(4,productDto.getDescription());
                callSt.setDouble(5,productDto.getPrice());
                callSt.setInt(6, productDto.getStock());
                callSt.setInt(7,productDto.getCategory_id());

                callSt.executeUpdate();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
    }

    public void delete(Long id) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call delete_product(?)}");
            callSt.setLong(1,id);
            callSt.executeUpdate();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(conn);
        }
    }
}
