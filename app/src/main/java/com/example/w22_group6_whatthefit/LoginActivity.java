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

            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("res", response);

                    if(response.equals("success")){

                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomePage.class);
                        startActivity(intent);
                        finish();
                                            }
                    else if (response.equals("failure")){
                        Toast.makeText(LoginActivity.this, "Invalid Login Credentials", Toast.LENGTH_SHORT).show();

                    }
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }

            ){


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String ,String> map = new HashMap<>();
                    map.put("username",username);

                    map.put("password",password);

                    return map;
                }
            };
                requestQueue.add(request);;
            }




        });

        //text link for Sign up
        TextView txtSignUp = findViewById(R.id.txtViewSignUp);
        txtSignUp.setOnClickListener((View view)->{
            startActivity(new Intent(this,SignUp.class));
        });

    }


    public void login(View view){
        username = eUsername.getText().toString().trim();
        password=ePassword.getText().toString().trim();

        if(username!="" && password!=""){
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Intent intent = new Intent(LoginActivity.this, HomePage.class);
                        startActivity(intent);
                        finish();
                    } else if (response.equals("failure")) {
                        Toast.makeText(LoginActivity.this, "Incorrect username or Password", Toast.LENGTH_SHORT).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this,error.toString().trim(),Toast.LENGTH_SHORT);
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> map = new HashMap<>();
                    map.put("username",username);
                    map.put("password",password);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
        else
        {
            Toast.makeText(LoginActivity.this,"Username or password are empty",Toast.LENGTH_SHORT);
        }
    }


}