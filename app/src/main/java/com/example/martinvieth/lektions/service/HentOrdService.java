package com.example.martinvieth.lektions.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;
import android.support.v4.content.LocalBroadcastManager;

import com.example.martinvieth.lektions.ThisApp;
import com.example.martinvieth.lektions.helper.Constants;
import com.example.martinvieth.lektions.logic.Galgelogik;

/**
 * Created by thomas on 19/01/16.
 */
public class HentOrdService extends IntentService {

    ThisApp app;
    Galgelogik gl;

    public HentOrdService() {
        super("HentOrdService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        app = ThisApp.getInstance();
        gl = ThisApp.getLogicInstance();

        String data = intent.getDataString();
        System.out.println("DAAAATAAAA: " + data);

        if(app.isNetworkAvailable()) {
            try {
                gl.hentOrd(data);
                Intent broadCastIntent = new Intent(Constants.HENT_ORD_SERVICE);
                LocalBroadcastManager.getInstance(this).sendBroadcast(broadCastIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }
}
