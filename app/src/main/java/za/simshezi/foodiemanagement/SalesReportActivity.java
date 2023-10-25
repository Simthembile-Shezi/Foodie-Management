package za.simshezi.foodiemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;

import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.api.JavaAPI;
import za.simshezi.foodiemanagement.model.OrderModel;
import za.simshezi.foodiemanagement.model.ShopModel;

public class SalesReportActivity extends AppCompatActivity {
    private ShopModel shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_report);
        TextView tvIncome = findViewById(R.id.tvSalesReportIncome);
        shop = (ShopModel) getIntent().getSerializableExtra("shop");

        FirebaseAPI.getInstance().getOrdersReviews(shop.getId(), queryDocumentSnapshots -> {
            Double income = 0.0;
            if(queryDocumentSnapshots != null){
                for(DocumentSnapshot document : queryDocumentSnapshots){
                    OrderModel order = document.toObject(OrderModel.class);
                    if(order != null && order.getStatus().equals("Collected")){
                        income += order.getPrice();
                    }
                }
            }
            tvIncome.setText(String.format("R %s", JavaAPI.formatDouble(income)));
        });
    }

    public void onExitClicked(View view) {
        shop.setDest(ProfileFragment.PROFILE_REQ);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("shop", shop);
        startActivity(intent);
    }
}