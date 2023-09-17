package za.simshezi.foodiemanagement.model;

public class OrderModel {
    private String orderId;
    private String customerName;
    private String paymentType;
    private String collectionTime;
    private int items;
    private float price;

    public OrderModel(String orderId, String customerName, String paymentType, String collectionTime, int items, float price) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.paymentType = paymentType;
        this.collectionTime = collectionTime;
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

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
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
