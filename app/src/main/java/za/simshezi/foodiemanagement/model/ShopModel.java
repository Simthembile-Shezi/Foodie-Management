package za.simshezi.foodiemanagement.model;

import java.io.Serializable;
import java.util.List;

public class ShopModel implements Serializable {
    private String name;
    private String id;
    private String email;
    private String cellphone;
    private float rating;
    private boolean status;
    private byte[] image;

    public ShopModel(String name, String email, String cellphone, float rating, boolean status, byte[] image) {
        this.name = name;
        this.email = email;
        this.cellphone = cellphone;
        this.rating = rating;
        this.status = status;
        this.image = image;
    }

    public ShopModel(String name, String id, String cellphone, boolean status, byte[] image) {
        this.name = name;
        this.id = id;
        this.cellphone = cellphone;
        this.status = status;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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

