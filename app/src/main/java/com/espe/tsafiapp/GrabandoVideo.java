package com.espe.tsafiapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GrabandoVideo extends AppCompatActivity {
    String fechaActual;
    private VideoView mVideoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabando_video);

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(GrabandoVideo.this,
                    new String[]{Manifest.permission.CAMERA},
                    1000);
        }

        mVideoView = findViewById(R.id.videoView3);

        Date todayDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        fechaActual = sdf.format(todayDate);

    }

    private File crearDirectorio() {
        File path = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        File patchFecha = null;
        if(!(new File(path, fechaActual).exists())){
            patchFecha = new File(path, fechaActual);
            patchFecha.mkdir();
        }
        return patchFecha;
    }


    public void grabarV(View v){
        lanzarVideo.launch(new Intent(MediaStore.ACTION_VIDEO_CAPTURE));
    }

    ActivityResultLauncher<Intent> lanzarVideo = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                File storageDirectory = crearDirectorio();

                try {
                    File videoFile = File.createTempFile(fechaActual,".mp4", storageDirectory);
                    Uri uriVideo = result.getData().getData();

                    InputStream inputStream = getContentResolver().openInputStream(uriVideo);
                    FileOutputStream fileOutputStream = new FileOutputStream(videoFile);
                    byte[] buf = new byte[1024];
                    int len;

                    //copio e buffer
                    while ((len = inputStream.read(buf)) > 0) {
                        fileOutputStream.write(buf, 0, len);
                    }
                    fileOutputStream.close();
                    inputStream.close();

                    mVideoView.setVideoURI(uriVideo);
                    mVideoView.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    });
}