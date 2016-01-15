package com.example.martinvieth.lektions.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.martinvieth.lektions.R;


public class Frag_menu extends Fragment implements View.OnClickListener {

    Button btnNewGame, btnInfo, btnWords;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_menu, container, false);

        btnNewGame = (Button) rod.findViewById(R.id.btnNewGame);
        btnNewGame.setText("Nyt spil");

        btnWords = (Button) rod.findViewById(R.id.btnWords);
        btnWords.setText("Hent ord");

        btnInfo = (Button) rod.findViewById(R.id.btnInfo);
        btnInfo.setText("Info");

        btnNewGame.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnWords.setOnClickListener(this);

        return rod;
    }

    @Override
    public void onClick(View v) {
        if(v == btnNewGame){
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragWindow, new Frag_Galgespil())
                    .addToBackStack(null)
                    .commit();
        }

        else if(v == btnInfo){
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragWindow, new Frag_info())
                    .addToBackStack(null)
                    .commit();
        }

        else if(v == btnWords){
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragWindow, new Frag_HentOrdFraDR())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
