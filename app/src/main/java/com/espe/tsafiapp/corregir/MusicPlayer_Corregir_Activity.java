package com.espe.tsafiapp;


import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MusicPlayer_Corregir_Activity extends AppCompatActivity {

    TextView currentTime, totalTime, currentTime2, totalTime2;
    SeekBar seekBar, seekBar2;
    ImageView playPause, stopBtn, playPause2, stopBtn2, btnGrabar;
    String pathAudio;

    MediaPlayer mediaPlayer = new MediaPlayer();
    MediaPlayer mediaPlayer2 = new MediaPlayer();

    private MediaRecorder grabacion;

    File archivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player_corregir);

        currentTime = findViewById(R.id.current_time);
        totalTime = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        playPause = findViewById(R.id.play_pause);
        stopBtn = findViewById(R.id.stop);

        currentTime2 = findViewById(R.id.current_time2);
        totalTime2 = findViewById(R.id.total_time2);
        seekBar2 = findViewById(R.id.seek_bar2);
        playPause2 = findViewById(R.id.play_pause2);
        stopBtn2 = findViewById(R.id.stop2);

        btnGrabar = findViewById(R.id.btnGrabar);


        pathAudio = getIntent().getStringExtra("SONG");
        Log.d("laptm","despues de song");

        setResourcesWitchMusic();
        setResourcesWitchMusicRespk();

        Log.d("laptm","despues iniciar recursos");
        MusicPlayer_Corregir_Activity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(mediaPlayer!=null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTime.setText(convertTOMMSS(mediaPlayer.getCurrentPosition()+""));

                    if(mediaPlayer.isPlaying()) {
                        playPause.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    } else {
                        playPause.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                    }
                }
                if(mediaPlayer2!=null) {
                    seekBar2.setProgress(mediaPlayer2.getCurrentPosition());
                    currentTime2.setText(convertTOMMSS(mediaPlayer2.getCurrentPosition()+""));

                    if(mediaPlayer2.isPlaying()) {
                        playPause2.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    } else {
                        playPause2.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                    }
                }
                new Handler().postDelayed(this,100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mediaPlayer != null && b){
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mediaPlayer2 != null && b){
                    mediaPlayer2.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    void setResourcesWitchMusic(){
        playPause.setOnClickListener(v -> pausePlay());
        stopBtn.setOnClickListener(v -> stopPlay());
        playMusic();
        //totalTime.setText(String.valueOf(convertTOMMSS(mediaPlayer.getDuration())));
    }

    void setResourcesWitchMusicRespk(){
        playPause2.setOnClickListener(v -> pausePlay2());
        stopBtn2.setOnClickListener(v -> stopPlay2());
        btnGrabar.setOnClickListener(v -> grabar());
        //playMusic2();
        //totalTime.setText(String.valueOf(convertTOMMSS(mediaPlayer.getDuration())));
    }

    private void playMusic(){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(pathAudio);
            mediaPlayer.prepare();
            //mediaPlayer.start();
            totalTime.setText(convertTOMMSS(mediaPlayer.getDuration()+""));
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playMusic2(){
        mediaPlayer2.reset();
        try {
            mediaPlayer2.setDataSource(archivo.getAbsolutePath());
            mediaPlayer2.prepare();
            //mediaPlayer.start();
            totalTime2.setText(convertTOMMSS(mediaPlayer2.getDuration()+""));
            seekBar2.setProgress(0);
            seekBar2.setMax(mediaPlayer2.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlay(){
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
    }

    private void stopPlay2(){
        mediaPlayer2.seekTo(0);
        mediaPlayer2.pause();
    }

    private void pausePlay(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }

    private void pausePlay2(){
        if(mediaPlayer2.isPlaying())
            mediaPlayer2.pause();
        else
            mediaPlayer2.start();
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    private void grabar(){
        if(grabacion==null){
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            archivo = new File(pathAudio);
            String auxFolfer = archivo.getParent();
            String auxName = archivo.getName().split("\\.")[0];
            try {
                archivo = null;
                archivo = new File(auxFolfer+"/"+auxName+"rpk.wav");
                if (archivo.exists()) {
                    archivo.delete();
                    //archivo = new File(auxFolfer+"/"+auxName+"rpk.wav");
                }
                archivo.createNewFile();

            } catch (IOException e) {
                Log.e("archivo", "crear archivo failed");
                Log.d("laptm", e.toString());
            }
            Log.d("laptm2", archivo.getAbsolutePath());
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
            grabacion=null;
            playMusic2();
        }
    }


    public static String convertTOMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }
}