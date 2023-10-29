package za.simshezi.foodiemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import za.simshezi.foodiemanagement.adapter.RecipeAdapter;
import za.simshezi.foodiemanagement.api.RecipeAPI;
import za.simshezi.foodiemanagement.model.ShopModel;

public class EducationActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ScrollView layoutRecipe;
    private RecyclerView ingredients;
    private TextView tvNoRecipe, tvRecipeName, tvRecipeArea, tvRecipeCategory, tvRecipeInstructions;
    private SearchView searchView;
    private RecipeAPI api;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        layoutRecipe = findViewById(R.id.layoutRecipe);
        searchView = findViewById(R.id.searchViewRecipe);
        tvNoRecipe = findViewById(R.id.tvNoRecipe);
        tvRecipeArea = findViewById(R.id.tvRecipeArea);
        tvRecipeCategory = findViewById(R.id.tvRecipeCategory);
        tvRecipeName = findViewById(R.id.tvRecipeName);
        tvRecipeInstructions = findViewById(R.id.tvRecipeInstruction);
        ingredients = findViewById(R.id.lsRecipeIngredients);
        api = new RecipeAPI();
        adapter = new RecipeAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ingredients.setAdapter(adapter);
        ingredients.setLayoutManager(layoutManager);
        build();
    }

    private void build() {
        ShopModel shop = (ShopModel) getIntent().getSerializableExtra("shop");
        if (shop != null) {
            searchView.setOnQueryTextListener(EducationActivity.this);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        api.searchByName(s, recipeModel -> runOnUiThread(() -> {
            if (recipeModel == null) {
                layoutRecipe.setVisibility(View.GONE);
                tvNoRecipe.setText(String.format("%s not found", s));
            } else {
                tvRecipeName.setText(recipeModel.getMealName());
                tvRecipeArea.setText(recipeModel.getArea());
                tvRecipeCategory.setText(recipeModel.getCategory());
                tvRecipeInstructions.setText(recipeModel.getInstructions());
                adapter.update(recipeModel.getIngredients());
                tvNoRecipe.setVisibility(View.GONE);
                layoutRecipe.setVisibility(View.VISIBLE);
            }
        }));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    public void onSearchClicked(View view) {
        searchView.setIconified(false);
    }

    public void onDoneClicked(View view) {
        ShopModel shop = (ShopModel) getIntent().getSerializableExtra("shop");
        if (shop != null) {
            shop.setDest(ProfileFragment.PROFILE_REQ);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("shop", shop);
            startActivity(intent);
        }
    }
}