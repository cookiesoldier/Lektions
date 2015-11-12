package com.example.martinvieth.lektions;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ordFraDR extends Activity implements View.OnClickListener{

    //Tilføjer textview, knap og arraylist til at putte ord fra hentOrdFraDr() ind i
    TextView ordFraDr;
    Button btnOrd;
    ArrayList<String> ord = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ord_fra_dr);


        ordFraDr = (TextView) findViewById(R.id.ordFraDr);
        btnOrd = (Button) findViewById(R.id.btnOrd);
        btnOrd.setOnClickListener(this);


        //AsyncTask, taget fra opgave-vejledningen på campusnet

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object... args0) {
                try {
                    galgeSpilActivity.galgelogik.hentOrdFraDr();
                    return "Følgende ord er hentet fra DR:" + "\n" ;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Ord blev ikke hentet fra DR!";
                }
            }

            @Override
            protected void onPostExecute(Object resultat) {
                ordFraDr.setText("\n" + resultat);
            }
        };
        asyncTask.execute();
    }

    @Override
    public void onClick(View v) {

        //Knap-funktionalitet, henter ordene via getMuligeOrd(), putter dem ind i arraylist "ord"
        if (v == btnOrd) {
            ord = galgeSpilActivity.galgelogik.getMuligeOrd();
            for (String s : ord){
                ordFraDr.append(s + "\n");
            }
            ordFraDr.setVerticalScrollBarEnabled(true);
        }
    }
}


