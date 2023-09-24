package za.simshezi.foodiemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    private TextView tvProductName;
    private ImageView imgProduct;
    private EditText edProductPrice, edProductDescription, edIngredientName, edIngredientPrice;
    private ShopModel shop;
    private ProductModel product;
    private IngredientAdapter adapter;
    private FirebaseAPI api;
    private ArrayList<IngredientModel> ingredients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        lstIngredients = findViewById(R.id.lstAddIngredients);
        imgProduct = findViewById(R.id.imgEditProduct);
        tvProductName = findViewById(R.id.tvEditProductName);
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
        if(image != null){
            imgProduct.setImageBitmap(ImagesAPI.convertToBitmap(image));
        }else {
            imgProduct.setImageResource(R.drawable.baseline_restaurant_menu_24);
        }
        build();
    }

    private void build() {
        ingredients = new ArrayList<>();
        api = FirebaseAPI.getInstance();
        api.getIngredients(product.getShopId(), product.getId(), queryDocumentSnapshots -> {
            if(queryDocumentSnapshots != null){
                for(DocumentSnapshot document: queryDocumentSnapshots){
                    IngredientModel ingredient = document.toObject(IngredientModel.class);
                    if(ingredient != null){
                        ingredient.setIngredientId(document.getId());
                        ingredients.add(ingredient);
                    }
                    if(ingredients.size() == queryDocumentSnapshots.size()){
                        update();
                    }
                }
            }else {
                update();
            }
        });
    }

    private void update() {
        adapter = new IngredientAdapter(ingredients, model -> {
            IngredientModel ingredient = (IngredientModel) model;       //IngredientModel is sub child of Serializable
            edIngredientName.setText(ingredient.getName());
            edIngredientPrice.setText(String.format("%s", ingredient.getPrice()));
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        lstIngredients.setAdapter(adapter);
        lstIngredients.setLayoutManager(layoutManager);
    }
    public void onConfirmClicked(View view) {
    }
    public void onRemoveClicked(View view) {
    }
    public void onAddClicked(View view) {
    }
    public void onUpdateProductClicked(View view) {
    }
}