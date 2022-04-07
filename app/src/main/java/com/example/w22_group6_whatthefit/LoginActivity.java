package com.example.w22_group6_whatthefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

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
        setContentView(R.layout.activity_login_new);
        username= password="";
        eUsername=findViewById(R.id.txtUsernameSignUp1);
        ePassword=findViewById(R.id.txtWeightSignUp1);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Button loginBtn = findViewById(R.id.btnSignUp1);
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
                        Intent intent  = new Intent(LoginActivity.this, HomePage.class);
                        //String currentUserNameKey="";
                        intent.putExtra( "currentUserNameKey", username);

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
        TextView txtSignUp = findViewById(R.id.txtViewSignUp1);

        txtSignUp.setOnClickListener((View view)->{
            startActivity(new Intent(this,SignUp.class));
        });

    }


    public void login() {
        }


}