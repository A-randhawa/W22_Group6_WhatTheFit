package com.example.w22_group6_whatthefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button loginBtn = findViewById(R.id.loginButton);
        //click listener
        loginBtn.setOnClickListener((View view) -> {
            startActivity(new Intent(LoginActivity.this,HomePage.class));
        });


    }



}