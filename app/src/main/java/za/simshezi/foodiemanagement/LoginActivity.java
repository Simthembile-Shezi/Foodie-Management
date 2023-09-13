package za.simshezi.foodiemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.model.ShopModel;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edEmail, edPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        edEmail = findViewById(R.id.edLoginEmail);
        edPassword = findViewById(R.id.edLoginPassword);
        FirebaseApp.initializeApp(this);
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
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        FirebaseAPI api = FirebaseAPI.getInstance();
                        if(user != null){
                            api.getShop(user.getEmail(), documentSnapshot -> {
                                        if (documentSnapshot != null && documentSnapshot.exists()) {
                                            String id = documentSnapshot.getId();
                                            String name = documentSnapshot.getString("name");
                                            String cellphone = documentSnapshot.getString("cellphone");
                                            boolean status = documentSnapshot.getBoolean("status");
                                            double rating = documentSnapshot.getDouble("rating");
                                            api.getShopLogo(id, bytes ->{
                                                if(bytes != null){
                                                    Intent intent = new Intent(this, MainActivity.class);
                                                    ShopModel model = new ShopModel(name, user.getEmail(), cellphone, (float) rating, status, bytes);
                                                    model.setId(id);
                                                    intent.putExtra("user", model);
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    }
                            );
                        }
                    } else {
                        // Login failed
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}