package com.example.martinvieth.lektions.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.martinvieth.lektions.ThisApp;
import com.example.martinvieth.lektions.logic.Galgelogik;
import com.example.martinvieth.lektions.ui.Frag_HentOrdFraDR;

/**
 * Created by thomas on 19/01/16.
 */
public class HentOrdBroadcastReceiver extends BroadcastReceiver {

    private Galgelogik gl;
    private Frag_HentOrdFraDR dr;

    @Override
    public void onReceive(Context context, Intent intent) {
        gl = ThisApp.getLogicInstance();
        //dr = context.
    }


}
