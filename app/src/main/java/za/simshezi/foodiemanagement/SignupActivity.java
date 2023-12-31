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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.api.ImagesAPI;
import za.simshezi.foodiemanagement.model.ShopModel;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final int GALLERY_REQUEST_CODE = 159;
    private static final int PERMISSION_REQUEST_CODE = 158;
    private ImageView imgShopLogo;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch statusSwitch;
    private EditText edName, edEmail, edAddress, edCellphone, edPassword, edOpenDay, edCloseDay, edOpenTime, edCloseTime;
    private byte[] image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        imgShopLogo = findViewById(R.id.imgSignupShopLogo);
        edName = findViewById(R.id.edSignupShopName);
        edEmail = findViewById(R.id.edSignupUserEmail);
        edAddress = findViewById(R.id.edSignupAddress);
        edOpenDay = findViewById(R.id.edOpenDay);
        edCloseDay = findViewById(R.id.edCloseDay);
        edOpenTime = findViewById(R.id.edOpenTime);
        edCloseTime = findViewById(R.id.edClosingTime);
        edCellphone = findViewById(R.id.edSignupUserCellphone);
        edPassword = findViewById(R.id.edSignupUserPassword);
        statusSwitch = findViewById(R.id.switchSignUpStatus);
        mAuth = FirebaseAuth.getInstance();
        imgShopLogo.setImageResource(R.drawable.icon);
    }

    public void onAlreadySignedClicked(View view) {
        finish();
    }

    public void onSignupClicked(View view) {
        String name = edName.getText().toString().trim();
        String address = edAddress.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String cellphone = edCellphone.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
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
            Toast.makeText(this, "Enter shop physical address", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter email address", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(openTime)) {
            Toast.makeText(this, "Enter opening time", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(closeTime)) {
            Toast.makeText(this, "Enter closing time", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(openDay)) {
            Toast.makeText(this, "Enter first day the shop is open", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(closeDay)) {
            Toast.makeText(this, "Enter last day the shop is open", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cellphone)) {
            Toast.makeText(this, "Enter cellphone number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter a valid password", Toast.LENGTH_SHORT).show();
        } else if (image == null) {
            Toast.makeText(this, "Choose a picture for your shop", Toast.LENGTH_SHORT).show();
        } else {
            String days = openDay.concat("-").concat(closeDay);
            String times = openTime.concat("-").concat(closeTime);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignupActivity.this, task -> {
                        if (task.isSuccessful()) {
                            ShopModel model = new ShopModel(name, email, cellphone, status, address,days, times, image);
                            FirebaseAPI.getInstance().saveShop(model, bool -> {
                                if (bool) {
                                    Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(SignupActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(SignupActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    public void onSignupShopLogoClicked(View view) {
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
                Toast.makeText(SignupActivity.this, "Permission to access gallery declined", Toast.LENGTH_SHORT).show();
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