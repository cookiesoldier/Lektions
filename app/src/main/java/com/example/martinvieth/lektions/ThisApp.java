package com.example.martinvieth.lektions;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by thomas on 18/01/16.
 */
public class ThisApp extends Application {

    private static ThisApp app = new ThisApp();
    private boolean muted = false;

    private ThisApp() {
    }

    public static ThisApp getInstance() {
        return app;
    }

    public boolean isMuted() {
        return muted;
    }

    public void mute() {
        muted = true;
       /*
       AudioManager.setMode(AudioManager.MODE_IN_CALL);
        AudioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, true);
        */
    }

    public void unMute() {
        muted = false;
        /*
        AudioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);
        AudioManager.setMode(AudioManager.MODE_NORMAL );
        */
    }
}
