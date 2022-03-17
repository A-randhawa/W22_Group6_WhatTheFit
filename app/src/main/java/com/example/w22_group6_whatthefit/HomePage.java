package com.example.w22_group6_whatthefit;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        VideoView videoView = findViewById(R.id.videoView1);
        videoView.setVideoPath("android.resources://"+getPackageName()+"/"+R.raw.workout1);
       // videoView.setVideoURI(Uri.parse());
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
       // videoView.setOnPreparedListener(mediaPlayer -> videoView.start());

    }
}