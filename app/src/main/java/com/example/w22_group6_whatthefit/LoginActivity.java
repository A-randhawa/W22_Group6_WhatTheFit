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
    TextView txtBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        username= password="";
        eUsername=findViewById(R.id.txtUsernameSignUp1);
        ePassword=findViewById(R.id.txtWeightSignUp1);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Button loginBtn = findViewById(R.id.btnSignUp1);

        txtBack=findViewById(R.id.txtBack);
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
                        Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(LoginActivity.this, HomePage.class);
                        //String currentUserNameKey="";
                      //  intent.putExtra( "currentUserNameKey", username);

                        startActivity(intent);
                    }else{
                        Toast.makeText(getBaseContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(getBaseContext(),"Username or password are empty",Toast.LENGTH_SHORT).show();
                }
            }

            });
        //text link for Sign up
        TextView txtSignUp = findViewById(R.id.txtViewSignUp1);

        txtSignUp.setOnClickListener((View view)->{
            startActivity(new Intent(this,SignUp.class));
        });

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));

                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

            }
        });



    }


    public void login() {
        }



}