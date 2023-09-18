package za.simshezi.foodiemanagement.model;

import java.io.Serializable;
import java.util.List;

public class ShopModel implements Serializable {
    private String name;
    private String id;
    private String email;
    private String cellphone;
    private Double rating;
    private String status;
    private String address;
    private byte[] image;

    public ShopModel(String name, String email, String cellphone, Double rating, String status, byte[] image) {
        this.name = name;
        this.email = email;
        this.cellphone = cellphone;
        this.rating = rating;
        this.status = status;
        this.image = image;
    }

    public ShopModel(String name, String id, String cellphone, String status, byte[] image) {
        this.name = name;
        this.id = id;
        this.cellphone = cellphone;
        this.status = status;
        this.image = image;
    }

    public ShopModel(String id, String name, String email, String cellphone, String address, String status, Double rating) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cellphone = cellphone;
        this.rating = rating;
        this.status = status;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}

