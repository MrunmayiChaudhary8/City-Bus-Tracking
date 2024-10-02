package com.example.citybustracking;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.citybustracking.HomeActivity;
import com.example.citybustracking.R;
import com.example.citybustracking.RegistrationActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername,etPassword;
    CheckBox cbShowHidePassword;
    Button btnLogin;
    TextView tvSignin;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login Activity");


        preferences = PreferenceManager.getDefaultSharedPreferences(com.example.citybustracking.LoginActivity.this);
        editor = preferences.edit();

        if (preferences.getBoolean("isLogin",false))
        {
            Intent i = new Intent(com.example.citybustracking.LoginActivity.this,HomeActivity.class);
            startActivity(i);
        }

        etUsername = findViewById(R.id.etUsernameLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        cbShowHidePassword =findViewById(R.id.cbcheckanduncheckactivity);
        btnLogin = findViewById(R.id.btnuserlogin);
        tvSignin = findViewById(R.id.tvSingup);

        cbShowHidePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean IsChecked) {
                if (IsChecked) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etUsername.getText().toString().isEmpty())
                {
                    etUsername.setError("Please Enter Your Username");
                } else if (etPassword.getText().toString().isEmpty()) {

                    etPassword.setError("Please Enter Your Password");

                } else if (etUsername.getText().toString().length() <  8) {
                    etUsername.setError("Please Enter 8 Character Username");

                } else if (etPassword.getText().toString().length() < 8) {
                    etPassword.setError("Please Enter 8 Character Password");

                } else if (!etUsername.getText().toString().matches(".*[A-Z].*")) {
                    etUsername.setError("Please Used 1 Uppercase Letter");

                } else if (!etUsername.getText().toString().matches(".*[a-z].*")) {
                    etUsername.setError("Please Used 1 Lowercase Letter");

                } else if (!etUsername.getText().toString().matches(".*[0-9].*")) {
                    etUsername.setError("Please Used 1 Number");

                } else if (!etUsername.getText().toString().matches(".*[@,#.$,&,!].*")) {
                    etUsername.setError("Please Used 1 Special Symbol");

                } else
                {
                    Toast.makeText(com.example.citybustracking.LoginActivity.this,"Login Successfully Done", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(com.example.citybustracking.LoginActivity.this, HomeActivity.class);
                    editor.putString("username",etUsername.getText().toString()).commit();
                    editor.putBoolean("isLogin",true).commit();
                    startActivity(intent);

                }
            }



        });


        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(com.example.citybustracking.LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });
    }
}
