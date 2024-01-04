package e_commerce.dto.request;

import e_commerce.service.impl.User.UserService;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private MultipartFile image;
    private int stock;
    private int category_id;
    private double price;
    private boolean status;

    public ProductDto() {
    }

    public ProductDto(Long id, String name, String description, MultipartFile image, int stock, int category_id, double price, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.stock = stock;
        this.category_id = category_id;
        this.price = price;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void checkValidate(Errors errors) {
        if(this.name.trim().equals("")){
            errors.rejectValue("name","name.empty");
        }else if(this.image.isEmpty()){
            errors.rejectValue("image","image.empty");
        }else if(this.price <= 0){
            errors.rejectValue("price","price.invalid");
        }else if(this.stock == 0){
            errors.rejectValue("stock","stock.invalid");
        }
    }
}
