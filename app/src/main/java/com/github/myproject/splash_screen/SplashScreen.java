package com.github.myproject.splash_screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.myproject.R;
import com.github.myproject.onboarding.OnBoarding;

public class SplashScreen extends AppCompatActivity {
    Animation logoAnimation;
    ImageView logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logoImage = findViewById(R.id.logo);
        logoAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_logo);
        logoImage.setAnimation(logoAnimation);

        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Intent onBoarding = new Intent(SplashScreen.this, OnBoarding.class);
                                          startActivity(onBoarding);
                                          finish();
                                      }
                                  }, 3000
        );
    }
}
