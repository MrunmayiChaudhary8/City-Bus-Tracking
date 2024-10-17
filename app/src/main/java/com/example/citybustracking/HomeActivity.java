package com.example.citybustracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeActivity extends AppCompatActivity {

        TextView tvName,tvEmailId;
        AppCompatButton btnsignOut;

        GoogleSignInOptions googleSignInOptions;
        GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        tvName = findViewById(R.id.tvName);
        tvEmailId = findViewById(R.id.tvEmail);
        btnsignOut = findViewById(R.id.BtnSignOut);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(HomeActivity.this, googleSignInOptions);

        GoogleSignInAccount googleSignInAccount =  GoogleSignIn.getLastSignedInAccount(this);

        if (googleSignInAccount != null) {
            String name = googleSignInAccount.getDisplayName();
            String Email = googleSignInAccount.getEmail();

            tvName.setText(name);
            tvEmailId.setText(Email);

            btnsignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                }
            });
        }


    }
    }
