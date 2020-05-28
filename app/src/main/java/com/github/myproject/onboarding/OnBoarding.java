package com.github.myproject.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.myproject.Login;
import com.github.myproject.R;
import com.github.myproject.view.activity.MainActivity;
import com.google.android.material.tabs.TabLayout;
import com.scwang.wave.MultiWaveHeader;

import java.util.ArrayList;
import java.util.List;

public class OnBoarding extends AppCompatActivity {
    private ViewPager onBoardingViewPager;
    private OnBoardingAdapter onBoardingAdapter;
    private Button onBoardingNext;
    private Button onBoardingSkip;
    private Button onBoardingGetStarted;
    private TabLayout onBoardingTabLayout;
    private MultiWaveHeader multiWaveHeader;
    private TabLayout onBoardingIndicator;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Request Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_on_boarding);

        //Request No Action Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        //Instansiasi
        onBoardingIndicator = findViewById(R.id.on_boarding_tab_layout);
        onBoardingNext = findViewById(R.id.on_boarding_bttn_next);
        onBoardingGetStarted = findViewById(R.id.on_boarding_bttn_get_started);
        onBoardingTabLayout = findViewById(R.id.on_boarding_tab_layout);
        onBoardingSkip = findViewById(R.id.on_boarding_bttn_skip);
        multiWaveHeader = findViewById(R.id.on_boarding_wave);
        onBoardingViewPager = findViewById(R.id.onBoardingViewPager);

        //Default Animation
        onBoardingNext.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bttn_next_animation_show));
        onBoardingSkip.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bttn_next_animation_show));
        onBoardingNext.setVisibility(View.VISIBLE);
        onBoardingSkip.setVisibility(View.VISIBLE);

        //Check status opened onBoarding screen
        if (restorePrefsData()) {
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
            finish();
        }
        //Add Data to OnBoarding Items
        final List<OnBoardingItem> onBoardingItems = new ArrayList<>();
        onBoardingItems.add(new OnBoardingItem("Easy To Find Hotel", "Aplikasi ini memudahkan user dalam mencari sebuah penginapan ataupun hotel", R.drawable.on_boarding_image_1, R.drawable.on_boarding_background_1));
        onBoardingItems.add(new OnBoardingItem("Get Best Your Rute", "Rute terbaik untuk menuju ke tempat wisata pilihan Anda telah disediakan dalam aplikasi ini", R.drawable.on_boarding_image_2, R.drawable.on_boarding_background_2));
        onBoardingItems.add(new OnBoardingItem("Enjoy Your Traveler", "Selamat menikmati liburanmu", R.drawable.on_boarding_image_3, R.drawable.on_boarding_background_3));
        //View Pager Setup
        onBoardingAdapter = new OnBoardingAdapter(this, onBoardingItems);
        onBoardingViewPager.setAdapter(onBoardingAdapter);
        onBoardingIndicator.setupWithViewPager(onBoardingViewPager);
        //MultiWave Setup
        multiWaveHeader.setVelocity(5);
        multiWaveHeader.setProgress(1);
        multiWaveHeader.isRunning();
        multiWaveHeader.setGradientAngle(180);
        multiWaveHeader.setStartColor(Color.rgb(51, 204, 51));
        multiWaveHeader.setCloseColor(Color.rgb(77, 191, 204));
        //Next Button Action Listener
        onBoardingNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = onBoardingViewPager.getCurrentItem();
                if (position < onBoardingItems.size()) {
                    position++;
                    onBoardingViewPager.setCurrentItem(position);
                }
                if (position == onBoardingItems.size() - 1) {
                    loadLastViewPager();
                }

            }
        });
        //Get Started Button Action Listener
        onBoardingGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(getApplicationContext(), Login.class);
                startActivity(loginActivity);
                savePrefData();
                finish();
            }
        });

        //Get Started Button Action Listener
        onBoardingIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == onBoardingItems.size() - 1) {
                    loadLastViewPager();
                }
                if (onBoardingGetStarted.getVisibility() == View.VISIBLE) {
                    if (tab.getPosition() < onBoardingItems.size() - 1) {
                        onBoardingGetStarted.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bttn_get_started_hide));
                        onBoardingGetStarted.setVisibility(View.INVISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onBoardingGetStarted.setVisibility(View.INVISIBLE);
                                onBoardingNext.setVisibility(View.VISIBLE);
                                onBoardingTabLayout.setVisibility(View.VISIBLE);
                                onBoardingSkip.setVisibility(View.VISIBLE);
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void loadLastViewPager() {
        onBoardingGetStarted.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bttn_get_started_show));
        onBoardingNext.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bttn_next_animation_hide));
        onBoardingSkip.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bttn_next_animation_hide));
        onBoardingGetStarted.setVisibility(View.VISIBLE);
        onBoardingNext.setVisibility(View.INVISIBLE);
        onBoardingTabLayout.setVisibility(View.INVISIBLE);
        onBoardingSkip.setVisibility(View.INVISIBLE);
    }

    private boolean restorePrefsData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpened = sharedPreferences.getBoolean("isIntroOpened", false);
        return isIntroActivityOpened;
    }

    private void savePrefData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();
    }

}
