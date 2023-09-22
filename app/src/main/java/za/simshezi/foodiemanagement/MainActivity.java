package za.simshezi.foodiemanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.InputStream;

import za.simshezi.foodiemanagement.api.FirebaseAPI;
import za.simshezi.foodiemanagement.api.ImagesAPI;
import za.simshezi.foodiemanagement.model.ShopModel;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private int finish = 0;
    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment = new HomeFragment();
    private MenuFragment menuFragment = new MenuFragment();
    private OrdersFragment ordersFragment = new OrdersFragment();
    private ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnItemSelectedListener(this);
        ShopModel model = (ShopModel) getIntent().getSerializableExtra("shop");
        switch (model.getDest()) {
            case 0: {
                bottomNavigationView.setSelectedItemId(R.id.current_orders_dest);
                break;
            }
            case 1: {
                bottomNavigationView.setSelectedItemId(R.id.previous_orders_dest);
                break;
            }
            case 2: {
                bottomNavigationView.setSelectedItemId(R.id.manage_menu_dest);
                break;
            }
            case 3: {
                bottomNavigationView.setSelectedItemId(R.id.profile_dest);
                break;
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        finish = 0;
        int itemId = item.getItemId();
        if (itemId == R.id.current_orders_dest) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, homeFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.previous_orders_dest) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, ordersFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.manage_menu_dest) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, menuFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.profile_dest) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, profileFragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish++;
        if (finish == 2) {
            finishAffinity();
        } else {
            Toast.makeText(this, "Press back again to exit the app", Toast.LENGTH_SHORT).show();
        }
    }
}