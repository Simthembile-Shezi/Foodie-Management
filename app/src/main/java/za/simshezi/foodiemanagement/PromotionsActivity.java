package za.simshezi.foodiemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import za.simshezi.foodiemanagement.adapter.IngredientAdapter;
import za.simshezi.foodiemanagement.adapter.PromotionAdapter;
import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.api.JavaAPI;
import za.simshezi.foodiemanagement.model.IngredientModel;
import za.simshezi.foodiemanagement.model.PromotionModel;
import za.simshezi.foodiemanagement.model.ShopModel;

public class PromotionsActivity extends AppCompatActivity {
    private ShopModel shop;
    private FirebaseAPI api;
    private PromotionAdapter adapter;
    private RecyclerView lstPromotions;
    private EditText edPromoCode, edDiscount, edMinimum,  edStart, edEnd;
    private long start, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);
        edDiscount = findViewById(R.id.edPromoDiscount);
        edPromoCode = findViewById(R.id.edPromoCode);
        edStart = findViewById(R.id.edStartDate);
        edEnd = findViewById(R.id.edEndDate);
        edMinimum = findViewById(R.id.edMinimumPrice);
        lstPromotions = findViewById(R.id.lstPromotions);
        shop = (ShopModel) getIntent().getSerializableExtra("shop");
        api = FirebaseAPI.getInstance();
        build();
    }

    private void build() {
        edEnd.setEnabled(false);
        edStart.setEnabled(false);
        adapter = new PromotionAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        lstPromotions.setAdapter(adapter);
        lstPromotions.setLayoutManager(layoutManager);
        api = FirebaseAPI.getInstance();
        api.getPromotions(shop.getId(), queryDocumentSnapshots -> {
            if (queryDocumentSnapshots != null) {
                for (DocumentSnapshot document : queryDocumentSnapshots) {
                    PromotionModel promotion = document.toObject(PromotionModel.class);
                    if (promotion != null) {
                        promotion.setPromoCode(document.getId());
                        adapter.add(promotion);
                    }
                }
            }
        });
    }

    public void onAddPromo(View view) {
        String promoCode = edPromoCode.getText().toString().trim();
        String discount = edDiscount.getText().toString().trim();
        String minimum = edMinimum.getText().toString().trim();
        String start = edStart.getText().toString().trim();
        String end = edEnd.getText().toString().trim();
        if (TextUtils.isEmpty(promoCode)) {
            Toast.makeText(this, "Enter promo code", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(discount)) {
            Toast.makeText(this, "Enter discount", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(minimum)) {
            Toast.makeText(this, "Enter minimum", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(start)) {
            Toast.makeText(this, "Select start date", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(end)) {
            Toast.makeText(this, "Select end date", Toast.LENGTH_SHORT).show();
        } else {
            PromotionModel model = new PromotionModel(shop.getId(), shop.getName(), promoCode,
                    Double.parseDouble(discount), Double.parseDouble(minimum),
                    JavaAPI.getTimestamp(start), JavaAPI.getTimestamp(end));
            api.addPromotion(model, aBoolean -> {
                if (aBoolean) {
                    adapter.add(model);
                    edPromoCode.setText("");
                    edDiscount.setText("");
                    edMinimum.setText("");
                    edStart.setText("");
                    edEnd.setText("");
                } else {
                    Toast.makeText(this, "Promotion not added", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onSelectEndDate(View view) {
        showInputDialog(edEnd);
    }

    public void onSelectStartDate(View view) {
        showInputDialog(edStart);
    }
    private void showInputDialog(EditText view) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.calender_popup_layout, null);
        dialogBuilder.setView(dialogView);

        CalendarView calendar = dialogView.findViewById(R.id.calenderPopup);
        ImageButton btnConfirm = dialogView.findViewById(R.id.btnDateConfirm);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        calendar.setOnDateChangeListener((v, year, month, day) -> {
            Calendar selectedDateCalendar = Calendar.getInstance();
            selectedDateCalendar.set(year, month, day);
            Date selectedDate = selectedDateCalendar.getTime();
            Timestamp timestamp = new Timestamp(selectedDate);
            view.setText(JavaAPI.getDate(timestamp));
        });
        btnConfirm.setOnClickListener(v -> alertDialog.dismiss());
    }
}