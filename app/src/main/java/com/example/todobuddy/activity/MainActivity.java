package com.example.todobuddy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.todobuddy.R;
import com.example.todobuddy.fragment.ApiFragment;
import com.example.todobuddy.fragment.HomeFragment;
import com.example.todobuddy.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private final static String PREF_LOGIN = "login";
    SharedPreferences preferencesLogin;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private HomeFragment HomeFragment;
    private ApiFragment ApiFragment;
    private ProfileFragment ProfileFragment;

    private Boolean cekLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TodoBuddy");

        // Menerima status login dari SharedPreferences
        // jika belum login maka beralih ke LoginActivity
        preferencesLogin = getSharedPreferences("logincheck", MODE_PRIVATE);
        cekLogin = preferencesLogin.getBoolean(PREF_LOGIN, false);

        // penanganan ketika belum login
        if (!cekLogin){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        HomeFragment = new HomeFragment();
        ApiFragment = new ApiFragment();
        ProfileFragment = new ProfileFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, HomeFragment)
                .commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.nav_home) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout, HomeFragment)
                            .commit();
                    toolbar.setTitle("TodoBuddy");
                } else if (itemId == R.id.nav_api) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout, ApiFragment)
                            .commit();
                    toolbar.setTitle("Example Task");
                } else if (itemId == R.id.nav_profile) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout, ProfileFragment)
                            .commit();
                    toolbar.setTitle("Profile");
                }
                return true;
            }
        });
    }
}
