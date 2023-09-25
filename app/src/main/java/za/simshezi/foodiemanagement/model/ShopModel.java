package za.simshezi.foodiemanagement.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.List;

import za.simshezi.foodiemanagement.api.JavaAPI;

public class ShopModel implements Serializable {
    private String id;
    private String name;
    private String email;
    private String cellphone;
    private Double rating;
    private String status;
    private String address;
    private byte[] image;
    private int dest;
    private ProductModel product;
    private Order order;

    public ShopModel() {
    }

    public ShopModel(String id, String name, String email, String cellphone, Double rating, String status, String address, byte[] image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cellphone = cellphone;
        this.rating = rating;
        this.status = status;
        this.address = address;
        this.image = image;
    }

    public ShopModel(String name, String email, String cellphone, String address, byte[] image) {
        this.name = name;
        this.email = email;
        this.cellphone = cellphone;
        this.address = address;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getDest() {
        return dest;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = new Order(order);
    }

    public static class Order implements Serializable {
        private String id;
        private String shopId;
        private String customer;
        private String cellphone;
        private String payment;
        private String time;
        private Integer items;
        private Double price;
        private String status;

        public Order(OrderModel order) {
            this.id = order.getId();
            this.shopId = order.getShopId();
            this.customer = order.getCustomer();
            this.cellphone = order.getCellphone();
            this.payment = order.getPayment();
            this.time = JavaAPI.getTime(order.getTime());
            this.items = order.getItems();
            this.price = order.getPrice();
            this.status = order.getStatus();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

