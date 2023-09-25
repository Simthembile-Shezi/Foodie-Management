package za.simshezi.foodiemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.model.ShopModel;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences readPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String email = readPreferences.getString("email", "");
        String password = readPreferences.getString("password", "");
        if (!email.equals("") && !password.equals("")) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SplashActivity.this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseAPI api = FirebaseAPI.getInstance();
                            api.getShop(email, (querySnapshot) -> {
                                if (querySnapshot != null) {
                                    Intent intent = new Intent(this, MainActivity.class);
                                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                    ShopModel model = document.toObject(ShopModel.class);
                                    if (model != null) {
                                        model.setId(document.getId());
                                        model.setDest(HomeFragment.HOME_REQ);
                                        intent.putExtra("shop", model);
                                        startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(SplashActivity.this, "Shop associated with account not found", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}