package com.espe.tsafiapp;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

/*
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;
*/

//port net.gotev.uploadservice.MultipartUploadRequest;
/*
import net.gotev.uploadservice.data.UploadNotificationConfig;
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest;*/
//port net.gotev.uploadservice.UploadNotificationConfig;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GrabandoAudio extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    ProgressDialog progressDialog;

    private static final String CHANNEL_ID = "1";
    private Button btnGrabar;

    String fechaActual;
    File archivo;
    private MediaRecorder grabacion;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabando_audio);

        btnGrabar = (Button)findViewById(R.id.btnGrabarOn);

        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
            (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
                 ActivityCompat.requestPermissions(GrabandoAudio.this,
                         new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO,
                                 Manifest.permission.READ_EXTERNAL_STORAGE},
                         1000);
        }

        Date todayDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        fechaActual = sdf.format(todayDate);

        //Toast.makeText(getApplicationContext(),fechaActual.toString(),Toast.LENGTH_SHORT).show();
/*
        Toast.makeText(getApplicationContext(),Environment.getDataDirectory()
                .getAbsolutePath().toString(),Toast.LENGTH_SHORT).show();
*/
        //Toast.makeText(getApplicationContext(),getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(),Toast.LENGTH_SHORT).show();

    }

    private File crearDirectorio() {
        File path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File patchFecha = null;
        if(!(new File(path, fechaActual).exists())){
            patchFecha = new File(path, fechaActual);
            patchFecha.mkdir();
        }
        return patchFecha;
    }

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


    //@RequiresApi(api = Build.VERSION_CODES.N)
    public void grabar(View v){
        if(grabacion==null) {
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            File path = crearDirectorio();
            try {
                archivo = null;
                archivo = File.createTempFile("temporal", ".wav", path);
            } catch (IOException e) {
                Log.e("archivo", "crear archivo failed");
            }
            grabacion.setOutputFile(archivo.getAbsolutePath());
            try {
                grabacion.prepare();
            } catch (IOException e) {
                Log.e("prepare", "prepare() failed");
            }
            grabacion.start();
            Toast.makeText(getApplicationContext(), "Grabando...", Toast.LENGTH_SHORT).show();
            btnGrabar.setBackgroundResource(R.drawable.icon_grab_on);
        } else {
            btnGrabar.setBackgroundResource(R.drawable.icon_grab_off);
            grabacion.stop();
            grabacion.release();
            player = new MediaPlayer();
            player.setOnCompletionListener(this);
            try {
                player.setDataSource(archivo.getAbsolutePath());
                testeo(archivo);
                //teste2(archivo);
            } catch (Exception e) {
                Log.d("archivo", "no se encontro archivo");
                Log.d("laptm", e.toString());
            }

            try {
                player.prepare();
            } catch (IOException e) {
                Log.d("media", "no se prepraro el reproductor");
            }
            grabacion=null;
        }
    }
    public void onCompletion(MediaPlayer mp) {
        /*b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        tv1.setText("Listo");*/
    }
    public void reproducir(View view){
        player.start();
        /*MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            //mediaPlayer.setDataSource(archivoSalida);
            //mediaPlayer.prepare();
        }catch (IOException e){

        }*/
        //mediaPlayer.start();
        Toast.makeText(getApplicationContext(), "Reproduciendo...", Toast.LENGTH_SHORT).show();
    }






}