package com.espe.tsafiapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.espe.tsafiapp.grabaciones.opcionesGrabacion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrabandoVideo extends AppCompatActivity {
    String fechaActual;
    private VideoView mVideoView;
    ProgressDialog progressDialog;
    MediaController mediaController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabando_video);
        mediaController = new MediaController(this);

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(GrabandoVideo.this,
                    new String[]{Manifest.permission.CAMERA},
                    1000);
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        mVideoView = findViewById(R.id.videoView3);

        Intent intent = getIntent();
        fechaActual = intent.getStringExtra(opcionesGrabacion.FECHA_ACTUAL);
    }

    private File crearDirectorio() {
        File path = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/media/com.espe.tsafiapp");
        File patchFecha = new File(path, fechaActual);
        if(!(patchFecha.exists())){
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

                    ///File videoFile = File.createTempFile(fechaActual,".mp4", storageDirectory);
                    File videoFile = new File(storageDirectory.getAbsolutePath()+"/"+fechaActual+".mp4");
                    videoFile.createNewFile();
                    //Environment.getExternalStorageDirectory().getPath() + "/Android/media/com.espe.tsafiapp
                    Uri uriVideo = result.getData().getData();


                    InputStream inputStream = getContentResolver().openInputStream(uriVideo);
                    FileOutputStream fileOutputStream = new FileOutputStream(videoFile);
                    byte[] buf = new byte[1024];
                    int len;

                    //copio el buffer
                    while ((len = inputStream.read(buf)) > 0) {
                        fileOutputStream.write(buf, 0, len);
                    }
                    fileOutputStream.close();
                    inputStream.close();

                    //testeo(videoFile);

                    mVideoView.setVideoURI(uriVideo);
                    mVideoView.setMediaController(mediaController);
                    mediaController.setAnchorView(mVideoView);


                    mVideoView.start();

                } catch (Exception e) {
                    Log.d("laptm2", e.toString());
                    e.printStackTrace();
                }
            }
        }
    });



    private void testeo (File f) {

        progressDialog.show();


        Log.d("laptm", "cheeee");
        String filePath = f.getAbsolutePath().toString();
        String name = f.getName();

        File file = f;
        Log.d("laptm", "cheeee2");

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Log.d("laptm", "cheeee3");
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Log.d("laptm", "cheeee3_4");
        Call<ServerResponse> call= getResponse.uploadFile(fileToUpload, filename);

        Log.d("laptm", "cheeee4");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Log.d("laptm", "cheeee6");
                ServerResponse serverResponse = response.body();
                Log.d("laptm", "cheeee7");
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.d("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });

    }

}