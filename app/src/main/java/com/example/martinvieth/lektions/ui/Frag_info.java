package com.example.martinvieth.lektions.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        tv.setTextSize(17);
        tv.setTextColor(Color.rgb(248, 248, 255));
        tv.setText("Der er et lille udvalg af ord, som du kan spille med. \n" +
                " \nVil du have flere ord, kan du trykke på Hent Ord-knappen. \n" +
                "\n For at spille, skal du trække det bogstav, du vil gætte på, op på billedet af galgen.");

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
