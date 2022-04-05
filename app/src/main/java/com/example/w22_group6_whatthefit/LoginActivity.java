package com.example.w22_group6_whatthefit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText eUsername;
    EditText ePassword;
    EditText eAge;
    EditText eHeight;
    EditText eWeight;
    private String URL ="http://192.168.222.1/whatthefit/login.php";
    DBHelper DB;
    String username,password;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username= password="";
        eUsername=findViewById(R.id.txtUsernameSignUp);
        ePassword=findViewById(R.id.txtPasswordSignUp);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Button loginBtn = findViewById(R.id.btnSignUp);
        //click listener
        loginBtn.setOnClickListener((View view) -> {


            username= eUsername.getText().toString().trim();
            password=ePassword.getText().toString().trim();
            if(username !="" && password!="" ) {
                username = eUsername.getText().toString().trim();
                password=ePassword.getText().toString().trim();
                DB = new DBHelper(this);

                if(username!="" && password!=""){
                    Boolean checkuserpass = DB.checkusernamepassword(username, password);
                    if(checkuserpass==true){
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Username or password are empty",Toast.LENGTH_SHORT);
                }
            }

            });
        //text link for Sign up
        TextView txtSignUp = findViewById(R.id.txtViewSignUp);

        txtSignUp.setOnClickListener((View view)->{
            startActivity(new Intent(this,SignUp.class));
        });

    }


    public void login() {
        }


}