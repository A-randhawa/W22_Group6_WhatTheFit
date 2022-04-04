package com.example.w22_group6_whatthefit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private List<Video> videoList;
    private VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoList= new ArrayList<>();
        viewPager2=findViewById(R.id.viewPager2);

        videoList.add(new Video("android.resource://" + getPackageName() + "/" + R.raw.video1,"Title 1","Simple ab workout"));
        videoList.add(new Video("android.resource://" + getPackageName() + "/" + R.raw.video2,"Title 2","Simple home workout"));

        adapter = new VideoAdapter(videoList);
        viewPager2.setAdapter(adapter);
    }
}