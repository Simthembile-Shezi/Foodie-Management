package za.simshezi.foodiemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import za.simshezi.foodiemanagement.adapter.OrderProductAdapter;
import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.api.JavaAPI;
import za.simshezi.foodiemanagement.model.IngredientModel;
import za.simshezi.foodiemanagement.model.ProductModel;
import za.simshezi.foodiemanagement.model.ShopModel;

public class ManageOrderActivity extends AppCompatActivity {
    private FirebaseAPI api;
    private ShopModel shop;
    private RecyclerView lstProduct;
    private TextView tvName, tvCellphone, tvPayment, tvETA, tvStatus, tvPrice;
    private Button btnStatus;
    private ArrayList<ProductModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_order);
        lstProduct = findViewById(R.id.lstOrderProducts);
        tvName = findViewById(R.id.tvOrderSummeryCustomerName);
        tvCellphone = findViewById(R.id.tvOrderSummeryCellphone);
        tvETA = findViewById(R.id.tvOrderSummeryTime);
        tvStatus = findViewById(R.id.tvOrderSummeryStatus);
        tvPayment = findViewById(R.id.tvOrderSummeryPayment);
        tvPrice = findViewById(R.id.tvOrderSummeryPrice);
        btnStatus = findViewById(R.id.btnManageOrderStatus);
        shop = (ShopModel) getIntent().getSerializableExtra("shop");
        api = FirebaseAPI.getInstance();
        build();
    }

    private void build() {
        String orderId = shop.getOrder().getId();
        tvName.setText(shop.getOrder().getCustomer());
        tvCellphone.setText(shop.getOrder().getCellphone());
        tvPayment.setText(shop.getOrder().getPayment());
        tvETA.setText(shop.getOrder().getTime());
        tvStatus.setText(shop.getOrder().getStatus());
        tvPrice.setText(String.format("R %s", JavaAPI.formatDouble(shop.getOrder().getPrice())));
        String status = shop.getOrder().getStatus();
        if(status.equals("Placed")){
            btnStatus.setText(R.string.completed);
        }else if(status.equals("Completed")){
            btnStatus.setText(R.string.collected);
        }
        list = new ArrayList<>();
        api.getOrderProducts(orderId, queryDocument -> {
            if(queryDocument != null){
                for(DocumentSnapshot document: queryDocument){
                    ProductModel product = document.toObject(ProductModel.class);
                    if(product != null) {
                        product.setId(document.getId());
                        product.setIngredients(new ArrayList<>());
                        api.getOrderIngredients(orderId, product.getId(), queryDocs -> {
                            if(queryDocs != null){
                                for (DocumentSnapshot doc : queryDocs) {
                                    IngredientModel ingredient = doc.toObject(IngredientModel.class);
                                    if(ingredient != null){
                                        ingredient.setId(doc.getId());
                                        product.addIngredients(ingredient);
                                    }
                                }
                            }
                            list.add(product);
                            if(list.size() == queryDocument.size()){
                                OrderProductAdapter adapter = new OrderProductAdapter(this, list);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                                lstProduct.setAdapter(adapter);
                                lstProduct.setLayoutManager(layoutManager);
                            }
                        });
                    }

                }

            }
        });
    }

    public void onOrderStatusUpdate(View view) {
        String orderId = shop.getOrder().getId();
        api.updateOrder(orderId, btnStatus.getText().toString() , aBoolean -> {
            if(aBoolean){
                Intent intent = new Intent(ManageOrderActivity.this, MainActivity.class);
                shop.setDest(HomeFragment.HOME_REQ);
                intent.putExtra("shop", shop);
                startActivity(intent);
            }
        });
    }
}