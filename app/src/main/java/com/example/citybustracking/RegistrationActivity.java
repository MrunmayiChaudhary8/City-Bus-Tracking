package com.example.citybustracking;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends Activity {
    EditText etName,etMobileNumber,etEmailID,etUserName,etPassword;
    Button btnRegister;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Registration Activity");
        preferences = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
        editor = preferences.edit();

        etName = findViewById(R.id.etRegisterName);
        etMobileNumber = findViewById(R.id.etRegisterMobileNumber);
        etEmailID = findViewById(R.id.etRegisterEmailID);
        etUserName = findViewById(R.id.etRegisterUserName);
        etPassword = findViewById(R.id.etRegisterPassword);
        btnRegister = findViewById(R.id.btnRegisterRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().isEmpty()) {
                    etName.setError("Please Enter Your Name");

                } else if (etMobileNumber.getText().toString().isEmpty()) {
                    etMobileNumber.setError("Please Enter Your Mobile Number");
                } else if (etMobileNumber.getText().toString().length() != 10) {
                    etMobileNumber.setError("please Enter Your Correct Mobile No");

                } else if (etEmailID.getText().toString().isEmpty()) {
                    etEmailID.setError("Please Enter Your Email ID");
                } else if (!etEmailID.getText().toString().contains("@")){
                    etEmailID.setError("Please Enter Valid EmailID");
                } else if (etUserName.getText().toString().isEmpty()) {
                    etUserName.setError("Please Enter Your UserName");
                } else if (etUserName.getText().toString().length() < 8) {
                    etUserName.setError("UserName Must BE Greater Than 8");
                }else if (!etUserName.getText().toString().matches(".*[A-Z].*")) {
                    etUserName.setError("Please Enter 1 Uppercase Letter");
                }else if (!etUserName.getText().toString().matches(".*[a-z].*")) {
                    etUserName.setError("Please Enter 1 Lowercase Letter");
                }else if (!etUserName.getText().toString().matches(".*[0-9].*")) {
                    etUserName.setError("Please Enter 1 Number");
                }else if (!etUserName.getText().toString().matches(".*[@,#.$,&,!].*")) {
                    etUserName.setError("Please Enter 1 Special Symbol");
                }else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Please Enter Your Password");
                } else if (etPassword.getText().toString().length() < 8) {
                    etPassword.setError("UserName Must BE Greater Than 8");
                }else {
                    Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                    editor.putString("name",etName.getText().toString()).commit();
                    editor.putString("mobileno",etMobileNumber.getText().toString()).commit();
                    editor.putString("emailid",etEmailID.getText().toString()).commit();
                    editor.putString("username",etUserName.getText().toString()).commit();
                    editor.putString("password",etPassword.getText().toString()).commit();
                    Toast.makeText(RegistrationActivity.this, "Registration Successfully Done", Toast.LENGTH_SHORT).show();
                    startActivity(intent);


                }
            }



        });

    }
}

