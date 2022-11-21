package com.espe.tsafiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {

    TextView currentTime, totalTime;
    SeekBar seekBar;
    ImageView playPause, stopBtn;
    String pathAudio;

    MediaPlayer mediaPlayer = new MediaPlayer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        currentTime = findViewById(R.id.current_time);
        totalTime = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        playPause = findViewById(R.id.play_pause);
        stopBtn = findViewById(R.id.stop);


        pathAudio = getIntent().getStringExtra("SONG");
        Log.d("laptm","despues de song");

        setResourcesWitchMusic();

        MusicPlayerActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(mediaPlayer!=null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTime.setText(convertTOMMSS(mediaPlayer.getCurrentPosition()+""));
                    Log.d("laptm", mediaPlayer.getCurrentPosition()+"");
                    if(mediaPlayer.isPlaying()) {
                        playPause.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    } else {
                        playPause.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
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

    }

    void setResourcesWitchMusic(){
        playPause.setOnClickListener(v -> pausePlay());
        stopBtn.setOnClickListener(v -> stopPlay());
        playMusic();
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

    private void stopPlay(){

        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
    }


    private void pausePlay(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }

    public static String convertTOMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }
}