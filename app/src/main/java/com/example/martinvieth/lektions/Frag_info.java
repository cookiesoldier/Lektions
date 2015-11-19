package com.example.martinvieth.lektions;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.martinvieth.lektions.R;


public class Frag_info extends Fragment implements View.OnClickListener{

    private Button btnBack;
    private TextView tv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TableLayout tl = new TableLayout(getActivity());

        btnBack = new Button(getActivity());
        btnBack.setText("Tilbage");
        btnBack.setOnClickListener(this);

        tv = new TextView(getActivity());
        tv.setTextSize(20);
        tv.setText("Der er et lille udvalg af ord, som du kan spille med. \n \nVil du have flere ord, kan du trykke på Hent Ord-knappen.");

        tl.addView(tv);
        tl.addView(btnBack);
        return tl;
    }

    @Override
    public void onClick(View v) {
        if(v == btnBack){
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragWindow, new Frag_menu())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
