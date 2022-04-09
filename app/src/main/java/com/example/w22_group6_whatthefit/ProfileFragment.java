package com.example.w22_group6_whatthefit;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ProfileFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    HomePage homePage;
    SliderAdapter adapter;
    AnimatedBottomBar bottom_bar;
    FragmentManager fragmentManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DBHelper dbHelper = new DBHelper(getContext());

        String currentUser = getArguments().getString("currentUser");

        View view= inflater.inflate(R.layout.activity_profile, container, false);

        TextView txtUsername = view.findViewById(R.id.tv_name);
        TextView age = view.findViewById(R.id.txtAge);
        TextView height = view.findViewById(R.id.txtHeight);
        TextView weight = view.findViewById(R.id.txtWeight);
        Cursor cursor = dbHelper.viewData(currentUser);
        while (cursor.moveToNext()){
            String user =cursor.getString(0);
            int age1 =cursor.getInt(1);
            Double height1 =cursor.getDouble(2);
            Double weight1 =cursor.getDouble(3);
            CalBMI(view,height1,weight1);
            txtUsername.setText(user);
            age.setText(age1+"");
            height.setText(height1 +"cm");
            weight.setText(weight1+" Kg");

        }





        return view;

    }


    public void CalBMI(View view,Double weight,Double height){


        TextView BMIVal = view.findViewById(R.id.txtBMIVal);
        RelativeLayout BMIR= view.findViewById(R.id.BMIRel);
        Double weightBMI= weight;
        Double heightBMI= height;
        Double BMI= weightBMI/(heightBMI*heightBMI);

        if(BMI<18.5){
            BMIR.setBackgroundColor(Color.GRAY);
            BMIVal.setText("Under Weight");

        }
        else if(BMI>18.5 && BMI<24.9){

            BMIR.setBackgroundColor(Color.GREEN);
            BMIVal.setText("Normal Weight");

        }

        else if(BMI>25.0 ){

            BMIR.setBackgroundColor(Color.RED);
            BMIVal.setText("Over Weight");

        }




    }

}