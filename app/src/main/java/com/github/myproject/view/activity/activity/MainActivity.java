package com.github.myproject.view.activity.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.myproject.R;
import com.github.myproject.view.fragment.Favorite;
import com.github.myproject.view.fragment.Home;
import com.github.myproject.view.fragment.Nearby;
import com.github.myproject.view.fragment.Profile;
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
        new Profile();
        if (savedInstanceState == null) {
            selectedFragment = new Home();
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
                selectedFragment = new Home();
                loadFragment(selectedFragment);
                break;
            case R.id.nav_nearby:
                selectedFragment = new Nearby();
                loadFragment(selectedFragment);
                break;
            case R.id.nav_favorite:
                selectedFragment = new Favorite();
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
        try {
            if (backPressTime + 2000 > System.currentTimeMillis()) {
                finishAffinity();
                super.onBackPressed();
                return;
            } else {
                Toast.makeText(getApplicationContext(), "Press Back Again To Exite", Toast.LENGTH_SHORT).show();
            }
            backPressTime = System.currentTimeMillis();
        } catch (NullPointerException e) {
            AlertDialog.Builder alertExit = new AlertDialog.Builder(this);
            alertExit.setIcon(R.drawable.logo_ayo_piknik);
            alertExit.setMessage("Are you sure you want to exit?");
            alertExit.setCancelable(false);
            alertExit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertExit.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = alertExit.create();
            alertDialog.show();
        }

    }
}
