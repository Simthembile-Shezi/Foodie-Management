package za.simshezi.foodiemanagement.model;

import java.util.List;

public class RecipeModel {
    private String mealName;
    private String category;
    private String area;
    private String instructions;
    private List<String> ingredients;
    private List<String> measures;

    public RecipeModel(String mealName, String category, String area, String instructions, List<String> ingredients, List<String> measures) {
        this.mealName = mealName;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.measures = measures;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getMeasures() {
        return measures;
    }

    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }
}
