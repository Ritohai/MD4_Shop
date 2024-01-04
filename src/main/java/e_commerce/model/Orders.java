package e_commerce.model;

import e_commerce.service.impl.Order.OrderService;
import e_commerce.service.impl.User.UserService;
import org.springframework.validation.Errors;

import java.util.Date;

public class Orders {
    private  Long id;
    private Long user_id;
    private Date createDate;
    private  String phone;
    private String address;
    private String other;
    private  Status status;

    public Orders() {
    }

    public Orders(Long id, Long user_id, Date createDate, String phone, String address, String other, Status status) {
        this.id = id;
        this.user_id = user_id;
        this.createDate = createDate;
        this.phone = phone;
        this.address = address;
        this.other = other;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    private boolean isValidPhone(String phone) {
        return phone.matches("0\\d{9}"); // Bắt đầu bằng số 0 và tiếp theo là 9 chữ số
    }

    public void checkValidate(Errors errors) {
        if (this.phone.trim().equals("")) {
            errors.rejectValue("phone", "phone.empty");
        } else if (this.address.trim().equals("")) {
            errors.rejectValue("address", "address.empty");
        } else if (!isValidPhone(this.phone)) {
            errors.rejectValue("phone", "phone.invalid");
        }
    }

}
