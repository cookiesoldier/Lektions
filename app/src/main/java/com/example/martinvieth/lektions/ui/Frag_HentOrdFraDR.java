package com.example.martinvieth.lektions.ui;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.martinvieth.lektions.R;
import com.example.martinvieth.lektions.logic.Galgelogik;

import java.util.ArrayList;

public class Frag_HentOrdFraDR extends Fragment implements View.OnClickListener{

    //Tilføjer textview, knap og arraylist til at putte ord fra hentOrd() ind i
    ListView ordFraUrl;
    EditText hentOrdUrl;
    Button btnOrd;
    ArrayList<String> ord = new ArrayList();
    String ordUrl;
    Galgelogik gl;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_hent_ord_fra_dr, container, false);

        ordFraUrl = (ListView) rod.findViewById(R.id.ordFraDr);
        ordFraUrl.setClickable(true);
        hentOrdUrl = (EditText) rod.findViewById(R.id.hentOrdUrl);
        hentOrdUrl.setTextColor(Color.rgb(248, 248, 255));
        btnOrd = (Button) rod.findViewById(R.id.btnOrd);
        btnOrd.setOnClickListener(this);

        if (gl == null) {
            gl = new Galgelogik();
        }

        return rod;
    }

    @Override
    public void onClick(View v) {

        //Knap-funktionalitet, henter ordene via getMuligeOrd(), putter dem ind i arraylist "ord"
        if (v == btnOrd) {
            ordUrl = String.valueOf(hentOrdUrl.getText());
            if(ordUrl.startsWith("www")){
                ordUrl = "http://"+hentOrdUrl.getText();
            }
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object... args0) {
                    try {
                        gl.hentOrd(ordUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
//                        Toast.makeText(getActivity(), "Der skete en fejl. Prøv igen", Toast.LENGTH_SHORT).show();
                    }
                    return "Success";
                }


                @Override
                protected void onPostExecute(Object resultat) {
                    hentOrdUrl.setText("");
                }
            };
            asyncTask.execute();
            ord = gl.getMuligeOrd();
            ordFraUrl.setAdapter(new ArrayAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, ord));
        }
    }
}



