package za.simshezi.foodiemanagement.model;

import com.google.firebase.Timestamp;

public class OrderReviewModel {
    private String shopId;
    private String customerName;
    private String paymentType;
    private Timestamp date;
    private String review;
    private Integer rating;
    private Integer items;
    private Double price;

    public OrderReviewModel(String shopId, String customerName, String paymentType, Timestamp date, String review, Integer rating, Integer items, Double price) {
        this.shopId = shopId;
        this.customerName = customerName;
        this.paymentType = paymentType;
        this.date = date;
        this.review = review;
        this.rating = rating;
        this.items = items;
        this.price = price;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
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
