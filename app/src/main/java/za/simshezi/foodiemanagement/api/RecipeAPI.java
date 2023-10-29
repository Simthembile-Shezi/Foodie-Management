package za.simshezi.foodiemanagement.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import za.simshezi.foodiemanagement.model.IngredientModel;
import za.simshezi.foodiemanagement.model.RecipeModel;

public class RecipeAPI {
    private final OkHttpClient client;

    public RecipeAPI() {
        client = new OkHttpClient();
    }

    public void searchByName(String name, OnSuccessListener<RecipeModel> callback) {
        Request request = new Request.Builder()
                .url("https://www.themealdb.com/api/json/v1/1/search.php?s=" + name)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onSuccess(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();

                try {
                    JsonNode root = objectMapper.readTree(responseData);
                    JsonNode mealsNode = root.path("meals");

                    // Assuming there's only one meal in the JSON data
                    JsonNode meal = mealsNode.get(0);

                    String mealName = meal.path("strMeal").asText();
                    String category = meal.path("strCategory").asText();
                    String area = meal.path("strArea").asText();
                    String instructions = meal.path("strInstructions").asText();
                    List<IngredientModel> ingredients = new ArrayList<>();
                    int count = 1;
                    while(true){
                        String ingredient = meal.path("strIngredient"+count).asText();
                        String measure = meal.path("strMeasure"+count).asText();

                        if(ingredient == null || ingredient.isEmpty()){
                            break;
                        }else {
                            ingredients.add(new IngredientModel(ingredient, measure));
                        }
                        ++count;
                    }
                    RecipeModel recipe = new RecipeModel(mealName, category, area, instructions, ingredients);
                    callback.onSuccess(recipe);
                } catch (Exception e) {
                    callback.onSuccess(null);
                }
            }
        });
    }
}
