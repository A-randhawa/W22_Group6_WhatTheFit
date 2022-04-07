package com.example.w22_group6_whatthefit;

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

import java.security.spec.ECField;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText eUsername;
    EditText ePassword;
    EditText eAge;
    EditText eHeight;
    EditText eWeight;
    TextView tvStatus;
    Button btnRegister;
    private String URL ="http://192.168.222.1/whatthefit/register.php";
    String username,password;
    int age;
    Double height,weight;
    DBHelper DB;
    RequestQueue requestQueue;
    private static final String TAG = "SignUp";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);
         requestQueue = Volley.newRequestQueue(getApplicationContext());
        eUsername =findViewById(R.id.txtUsernameSignUp2);
        ePassword=findViewById(R.id.txtPasswordSignUp2);
        eAge=findViewById(R.id.txtAgeSignUp1);
        eHeight=findViewById(R.id.txtHeightSignUp);
        eWeight=findViewById(R.id.txtWeightSignUp1);
        btnRegister=findViewById(R.id.btnSignUp2);
        tvStatus=findViewById(R.id.txtViewSignUp);
        username=password="";


         btnRegister.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 save();
             }
         });

        //text link for Sign up
      /*  TextView txtSignUp = findViewById(R.id.txtViewSignUp);
        txtSignUp.setOnClickListener((View view)->{
            startActivity(new Intent(this,LoginActivity.class));
        });*/




    }

    public void save() {

        try {



        username = eUsername.getText().toString().trim();
        password = ePassword.getText().toString().trim();
        age = Integer.parseInt(eAge.getText().toString().trim());
        height = Double.parseDouble(eHeight.getText().toString().trim());
        weight = Double.parseDouble(eWeight.getText().toString().trim());
            DB = new DBHelper(this);
        // requestQueue.add(request);
        if (username != "" && password != "" && age !=0 && height != 0 && weight != 0) {
            Boolean checkuser = DB.checkusername(username);
            if(checkuser==false) {
                Boolean insert = DB.insertData(username, age, height, weight, password);
                if (insert == true) {
                    Toast.makeText(SignUp.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                    clearData();

                } else {
                    Toast.makeText(SignUp.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                Toast.makeText(SignUp.this, "User Already Exists", Toast.LENGTH_SHORT).show();
            }

    }}
        catch (Exception e){
                   Log.d(TAG,"Sign up exception");
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }


        public void clearData(){

            eUsername.getText().clear();
            ePassword.getText().clear();
            eHeight.getText().clear();
            eWeight.getText().clear();
            eAge.getText().clear();
        }



}