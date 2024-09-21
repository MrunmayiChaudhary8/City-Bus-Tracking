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

public class splashActivity extends AppCompatActivity {

    TextView tvtitle;

    Animation animtranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvtitle = findViewById(R.id.tvSplash);

        animtranslate = AnimationUtils.loadAnimation(splashActivity.this,R.anim.toptobottomtranslate);
        tvtitle.startAnimation(animtranslate);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splashActivity.this , LoginActivity.class);
                startActivity(i);

            }
        } ,4000);



    }
}