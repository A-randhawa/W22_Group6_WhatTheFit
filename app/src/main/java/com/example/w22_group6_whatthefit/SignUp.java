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
    String age;
    String height,weight;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
         requestQueue = Volley.newRequestQueue(getApplicationContext());
        eUsername =findViewById(R.id.txtUsernameSignUp);
        ePassword=findViewById(R.id.txtPasswordSignUp);
        eAge=findViewById(R.id.txtAgeSignUp);
        eHeight=findViewById(R.id.txtHeightSignUp);
        eWeight=findViewById(R.id.txtWeightSignUp);
        btnRegister=findViewById(R.id.btnSignUp);
        tvStatus=findViewById(R.id.txtViewSignUp);
        username=password="";


         btnRegister.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 save();
             }
         });

        //text link for Sign up
        TextView txtSignUp = findViewById(R.id.txtViewSignUp);
        txtSignUp.setOnClickListener((View view)->{
            startActivity(new Intent(this,LoginActivity.class));
        });
    }

    public void save() {

        username= eUsername.getText().toString().trim();
        password=ePassword.getText().toString().trim();
        age=(eAge.getText().toString().trim());
        height=(eHeight.getText().toString().trim());
        weight=(eWeight.getText().toString().trim());

       // requestQueue.add(request);
        if(username !="" && password!="" && age!="" && height!="" && weight!=""){

          StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  Log.d("res", response);

                  if(response.equals("success")) {
                      Toast.makeText(SignUp.this, "User Added Successfully", Toast.LENGTH_SHORT).show();
                  }


                   if (response.equals("failure")) {
                      Toast.makeText(SignUp.this, "Invalid Login Id/Password", Toast.LENGTH_SHORT).show();
                  }
                  clearData();


              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {

              }
          }){


              @Override
              protected Map<String, String> getParams() throws AuthFailureError {

                  Map<String ,String> map = new HashMap<>();
                  map.put("username",username);
                  map.put("height",height);
                  map.put("weight",weight);
                  map.put("age",age);
                  map.put("password",password);

                  return map;
              }
          };
           requestQueue.add(request);
        }

        }

        public void clearData(){

            eUsername.getText().clear();
            ePassword.getText().clear();
            eHeight.getText().clear();
            eWeight.getText().clear();
            eAge.getText().clear();
        }



}