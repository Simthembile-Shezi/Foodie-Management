package za.simshezi.foodiemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.model.ShopModel;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences readPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String email = readPreferences.getString("email", "");
        String password = readPreferences.getString("password", "");
        if (!email.equals("") && !password.equals("")) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SplashActivity.this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                FirebaseAPI.getInstance().getShop(user.getEmail(), (document) -> {
                                    Intent intent = new Intent(this, MainActivity.class);
                                    if (document != null) {
                                        ShopModel model = document.toObject(ShopModel.class);
                                        assert model != null;
                                        model.setId(document.getId());
                                        intent.putExtra("shop", model);
                                    }
                                    startActivity(intent);
                                });
                            }
                        }
                    });
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}