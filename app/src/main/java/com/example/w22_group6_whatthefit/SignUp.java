package com.example.w22_group6_whatthefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //text link for Sign up
        TextView txtSignUp = findViewById(R.id.txtViewSignUp);
        txtSignUp.setOnClickListener((View view)->{
            startActivity(new Intent(this,LoginActivity.class));
        });
    }
}