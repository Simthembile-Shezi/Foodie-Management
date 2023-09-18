package za.simshezi.foodiemanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    private Switch statusSwitch;
    private EditText edName, edCellphone;
    private byte[] image = null;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imgShopLogo = findViewById(R.id.imgProfileShopLogo);
        statusSwitch = findViewById(R.id.switchProfileStatus);
        edName = findViewById(R.id.edProfileShopName);
        edCellphone = findViewById(R.id.edProfileUserCellphone);

        Intent intent = getIntent();
        ShopModel model = (ShopModel) intent.getSerializableExtra("shop");
        if (model != null) {
            imgShopLogo.setImageBitmap(ImagesAPI.convertToBitmap(model.getImage()));
            edName.setText(model.getName());
            edCellphone.setText(model.getCellphone());
            id = model.getId();
            image = model.getImage();
        } else {
            finish();
        }
    }

    public void onProfileConfirmClicked(View view) {
        String name = edName.getText().toString().trim();
        String cellphone = edCellphone.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter full name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cellphone)) {
            Toast.makeText(this, "Enter cellphone number", Toast.LENGTH_SHORT).show();
        } else if (image == null) {
            Toast.makeText(this, "Choose a picture for your shop", Toast.LENGTH_SHORT).show();
        } else {

            ShopModel model = new ShopModel(name, id, cellphone, "Open", image);
            FirebaseAPI.getInstance().editShop(model, bool -> {
                if (bool) {
                    Intent intent = new Intent();
                    intent.putExtra("shop", model);
                    setResult(MainActivity.PROFILE_REQ);
                    finish();
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