package za.simshezi.foodiemanagement.model;

import java.io.Serializable;
import java.util.List;

public class ProductModel implements Serializable {
    private String name;
    private String description;
    private float price;
    private byte[] image;
    private List<IngredientModel> ingredients;

    public ProductModel(String name, String description, float price, byte[] image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public ProductModel(String name, String description, String price) {
        this.name = name;
        this.description = description;
        this.price = Float.parseFloat(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<IngredientModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientModel> ingredients) {
        this.ingredients = ingredients;
    }
}
