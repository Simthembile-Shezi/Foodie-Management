package za.simshezi.foodiemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.model.ShopModel;

public class LoginActivity extends AppCompatActivity {
    private EditText edEmail, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edEmail = findViewById(R.id.edLoginEmail);
        edPassword = findViewById(R.id.edLoginPassword);

    }

    public void onForgotPasswordClicked(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void onNewAccountClicked(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void onLoginClicked(View view) {

        //startActivity(new Intent(this, MainActivity.class));
        //Simshezi8@gmail.com SimShezi
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter a valid password", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isSuccessful()) {
                        SharedPreferences writePreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = writePreferences.edit();
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.apply();
                        FirebaseAPI api = FirebaseAPI.getInstance();
                        api.getShop(email, (querySnapshot) -> {
                            if (querySnapshot != null) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                ShopModel model = document.toObject(ShopModel.class);
                                if (model != null) {
                                    api.getShopLogo(document.getId(), bytes -> {
                                        if (bytes != null) {
                                            model.setImage(bytes);
                                            model.setId(document.getId());
                                            model.setDest(HomeFragment.HOME_REQ);
                                            intent.putExtra("shop", model);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Shop image associated with account not found", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Shop associated with account not found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // Login failed
                        Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}