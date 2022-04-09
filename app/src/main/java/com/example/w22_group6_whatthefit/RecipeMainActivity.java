package com.example.w22_group6_whatthefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.w22_group6_whatthefit.databinding.ActivityMainRecipeBinding;

public class RecipeMainActivity extends AppCompatActivity {

    ActivityMainRecipeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] recipeName = {"Herbed Chicken Marsala","Oil And Vinegar Slaw",
                "Pan-Seared Salmon with Kale and Apple Salad","" +
                "Lemon-Garlic Shrimp and Grits", "Mixed Berries and Banana Smoothie ",
                "Quinoa Salad"};
        int[] recipeImages = {R.drawable.herbedchickenmarsala,R.drawable.salad,
                R.drawable.salmomn,R.drawable.shrimp,R.drawable.smoothie,R.drawable.quinoa};


        GridAdapter gridAdapter = new GridAdapter(RecipeMainActivity.this,recipeName,recipeImages);
        binding.gridView.setAdapter(gridAdapter);


        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //intent activity to click the image in the grid view and go to their respective layout file
                Context context = RecipeMainActivity.this;
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(context, ChickenRecipe.class);
                        startActivity(intent);
                        return;
                    case 1:
                        intent = new Intent(context, SaladRecipe.class);
                        startActivity(intent);
                        return;
                    case 2:
                        intent = new Intent(context, SalmonRecipe.class);
                        startActivity(intent);
                        return;
                    case 3:
                        intent = new Intent(context, Shrimp.class);
                        startActivity(intent);
                        return;
                    case 4:
                        intent = new Intent(context, SmoothieActivity.class);
                        startActivity(intent);
                        return;

                    case 5:
                        intent = new Intent(context, QuinoaRecipe.class);
                        startActivity(intent);
                        return;


                }

            }
        });

    }

}