package za.simshezi.foodiemanagement.model;

import com.google.firebase.Timestamp;
import com.google.type.DateTime;

public class OrderModel {
    private String shopId;
    private String customer;
    private String payment;
    private Timestamp time;
    private Integer items;
    private Double price;

    public OrderModel() {
    }

    public OrderModel(String shopId, String customer, String payment, Timestamp time, Integer items, Double price) {
        this.shopId = shopId;
        this.customer = customer;
        this.payment = payment;
        this.time = time;
        this.items = items;
        this.price = price;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getItems() {
        return items;
    }

    public void setItems(Integer items) {
        this.items = items;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
