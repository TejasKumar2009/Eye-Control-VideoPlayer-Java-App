package com.tejas.eyecontrolvideoplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pd.lookatme.LookAtMe;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private LookAtMe lookAtMe;
    private static final int SELECT_VIDEO = 100;
    private String selectedVideoPath;


    Button video_file_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding ids
        video_file_upload = findViewById(R.id.video_file_upload);

        // Video Url Button Click Listener
        video_file_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SELECT_VIDEO);

                    lookAtMe.init(MainActivity.this);
                    lookAtMe.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
                    lookAtMe.start();
                    lookAtMe.setLookMe();


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("errors", e.toString());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                selectedVideoPath = getPath(data.getData());

                try {
                    if(selectedVideoPath == null) {
                        Log.e("errors","selected video path = null!");

                        finish();
                    } else {
                        Log.d("mannu", selectedVideoPath);
                        Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
                        intent.putExtra("videoPath", selectedVideoPath);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    //#debug
                    e.printStackTrace();
                }
            }
        }
        finish();
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
    }

}
