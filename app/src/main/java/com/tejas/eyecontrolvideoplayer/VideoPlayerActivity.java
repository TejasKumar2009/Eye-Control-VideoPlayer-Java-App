package com.tejas.eyecontrolvideoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.pd.lookatme.LookAtMe;

public class VideoPlayerActivity extends AppCompatActivity {
    private LookAtMe lookAtMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        // Finding ids
        lookAtMe = findViewById(R.id.lookme);

        // Getting Intent Data
        Intent intent = getIntent();
        String videoPath = intent.getStringExtra("videoPath");

        lookAtMe.init(VideoPlayerActivity.this);
        lookAtMe.setVideoURI(Uri.parse(videoPath));
//        lookAtMe.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
        lookAtMe.start();
        lookAtMe.setLookMe();

    }
}