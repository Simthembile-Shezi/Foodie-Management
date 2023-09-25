package za.simshezi.foodiemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import za.simshezi.foodiemanagement.adapter.IngredientAdapter;
import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.api.ImagesAPI;
import za.simshezi.foodiemanagement.model.IngredientModel;
import za.simshezi.foodiemanagement.model.ProductModel;
import za.simshezi.foodiemanagement.model.ShopModel;

public class EditProductActivity extends AppCompatActivity {

    private RecyclerView lstIngredients;
    private EditText edProductPrice, edProductDescription, edIngredientName, edIngredientPrice;
    private IngredientModel ingredient;
    private ProductModel product;
    private ShopModel shop;
    private IngredientAdapter adapter;
    private FirebaseAPI api;
    private ArrayList<IngredientModel> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        lstIngredients = findViewById(R.id.lstAddIngredients);
        ImageView imgProduct = findViewById(R.id.imgEditProduct);
        TextView tvProductName = findViewById(R.id.tvEditProductName);
        edProductPrice = findViewById(R.id.edEditProductPrice);
        edProductDescription = findViewById(R.id.edEditProductDescription);
        edIngredientName = findViewById(R.id.edAddIngredientName);
        edIngredientPrice = findViewById(R.id.edAddIngredientPrice);
        shop = (ShopModel) getIntent().getSerializableExtra("shop");
        product = shop.getProduct();
        tvProductName.setText(product.getName());
        edProductPrice.setText(String.format("%s", product.getPrice()));
        edProductDescription.setText(product.getDescription());
        byte[] image = product.getImage();
        if (image != null) {
            imgProduct.setImageBitmap(ImagesAPI.convertToBitmap(image));
        } else {
            imgProduct.setImageResource(R.drawable.baseline_restaurant_menu_24);
        }
        build();
    }

    private void build() {
        ingredient = new IngredientModel();
        ingredients = new ArrayList<>();
        api = FirebaseAPI.getInstance();
        api.getIngredients(product.getShopId(), product.getId(), queryDocumentSnapshots -> {
            if (queryDocumentSnapshots != null) {
                for (DocumentSnapshot document : queryDocumentSnapshots) {
                    IngredientModel ingredient = document.toObject(IngredientModel.class);
                    if (ingredient != null) {
                        ingredient.setId(document.getId());
                        ingredients.add(ingredient);
                    }
                    if (ingredients.size() == queryDocumentSnapshots.size()) {
                        update();
                    }
                }
            } else {
                update();
            }
        });
    }

    private void update() {
        adapter = new IngredientAdapter(ingredients, model -> {
            ingredient = (IngredientModel) model;       //IngredientModel is sub child of Serializable
            edIngredientName.setText(ingredient.getName());
            edIngredientName.setEnabled(false);
            edIngredientPrice.setText(String.format("%s", ingredient.getPrice()));
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        lstIngredients.setAdapter(adapter);
        lstIngredients.setLayoutManager(layoutManager);
    }

    public void onConfirmClicked(View view) {
        String name = edIngredientName.getText().toString().trim();
        String price = edIngredientPrice.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter name of the ingredient", Toast.LENGTH_SHORT).show();
        } else if (price.isEmpty()) {
            Toast.makeText(this, "Enter price of the ingredient", Toast.LENGTH_SHORT).show();
        } else {
            ingredient.setName(name);
            ingredient.resetId(product.getId());
            ingredient.setPrice(Double.parseDouble(price));
            api.saveIngredient(product, ingredient, aBoolean -> {
                if (aBoolean) {
                    adapter.add(ingredient);
                    onAddClicked(view);
                } else {
                    Toast.makeText(this, "Ingredient was not saved", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onRemoveClicked(View view) {
       if(ingredient != null && ingredient.getId() != null){
           api.deleteIngredient(product, ingredient, aBoolean -> {
               if (aBoolean) {
                   adapter.remove(ingredient);
                   ingredient = new IngredientModel();
                   edIngredientName.setText("");
                   edIngredientPrice.setText("");
               } else {
                   Toast.makeText(this, ingredient.getName() + "was not deleted", Toast.LENGTH_SHORT).show();
               }
           });
       }else {
           Toast.makeText(this, "Select ingredient to delete", Toast.LENGTH_SHORT).show();
       }
    }

    public void onAddClicked(View view) {
        ingredient = new IngredientModel();
        edIngredientName.setEnabled(true);
        edIngredientName.setText("");
        edIngredientPrice.setText("");
    }

    public void onUpdateProductClicked(View view) {
        String description = edProductDescription.getText().toString().trim();
        String price = edProductPrice.getText().toString().trim();
        if (description.isEmpty()) {
            Toast.makeText(this, "Enter product description", Toast.LENGTH_SHORT).show();
        } else if (price.isEmpty()) {
            Toast.makeText(this, "Enter product price", Toast.LENGTH_SHORT).show();
        } else {
            product.setDescription(description);
            product.setPrice(Double.parseDouble(price));
            FirebaseAPI.getInstance().editProduct(product, (bool) -> {
                if (bool) {
                    Toast.makeText(this, "Product has been edited", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    shop.setDest(MenuFragment.REQ_MENU);
                    intent.putExtra("shop", shop);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Product was not saved, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}