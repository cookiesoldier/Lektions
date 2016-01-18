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

    //Tilføjer textview, knap og arraylist til at putte ord fra hentOrdFraDr() ind i
    EditText hentOrdUrl;
    ListView ordFraUrl;
    Button btnOrd;
    ArrayList<String> ord = new ArrayList<>();
    Galgelogik gl;
    String url;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_hent_ord_fra_dr, container, false);


        ordFraUrl = (ListView) rod.findViewById(R.id.ordFraDr);
        ordFraUrl.setClickable(true);
        hentOrdUrl = (EditText) rod.findViewById(R.id.hentOrdUrl);
        hentOrdUrl.setTextColor(Color.rgb(248, 248, 255));
        btnOrd = (Button) rod.findViewById(R.id.btnOrd);
        btnOrd.setOnClickListener(this);

        if (gl == null){
            gl = new Galgelogik();
        }

        return rod;
    }

    @Override
    public void onClick(View v) {

        if (v == btnOrd) {
            url = String.valueOf(hentOrdUrl.getText());
            if(url.startsWith("www")){
                url = "http://"+hentOrdUrl.getText();
            }
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
                    hentOrdUrl.setText("");
                }
            };
            task.execute();
            ord = gl.getMuligeOrd();
            ordFraUrl.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, ord));
        }
    }
}



