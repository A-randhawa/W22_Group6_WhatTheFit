package com.example.w22_group6_whatthefit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomePage extends AppCompatActivity {

    AnimatedBottomBar bottom_bar;
    private static final String TAG = "HomePage";

    FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        bottom_bar = findViewById(R.id.animatedBottomBar);
        /*VideoView videoView = findViewById(R.id.videoView1);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.workoutvid2);
        // videoView.setVideoURI(Uri.parse());
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);*/
        // videoView.setOnPreparedListener(mediaPlayer -> videoView.start());
        //image slider
        SliderView sliderView;
        SliderView txtSlider;
        //using an array for images
        int[] images = {
                R.drawable.gyms,
                R.drawable.healhtyfood,
                R.drawable.workouts,
                R.drawable.music};

        String[] txtViews ={
                "Gyms Near You",
                "Cook Books For A Healthy Diet",
                "Workout Videos",

        };
        sliderView = findViewById(R.id.image_slider);
        //txtSlider = findViewById(R.id.txtViewsSlider);


        SliderAdapter sliderAdapter = new SliderAdapter(images);
        //text slider adapter
        //txtSliderAdapter textSlider = new txtSliderAdapter(txtSlider);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();






        sliderAdapter.setOnItemClickListener(new SliderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            String p = String.valueOf(position);
           /// Toast.makeText(getApplicationContext(), p, Toast.LENGTH_SHORT).show();
                if(p.equals("2")){
                    Intent intent = new Intent(HomePage.this,VideoActivity.class);
                    startActivity(intent);

                }

        } });







        bottom_bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
             @Override
             public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                 Fragment fragment=null;

                 switch (newTab.getId()){
                     case R.id.tab_home:
                         Log.i(TAG,"home");
                         fragment= new HomeFragment();
                         break;
                     case R.id.tab_stats:
                         Log.i(TAG,"stats");
                         fragment= new StatsFragment();
                         break;
                     case  R.id.tab_profile:
                         Log.i(TAG,"profile");
                         fragment= new ProfileFragment();

                         break;
                 }
                 if(fragment!=null){

                     fragmentManager=getSupportFragmentManager();
                     fragmentManager.beginTransaction().replace(R.id.relativeLayout,fragment).commit();
                 }
                 else
                 {
                     Log.d(TAG,"Error in creating fragment");
                 }

             }

             @Override
             public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

             }
         });



    }

}