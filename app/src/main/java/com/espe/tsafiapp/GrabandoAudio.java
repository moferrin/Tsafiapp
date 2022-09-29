package com.espe.tsafiapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GrabandoAudio extends AppCompatActivity implements MediaPlayer.OnCompletionListener {


    private Button btnGrabar;

    String fechaActual;
    File archivo;
    private MediaRecorder grabacion;
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

    //@RequiresApi(api = Build.VERSION_CODES.N)
    public void grabar(View v){
        if(grabacion==null) {
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            File path = crearDirectorio();
            try {
                archivo = File.createTempFile("temporal", ".3gp", path);
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
            } catch (IOException e) {
                Log.e("archivo", "no se encontro archivo");
            }

            try {
                player.prepare();
            } catch (IOException e) {
                Log.e("media", "no se prepraro el reproductor");
            }
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