package za.simshezi.foodiemanagement.model;

public class OrderReviewModel {
    private String orderId;
    private String customerName;
    private String paymentType;
    private String date;
    private String review;
    private int rating;
    private int items;
    private float price;

    public OrderReviewModel(String orderId, String customerName, String paymentType, String date, String review, int rating, int items, float price) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.paymentType = paymentType;
        this.date = date;
        this.review = review;
        this.rating = rating;
        this.items = items;
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
