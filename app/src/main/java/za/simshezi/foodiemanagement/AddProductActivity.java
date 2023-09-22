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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.api.ImagesAPI;
import za.simshezi.foodiemanagement.model.ProductModel;
import za.simshezi.foodiemanagement.model.ShopModel;

public class AddProductActivity extends AppCompatActivity {
    private EditText edName, edDescription, edPrice;
    private static final int GALLERY_REQUEST_CODE = 159;
    private static final int PERMISSION_REQUEST_CODE = 158;
    private ImageView imgProduct;
    private byte[] image = null;
    private ShopModel shop = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        edName = findViewById(R.id.edMenuProductName);
        edDescription = findViewById(R.id.edMenuProductDescription);
        edPrice = findViewById(R.id.edMenuProductPrice);
        imgProduct = findViewById(R.id.imgAddProduct);
        shop = (ShopModel) getIntent().getSerializableExtra("shop");
    }

    public void onSaveProductClicked(View view) {
        String name = edName.getText().toString().trim();
        String description = edDescription.getText().toString().trim();
        String price = edPrice.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(AddProductActivity.this, "Enter product name", Toast.LENGTH_SHORT).show();
        } else if (description.isEmpty()) {
            Toast.makeText(AddProductActivity.this, "Enter product description", Toast.LENGTH_SHORT).show();
        } else if (price.isEmpty()) {
            Toast.makeText(AddProductActivity.this, "Enter product price", Toast.LENGTH_SHORT).show();
        } else if (image == null) {
            Toast.makeText(this, "Choose a picture for your shop", Toast.LENGTH_SHORT).show();
        } else {
            ProductModel model = new ProductModel(shop.getId(), name, description, Double.parseDouble(price), image);
            FirebaseAPI.getInstance().saveProduct(model, (bool) -> {
                if (bool) {
                    Toast.makeText(this, "Product has been saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddProductActivity.this, MainActivity.class);
                    shop.setDest(MenuFragment.REQ_MENU);
                    intent.putExtra("shop", shop);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "Product was not saved, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void onAddProductClicked(View view) {
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
                Toast.makeText(AddProductActivity.this, "Permission to access gallery declined", Toast.LENGTH_SHORT).show();
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
                    imgProduct.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}