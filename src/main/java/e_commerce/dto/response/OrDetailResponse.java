package e_commerce.dto.response;

public class OrDetailResponse {
    private String product_name;
    private String image;
    private double price;
    private int quantity;

    public OrDetailResponse(String product_name, String image, double price, int quantity) {
        this.product_name = product_name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public OrDetailResponse() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
