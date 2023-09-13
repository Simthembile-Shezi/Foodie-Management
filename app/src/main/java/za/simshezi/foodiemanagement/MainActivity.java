package za.simshezi.foodiemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.InputStream;

import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.api.ImagesAPI;
import za.simshezi.foodiemanagement.model.ShopModel;

public class MainActivity extends AppCompatActivity {
    public static final int PROFILE_REQ = 2000;
    private TextView tvShopName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShopName = findViewById(R.id.tvMainShopName);

        Intent data = getIntent();
        ShopModel user = (ShopModel) data.getSerializableExtra("user");
        if (user != null) {
            tvShopName.setText(user.getName());
        }
    }

    public void onProfileClicked(View view) {
        Intent data = getIntent();
        ShopModel user = (ShopModel) data.getSerializableExtra("user");
        if (user != null) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("user", user);
            startActivityForResult(intent, PROFILE_REQ);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PROFILE_REQ && data != null) {
            ShopModel model = (ShopModel) data.getSerializableExtra("user");
            if (model != null) {
                tvShopName.setText(model.getName());
            }
        }
    }
}