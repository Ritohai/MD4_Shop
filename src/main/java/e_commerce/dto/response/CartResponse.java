package e_commerce.dto.response;

public class CartResponse {
    private Long id;
    private Long user_id;
    private Long product_id;
    private String product_name;
    private String image;
    private double price;
    private int quantity;

    public CartResponse() {
    }

    public CartResponse(Long id, Long user_id,Long product_id, String product_name, String image,double price, int quantity) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
