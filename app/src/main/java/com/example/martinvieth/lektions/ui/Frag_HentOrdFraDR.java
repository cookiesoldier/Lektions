package com.example.martinvieth.lektions.ui;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.martinvieth.lektions.R;
import com.example.martinvieth.lektions.ThisApp;
import com.example.martinvieth.lektions.helper.Constants;
import com.example.martinvieth.lektions.logic.Galgelogik;
import com.example.martinvieth.lektions.service.HentOrdService;

import java.util.ArrayList;

public class Frag_HentOrdFraDR extends Fragment implements View.OnClickListener{

    //Tilføjer textview, knap og arraylist til at putte ord fra hentOrdFraDr() ind i
    EditText hentOrdUrl;
    ListView ordFraUrl;
    Button btnOrd;
    ArrayList<String> ord = new ArrayList<>();
    Galgelogik gl;
    String url;
    private ProgressDialog progress;

    private class HentOrdBroadcastReceiver extends BroadcastReceiver {

        private HentOrdBroadcastReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            gl = ThisApp.getLogicInstance();
            hentOrdUrl.setText("");
            dismissLoadingDialog();
            ord = gl.getMuligeOrd();
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ord);
            ordFraUrl.setAdapter(itemsAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_hent_ord_fra_dr, container, false);


        ordFraUrl = (ListView) rod.findViewById(R.id.ordFraDr);
        ordFraUrl.setClickable(true);
        hentOrdUrl = (EditText) rod.findViewById(R.id.hentOrdUrl);
        hentOrdUrl.setTextColor(Color.rgb(248, 248, 255));
        btnOrd = (Button) rod.findViewById(R.id.btnOrd);
        btnOrd.setOnClickListener(this);
        dismissLoadingDialog();

        if (gl == null){
            gl = ThisApp.getLogicInstance();
        }

        IntentFilter intentFilter = new IntentFilter(Constants.HENT_ORD_SERVICE);
        HentOrdBroadcastReceiver rec = new HentOrdBroadcastReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(rec, intentFilter);

        return rod;
    }

    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(getActivity());
            progress.setTitle("Loading");
            progress.setMessage("Henter ord..");
        }
        progress.show();
    }


    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    @Override
    public void onClick(View v) {

        if (v == btnOrd) {
            gl.getMuligeOrd();
            showLoadingDialog();
            url = String.valueOf(hentOrdUrl.getText());
            if(url.startsWith("www")){
                url = "http://"+hentOrdUrl.getText();
            }

            Intent serviceIntent;
            serviceIntent = new Intent(getActivity(), HentOrdService.class);
            serviceIntent.setData(Uri.parse(url));
            getActivity().startService(serviceIntent);

            /*
            AsyncTask task = new AsyncTask() {
                @Override
                protected Object doInBackground(Object... args0) {
                    try {
                        gl.hentOrd(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return "Success";
                }
                @Override
                protected void onPostExecute(Object resultat) {

                }
            };
            task.execute();
            */

            ord = gl.getMuligeOrd();
            ordFraUrl.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, ord));
        }
    }
}



