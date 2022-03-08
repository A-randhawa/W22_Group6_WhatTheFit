package com.example.w22_group6_whatthefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//this is the main activity
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //changes made here are displayed on local repo

        //declaring login button
        Button loginBtn = findViewById(R.id.loginBtn);
        //click listener
        loginBtn.setOnClickListener((View view) -> {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        });



        //declaring test map location button
        Button testMapLocBtn = findViewById(R.id.btnTestMapLoc);
        //click listener
        testMapLocBtn.setOnClickListener((View view) -> {
            startActivity(new Intent(MainActivity.this,GoogleLocation.class));
        });

    }
}