package com.example.martinvieth.lektions.ui;

import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;

import com.example.martinvieth.lektions.R;
import com.example.martinvieth.lektions.ThisApp;
import com.example.martinvieth.lektions.logic.Galgelogik;


public class Activity_Main extends FragmentActivity {

    private static Galgelogik gl;
    MediaPlayer mp;
    private ThisApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        app = ThisApp.getInstance();
        ThisApp.setMain(this);

        setContentView(R.layout.akt_main);
        FrameLayout fl = (FrameLayout) findViewById(R.id.fragWindow);
        fl.setPadding(0,5,0,0);

        if (savedInstanceState == null) {
            Fragment fragment = new Frag_menu();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragWindow, fragment)  // tom container i layout
                    .commit();
        }

        setTitle("Galgespil");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // brugeren vil navigere op i hierakiet
        }
        return super.onOptionsItemSelected(item);

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void playSound(int id) {
        if (!app.isMuted()) {
            mp = MediaPlayer.create(this, id);
            mp.start();
        }
    }

    public boolean isPlaying(){
        return mp != null && mp.isPlaying();
    }

    public void stopMediaPlayer() {
        if(mp != null) mp.stop();
        mp = null;
    }

    public void pauseMediaPlayer() { if(isPlaying()) mp.pause(); }

    public void continueMediaPlayer() { if(!isPlaying()) mp.start(); }



}
