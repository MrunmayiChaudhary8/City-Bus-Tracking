package com.example.citybustracking;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.citybustracking.HomeActivity;
import com.example.citybustracking.R;
import com.example.citybustracking.RegistrationActivity;
import com.example.citybustracking.common.NetworkChangeListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername,etPassword;
    CheckBox cbShowHidePassword;
    Button btnLogin;
    TextView tvSignin;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;

    GoogleSignInOptions googleSignInOptions;

    GoogleSignInClient googleSignInClient;
    AppCompatButton btnSignInWithGoogle;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


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
        btnLogin = findViewById(R.id.BtnLogin);
        tvSignin = findViewById(R.id.tvSingup);
        btnSignInWithGoogle = findViewById(R.id.acbtnLoginSignInWithGoogle);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this,googleSignInOptions);

        btnSignInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }


        });

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
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Please Wait..!");
                    progressDialog.setMessage("Login Under Process");
                    progressDialog.show();
                    userLogin();

                }
            }
            @Override
            protected void onStart(){
                LoginActivity.super.onStart();
                 IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                 registerReceiver(networkChangeListener,filter);

            }

            @Override
            protected void onStop(){
                LoginActivity.super.onStop();
                unregisterReceiver(networkChangeListener);
            }


            private void userLogin() {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();

                params.put("username",etUsername.getText().toString());
                params.put("password",etPassword.getText().toString());

                client.post("http:// 192.168.43.152:80/CityBusAPI/userLogin.php",params,new JsonHttpResponseHandler()
                        {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                super.onSuccess(statusCode, headers, response);

                                try {
                                    String status = response.getString(Integer.parseInt("success"));
                                    if (status.equals("1"))
                                    {
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this,"Invalid Username or Password",Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);

                                progressDialog.dismiss();

                                Toast.makeText(LoginActivity.this,"Server Error..!",Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                );
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

    private void signIn() {

        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 999)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();


            } catch (ApiException e) {
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

