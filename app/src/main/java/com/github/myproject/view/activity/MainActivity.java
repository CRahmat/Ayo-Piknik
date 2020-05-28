package com.github.myproject.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.myproject.Profile;
import com.github.myproject.R;
import com.github.myproject.view.fragment.HomeFragment;
import com.github.myproject.view.fragment.Nearby;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigation;
    Fragment selectedFragment;
    private Long backPressTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        if (savedInstanceState == null) {
            selectedFragment = new HomeFragment();
            bottomNavigation.setOnNavigationItemSelectedListener(this);
            loadFragment(selectedFragment);
        }
    }

    private boolean loadFragment(Fragment selectedFragment) {
        if (selectedFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content, selectedFragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                loadFragment(selectedFragment);
                break;
            case R.id.nav_nearby:
                selectedFragment = new Nearby();
                loadFragment(selectedFragment);
                break;
            case R.id.nav_favorite:
                selectedFragment = new Nearby();
                loadFragment(selectedFragment);
                break;
            case R.id.nav_profile:
                selectedFragment = new Profile();
                loadFragment(selectedFragment);
                break;
        }
        return loadFragment(selectedFragment);
    }

    @Override
    public void onBackPressed() {

        if (backPressTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getApplicationContext(), "Press Back Again To Exite", Toast.LENGTH_SHORT).show();
        }
        backPressTime = System.currentTimeMillis();

    }
}
