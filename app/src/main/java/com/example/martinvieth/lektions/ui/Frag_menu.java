package com.example.martinvieth.lektions.ui;

import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.martinvieth.lektions.R;


public class Frag_menu extends Fragment implements View.OnClickListener {

    Button btnNewGame, btnInfo, btnWords;
    ImageView menuIV;


    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View rod = i.inflate(R.layout.frag_menu, container, false);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;



        btnNewGame = (Button) rod.findViewById(R.id.btnNewGame);
        btnNewGame.setText("Nyt spil");

        menuIV = (ImageView) rod.findViewById(R.id.imageView);
        menuIV.setMinimumWidth(width/3);
        menuIV.setMinimumHeight(width/3);
        menuIV.setImageResource(R.mipmap.ic_launcher);

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
