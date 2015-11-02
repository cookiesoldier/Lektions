package com.example.martinvieth.lektions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class galgeSpilActivity extends Activity  implements OnClickListener {

    Button btnBack;
    Button btnGuess;
    EditText eTGuess;
    TextView twBesked;
    TextView textView;
    TextView textView2;
    Galgelogik galgelogik;
    ImageView imgGalge;
    TextView tvUsedLetters;

    TextView twHiddenWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galge_spil);

        galgelogik = new Galgelogik();
        //buttons
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnGuess =(Button) findViewById(R.id.btnGuess);
        btnGuess.setOnClickListener(this);

        //text box for guess
        eTGuess = (EditText) findViewById(R.id.eTGuess);


        //Text view for error or messages
        twBesked = (TextView) findViewById(R.id.twBesked);
        twHiddenWord = (TextView) findViewById(R.id.twHiddenWord);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        tvUsedLetters = (TextView) findViewById(R.id.tvUsedLetters);

        //Img for the game
        imgGalge = (ImageView) findViewById(R.id.imgGalge);



    }
    public void onClick(View v) {
        if(v==btnBack){
            startActivity(new Intent(galgeSpilActivity.this, MainActivity.class));

        }else if(v ==btnGuess && !galgelogik.erSpilletSlut()){
            System.out.println("Der gættes");
            if(eTGuess.length() > 1){
                twBesked.setText("For mange bogstaver!");
            }else if(eTGuess.length() < 1){
                twBesked.setText("Send et bogstav!");
            }else if(eTGuess.length() == 1){
                galgelogik.gætBogstav(eTGuess.getText().toString());

            }
            galgeGraphicsOpgadeter();

        }else if(v == btnGuess && galgelogik.erSpilletSlut()){
            //game reset
            galgelogik.nulstil();
            textView.setText("Velkommen til galge spillet!");
            textView2.setText("Gæt ordet, skriv et bogstav og tryk på knappen");
            btnGuess.setText("Gæt");
            twHiddenWord.setText("");
            tvUsedLetters.setText("");
            imgGalge.setImageResource(R.mipmap.galge);

        }
    }

    public void galgeGraphicsOpgadeter(){
        System.out.println(galgelogik.getAntalForkerteBogstaver());
        if(galgelogik.getAntalForkerteBogstaver()==1){
                imgGalge.setImageResource(R.mipmap.forkert1);
        }else  if(galgelogik.getAntalForkerteBogstaver()==2){
            imgGalge.setImageResource(R.mipmap.forkert2);

        }else  if(galgelogik.getAntalForkerteBogstaver()==3){
            imgGalge.setImageResource(R.mipmap.forkert3);

        }else  if(galgelogik.getAntalForkerteBogstaver()==4){
            imgGalge.setImageResource(R.mipmap.forkert4);

        }else  if(galgelogik.getAntalForkerteBogstaver()==5){
            imgGalge.setImageResource(R.mipmap.forkert5);

        }else  if(galgelogik.getAntalForkerteBogstaver()==6){
            imgGalge.setImageResource(R.mipmap.forkert6);

        }
        twHiddenWord.setText(galgelogik.getSynligtOrd());
        tvUsedLetters.setText(galgelogik.getBrugteBogstaver().toString());
        if(galgelogik.erSpilletSlut()){
            textView2.setText("Vil du spille igen?");
            btnGuess.setText("Nyt Spil");


            if(galgelogik.erSpilletVundet()){
                textView.setText("Du vandt! woho!");

            }else{
                textView.setText("Du tabte! Boooh!");
                twHiddenWord.setText(galgelogik.getOrdet());
            }


        }
    }
}
