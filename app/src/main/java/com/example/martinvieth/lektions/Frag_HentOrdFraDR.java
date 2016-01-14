package com.example.martinvieth.lektions;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Frag_HentOrdFraDR extends Fragment implements View.OnClickListener{

    //Tilføjer textview, knap og arraylist til at putte ord fra hentOrdFraDr() ind i
    ListView ordFraDr;
    TextView hentOrdStatus;
    Button btnOrd;
    ArrayList<String> ord = new ArrayList();
    static String selectedWord;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_hent_ord_fra_dr, container, false);


        ordFraDr = (ListView) rod.findViewById(R.id.ordFraDr);
        ordFraDr.setClickable(true);
        hentOrdStatus = (TextView) rod.findViewById(R.id.hentOrdStatus);
        hentOrdStatus.setTextColor(Color.rgb(248, 248, 255));
        hentOrdStatus.setText("Henter ord...");
        btnOrd = (Button) rod.findViewById(R.id.btnOrd);
        btnOrd.setOnClickListener(this);


        //AsyncTask, taget fra opgave-vejledningen på campusnet

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object... args0) {
                try {
                    Frag_Galgespil.getLogic().hentOrdFraDr();
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
        return rod;
    }

    @Override
    public void onClick(View v) {

        //Knap-funktionalitet, henter ordene via getMuligeOrd(), putter dem ind i arraylist "ord"
        if (v == btnOrd) {
            ord = Frag_Galgespil.getLogic().getMuligeOrd();
            ordFraDr.setAdapter(new ArrayAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, Frag_Galgespil.getLogic().getMuligeOrd()));
        }
    }
}



