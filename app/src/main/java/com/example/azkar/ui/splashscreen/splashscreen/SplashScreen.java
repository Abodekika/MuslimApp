package com.example.azkar.ui.splashscreen.splashscreen;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.azkar.R;
import com.example.azkar.data.sharedpreferences.PrayersPreferences;
import com.example.azkar.data.sharedpreferences.QuranPreferences;
import com.example.azkar.ui.MainActivity;
import com.example.azkar.ui.location.LocationActivity;


public class SplashScreen extends AppCompatActivity {

    PrayersPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TextView sp_tex = findViewById(R.id.sp_tex);
        ImageView logo = findViewById(R.id.logo);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.alpha);

        Typeface fonts = Typeface.createFromAsset(getAssets(), "fonts/AlHurra Font Bold.ttf");
        logo.startAnimation(animation);
        sp_tex.startAnimation(animation);

        preferences = new PrayersPreferences(this);

        sp_tex.setTypeface(fonts);
        new Handler().postDelayed(() -> {
            // This method will be executed once the timer is over


            if (preferences.getFIRST_OPEN()) {
                Intent i1 = new Intent(SplashScreen.this, LocationActivity.class);
                startActivity(i1);
                finish();
                preferences.setFIRST_OPEN(false);
            } else {
                Intent i1 = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i1);
                finish();
            }

        }, 3000);
    }
}