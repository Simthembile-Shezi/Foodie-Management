package za.simshezi.foodiemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import za.simshezi.foodiemanagement.api.RecipeAPI;
import za.simshezi.foodiemanagement.model.ShopModel;

public class EducationActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener{
    private ConstraintLayout layoutRecipe;
    private TextView tvNoRecipe;
    private SearchView searchView;
    private RecipeAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        layoutRecipe = findViewById(R.id.layoutRecipe);
        searchView = findViewById(R.id.searchViewRecipe);
        tvNoRecipe = findViewById(R.id.tvNoRecipe);
        api = new RecipeAPI();
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
        api.searchByName(s, recipeModel -> {
            if(recipeModel == null){
                layoutRecipe.setVisibility(View.GONE);
                tvNoRecipe.setText(String.format("%s not found", s));
            }else {
                tvNoRecipe.setVisibility(View.GONE);
                layoutRecipe.setVisibility(View.VISIBLE);
            }
        });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}