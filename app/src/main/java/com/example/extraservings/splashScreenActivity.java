package com.example.extraservings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class splashScreenActivity extends AppCompatActivity {
    ImageView imageView, splashImage;
    TextView textView;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView = findViewById(R.id.image1);
        splashImage = findViewById(R.id.bg);
        textView = findViewById(R.id.text);
        lottieAnimationView = findViewById(R.id.lottie);

        splashImage.animate().translationY(-2500).setDuration(1000).setStartDelay(3500);
        imageView.animate().translationY(2000).setDuration(1000).setStartDelay(3500);
        textView.animate().translationY(1400).setDuration(1000).setStartDelay(3500);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(3500);
        setContentView(R.layout.activity_splash_screen);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        int SPLASH_DISPLAY_LENGTH = 3500;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(splashScreenActivity.this,RegisterActivity.class);
                splashScreenActivity.this.startActivity(mainIntent);
                splashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}


