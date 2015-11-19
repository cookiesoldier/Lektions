package com.example.martinvieth.lektions;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.martinvieth.lektions.Galgelogik;

public class Frag_Galgespil extends Fragment implements View.OnClickListener{

    Frag_HentOrdFraDR hentOrd = new Frag_HentOrdFraDR();
    static Galgelogik logik = new Galgelogik();
    private TextView txtUsedWords, txtWelcome, txtInfo, txtHiddenWords;
    private Button btnGuess, btnBack, btnPickWord;
    private ImageView galge;
    private EditText etxtGuess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        TableLayout tl = new TableLayout(getActivity());
        galge = new ImageView(getActivity());
        tl.addView(galge);

        txtWelcome = new TextView(getActivity());
        txtWelcome.setText("Velkommen til vores Galgespil!");
        tl.addView(txtWelcome);

        txtInfo = new TextView(getActivity());
        tl.addView(txtInfo);

        txtHiddenWords = new TextView(getActivity());
        tl.addView(txtHiddenWords);

        txtUsedWords = new TextView(getActivity());
        tl.addView(txtUsedWords);

        etxtGuess = new EditText(getActivity());
        etxtGuess.setText("Indtast et bogstav");
        tl.addView(etxtGuess);


        btnGuess = new Button(getActivity());
        btnGuess.setOnClickListener(this);
        btnGuess.setText("Hent et tilfældigt ord");
        tl.addView(btnGuess);

        btnBack = new Button(getActivity());
        btnBack.setOnClickListener(this);
        btnBack.setText("Afslut");
        tl.addView(btnBack);


/*
        setContentView(R.layout.frag_galgespil_ubrugt);
        twBesked = (TextView) findViewById(R.id.twBesked);
        twHiddenWord = (TextView) findViewById(R.id.twHiddenWord);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        tvUsedLetters = (TextView) findViewById(R.id.tvUsedLetters);

        //Img for the game
        imgGalge = (ImageView) findViewById(R.id.imgGalge);
        images = getResources().obtainTypedArray(R.array.galge_imgs);
        imgGalge.setImageResource(images.getResourceId(galgelogik.getAntalForkerteBogstaver(), 0));

        //Set text for Textview
        textView2.setText("Gæt ordet, skriv et bogstav og tryk på knappen");
        tvUsedLetters.setText("Brugte Bogstaver");

         */

        return tl;
    }

    public void onClick(View v) {

        btnGuess.setText("Gæt");
        txtInfo.setText("");

        if(v==btnGuess && !logik.erSpilletSlut()){
            txtHiddenWords.setText(logik.getSynligtOrd());
            logik.gætBogstav(String.valueOf(etxtGuess.getText()));
            etxtGuess.setText("");
            updateGalge();
        }

        if(logik.erSidsteBogstavKorrekt()==true){
            txtUsedWords.setText(logik.getBrugteBogstaver().toString());
            txtHiddenWords.setText(logik.getSynligtOrd());
            etxtGuess.setText("");
        }
        else if(logik.erSidsteBogstavKorrekt()==false){
            txtUsedWords.setText(logik.getBrugteBogstaver().toString());
            updateGalge();
            etxtGuess.setText("");
        }

        if(logik.erSpilletSlut()){
            //game reset
            if(logik.erSpilletVundet()){
                txtInfo.setText("Du har vundet! \nOrdet var "+logik.getOrdet());
            }
            else if(logik.erSpilletTabt()){
                txtInfo.setText("Du har tabt! \nOrdet var " +logik.getOrdet());
            }
            btnGuess.setText("Hent nyt ord");
            logik.nulstil();
            txtHiddenWords.setText("");
            txtUsedWords.setText("");
        }

        if(v == btnBack){
            logik.nulstil();
            etxtGuess.setText("");
            txtHiddenWords.setText("");
            txtUsedWords.setText("");
            updateGalge();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragWindow, new Frag_menu())
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void updateGalge() {
        if (logik.getAntalForkerteBogstaver() == 0) {
            galge.setImageResource(R.mipmap.galge);
        }
        if (logik.getAntalForkerteBogstaver() == 1) {
            galge.setImageResource(R.mipmap.forkert1);
        }
        if (logik.getAntalForkerteBogstaver() == 2) {
            galge.setImageResource(R.mipmap.forkert2);
        }
        if (logik.getAntalForkerteBogstaver() == 3) {
            galge.setImageResource(R.mipmap.forkert3);
        }
        if (logik.getAntalForkerteBogstaver() == 4) {
            galge.setImageResource(R.mipmap.forkert4);
        }
        if (logik.getAntalForkerteBogstaver() == 5) {
            galge.setImageResource(R.mipmap.forkert5);
        }
        if (logik.getAntalForkerteBogstaver() == 6) {
            galge.setImageResource(R.mipmap.forkert6);
        }
    }
}
