package com.example.martinvieth.lektions.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;

import com.example.martinvieth.lektions.ThisApp;
import com.example.martinvieth.lektions.logic.Galgelogik;

/**
 * Created by thomas on 19/01/16.
 */
public class HentOrdService extends IntentService {

    ThisApp app;
    Galgelogik gl;



    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public HentOrdService(String name) {
        super(name);
    }

    public HentOrdService() {
        super("HentOrdService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        app = ThisApp.getInstance();
        gl = ThisApp.getLogicInstance();

        String data = intent.getDataString();
        System.out.println("DAAAATAAAA: "+data);

        if(app.isNetworkAvailable()) {
            try {
                gl.hentOrd(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
