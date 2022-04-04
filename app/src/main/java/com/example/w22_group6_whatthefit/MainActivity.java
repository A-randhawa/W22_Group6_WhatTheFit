package com.example.w22_group6_whatthefit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

//this is the main activity

public class MainActivity extends AppCompatActivity {

private int progressBar =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //changes made here are displayed on local repo

        Button loginBtn = findViewById(R.id.btnlogin);
        //click listener
        loginBtn.setOnClickListener((View view) -> {
            startActivity(new Intent(this,LoginActivity.class));
        });

        //text link for Sign up
        TextView txtSignUp = findViewById(R.id.txtViewSignUp);
        txtSignUp.setOnClickListener((View view)->{
            startActivity(new Intent(this,SignUp.class));
        });





    }



}