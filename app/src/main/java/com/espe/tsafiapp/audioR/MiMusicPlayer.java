package com.espe.tsafiapp.audioR;

import android.media.MediaPlayer;

public class MiMusicPlayer {
    static MediaPlayer instance;

    public static MediaPlayer getInstance() {
        if (instance == null) {
            instance = new MediaPlayer();
        }
        return instance;
    }

    public static int currentIndex = -1;
}
