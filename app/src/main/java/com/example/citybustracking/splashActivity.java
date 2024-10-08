package com.example.citybustracking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

public class splashActivity extends AppCompatActivity {

    TextView tvtitle;
    LottieAnimationView lottieAnimationView;

    Animation animtranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvtitle = findViewById(R.id.tvSplash);
        lottieAnimationView =  findViewById(R.id.laLoginLogo);

        animtranslate = AnimationUtils.loadAnimation(splashActivity.this,R.anim.toptobottomtranslate);
        lottieAnimationView.startAnimation(animtranslate);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splashActivity.this , LoginActivity.class);
                startActivity(i);

            }
        },5000);



    }
}

