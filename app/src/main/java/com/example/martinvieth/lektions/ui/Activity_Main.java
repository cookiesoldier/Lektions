package com.example.martinvieth.lektions.ui;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.view.MenuItem;
import android.view.Window;

import com.example.martinvieth.lektions.R;
import com.example.martinvieth.lektions.logic.Galgelogik;


public class Activity_Main extends Activity {

    private static Galgelogik gl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.akt_main);

        if (savedInstanceState == null) {
            Fragment fragment = new Frag_menu();
            getFragmentManager().beginTransaction()
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

    interface AppMediaPlayer {
        void playSound();
        void stopMediaPlayer();
    }


    public void playSound(int id) {
        MediaPlayer mp = MediaPlayer.create(this, id);
        mp.start();
    }

    public void stopMediaPlayer() {

    }



}
