package com.example.martinvieth.lektions.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;

/**
 * Created by thomas on 19/01/16.
 */
public class HentOrdService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    String name = "lol";

    public HentOrdService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String data = intent.getDataString();
    }
}
