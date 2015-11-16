package com.example.martinvieth.lektions;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ordFraDR extends Activity implements View.OnClickListener{

    //Tilføjer textview, knap og arraylist til at putte ord fra hentOrdFraDr() ind i
    ListView ordFraDr;
    TextView hentOrdStatus;
    Button btnOrd;
    ArrayList<String> ord = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ord_fra_dr);


        ordFraDr = (ListView) findViewById(R.id.ordFraDr);
        hentOrdStatus = (TextView) findViewById(R.id.hentOrdStatus);
        hentOrdStatus.setText("Henter ord...");
        btnOrd = (Button) findViewById(R.id.btnOrd);
        btnOrd.setOnClickListener(this);


        //AsyncTask, taget fra opgave-vejledningen på campusnet

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object... args0) {
                try {
                    galgeSpilActivity.galgelogik.hentOrdFraDr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "Success";
            }


            @Override
            protected void onPostExecute(Object resultat) {
                hentOrdStatus.setText("Ord hentet!");
            }
        };
        asyncTask.execute();
    }

    @Override
    public void onClick(View v) {

        //Knap-funktionalitet, henter ordene via getMuligeOrd(), putter dem ind i arraylist "ord"
        if (v == btnOrd) {
            ord = galgeSpilActivity.galgelogik.getMuligeOrd();
            ordFraDr.setAdapter(new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, galgeSpilActivity.galgelogik.getMuligeOrd()));

        }
    }
}



