package com.example.martinvieth.lektions;

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

public class galgeSpilActivity extends Fragment implements View.OnClickListener{

    static Galgelogik logik = new Galgelogik();
    private TextView txtUsedWords, txtWelcome, txtInfo, txtHiddenWords;
    private Button btnGuess, btnBack;
    private ImageView galge;
    private EditText etxtGuess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        TableLayout tl = new TableLayout(getActivity());

        txtWelcome = new TextView(getActivity());
        txtWelcome.setText("Velkommen til vores Galgespil!");
        tl.addView(txtWelcome);

        txtHiddenWords = new TextView(getActivity());
//        txtHiddenWords.setText(logik.getSynligtOrd());
        tl.addView(txtHiddenWords);

        txtUsedWords = new TextView(getActivity());
//        txtUsedWords.append((CharSequence) logik.getBrugteBogstaver());
        tl.addView(txtUsedWords);

        etxtGuess = new EditText(getActivity());
        tl.addView(etxtGuess);

        btnGuess = new Button(getActivity());
        btnGuess.setOnClickListener(this);
        tl.addView(btnGuess);
/*
        setContentView(R.layout.activity_galge_spil);
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

        if(v==btnGuess&& !logik.erSpilletSlut()){
            System.out.println("Der gættes");
            if(etxtGuess.length() == 1){
                logik.gætBogstav(etxtGuess.getText().toString());
//                galge.setImageResource(.getResourceId(logik.getAntalForkerteBogstaver(), 0));

            }
            galgeGraphicsOpdater();

        }else if(v == btnGuess && logik.erSpilletSlut()){
            //game reset
            logik.nulstil();
            txtHiddenWords.setText("");
            txtUsedWords.setText("");

        }
        etxtGuess.setText("");
    }

    public void galgeGraphicsOpdater() {
        txtHiddenWords.setText(logik.getSynligtOrd());
        txtUsedWords.setText("Brugte Bogstaver"+ logik.getBrugteBogstaver().toString());
        if (logik.erSpilletSlut()) {
            txtWelcome.setText("Vil du spille igen?");
            btnGuess.setText("Nyt Spil");


            if (logik.erSpilletVundet()) {
                txtInfo.setText("Du vandt! woho!");

            } else {
                txtInfo.setText("Du tabte! Boooh!");
                txtHiddenWords.setText(logik.getOrdet());
            }


        }
    }
}
