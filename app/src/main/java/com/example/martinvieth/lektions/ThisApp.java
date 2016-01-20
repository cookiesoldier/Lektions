package com.example.martinvieth.lektions;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.martinvieth.lektions.logic.Galgelogik;
import com.example.martinvieth.lektions.ui.Activity_Main;
import com.example.martinvieth.lektions.ui.Frag_Galgespil;

/**
 * Created by thomas on 18/01/16.
 */
public class ThisApp extends Application {

    private static ThisApp app = new ThisApp();
    private static Galgelogik gl = null;
    private static boolean muted = false;
    private static Activity_Main main;

    private ThisApp() {
    }

    public static void setMain(Activity_Main main) {
        ThisApp.main = main;
    }

    public static Activity_Main getMain() {
        return main;
    }

    public static ThisApp getInstance() {
        return app;
    }

    public static Galgelogik getLogicInstance() {
        if(gl == null) {
            gl = new Galgelogik();
        }
        return gl;
    }

    public boolean isMuted() {
        return muted;
    }

    public void mute() {
        muted = true;
    }

    public void unMute() {
        muted = false;
    }

    public boolean isNetworkAvailable() {
       return main.isNetworkAvailable();
    }
}


