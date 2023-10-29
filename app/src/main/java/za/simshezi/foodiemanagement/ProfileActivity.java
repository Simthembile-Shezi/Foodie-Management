package za.simshezi.foodiemanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;

import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.api.ImagesAPI;
import za.simshezi.foodiemanagement.model.ShopModel;

public class ProfileActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 159;
    private static final int PERMISSION_REQUEST_CODE = 158;
    private ImageView imgShopLogo;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch statusSwitch;
    private EditText edName, edAddress, edCellphone, edOpenDay, edCloseDay, edOpenTime, edCloseTime;
    private byte[] image = null;
    private ShopModel model = null;
    private FirebaseAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imgShopLogo = findViewById(R.id.imgProfileShopLogo);
        statusSwitch = findViewById(R.id.switchProfileStatus);
        edName = findViewById(R.id.edProfileShopName);
        edAddress = findViewById(R.id.edProfileShopAddress);
        edCellphone = findViewById(R.id.edProfileUserCellphone);
        edOpenDay = findViewById(R.id.edProfileOpenDay);
        edCloseDay = findViewById(R.id.edProfileCloseDay);
        edOpenTime = findViewById(R.id.edProfileOpenTime);
        edCloseTime = findViewById(R.id.edProfileClosingTime);

        model = (ShopModel) getIntent().getSerializableExtra("shop");
        if(model != null) {
            String[] days = model.getDays().split("-");
            String[] times = model.getTimes().split("-");
            edName.setText(model.getName());
            edAddress.setText(model.getAddress());
            edCellphone.setText(model.getCellphone());
            edOpenDay.setText(days[0]);
            edCloseDay.setText(days[1]);
            edOpenTime.setText(times[0]);
            edCloseTime.setText(times[1]);
            if (model.getStatus().equals("Open")) {
                statusSwitch.setChecked(true);
            }
            api = FirebaseAPI.getInstance();
            api.getShopLogo(model.getId(), bytes -> {
                model.setImage(bytes);
                image = model.getImage();
                imgShopLogo.setImageBitmap(ImagesAPI.convertToBitmap(image));
            });
        }
    }

    public void onProfileConfirmClicked(View view) {
        String name = edName.getText().toString().trim();
        String address = edAddress.getText().toString().trim();
        String cellphone = edCellphone.getText().toString().trim();
        String openTime = edOpenTime.getText().toString().trim();
        String closeTime = edCloseTime.getText().toString().trim();
        String openDay = edOpenDay.getText().toString().trim();
        String closeDay = edCloseDay.getText().toString().trim();
        String status;
        if (statusSwitch.isChecked()) {
            status = "Open";
        } else {
            status = "Closed";
        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter full name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Enter address", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cellphone)) {
            Toast.makeText(this, "Enter cellphone number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(openTime)) {
            Toast.makeText(this, "Enter opening time", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(closeTime)) {
            Toast.makeText(this, "Enter closing time", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(openDay)) {
            Toast.makeText(this, "Enter first day the shop is open", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(closeDay)) {
            Toast.makeText(this, "Enter last day the shop is open", Toast.LENGTH_SHORT).show();
        } else if (image == null) {
            Toast.makeText(this, "Choose a picture for your shop", Toast.LENGTH_SHORT).show();
        } else {
            String days = openDay.concat("-").concat(closeDay);
            String times = openTime.concat("-").concat(closeTime);
            ShopModel shop = new ShopModel(name, model.getEmail(), cellphone, status, address, days, times, image);
            shop.setId(model.getId());
            api.editShop(shop, bool -> {
                if (bool) {
                    shop.setDest(ProfileFragment.PROFILE_REQ);
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    intent.putExtra("shop", shop);
                    startActivity(intent);
                } else {
                    Toast.makeText(ProfileActivity.this, "Profile updated failed, try again later", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onProfileShopLogoClicked(View view) {
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            openGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(ProfileActivity.this, "Permission to access gallery declined", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    image = ImagesAPI.convertToByte(bitmap);
                    imgShopLogo.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}