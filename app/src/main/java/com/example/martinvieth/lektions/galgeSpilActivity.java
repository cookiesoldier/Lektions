package com.example.martinvieth.lektions;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class galgeSpilActivity extends Activity  implements OnClickListener {

    Button btnBack,btnGuess,btnMore;
    EditText eTGuess;
    TextView twBesked,textView,textView2;
    public static Galgelogik galgelogik;
    ImageView imgGalge;
    TextView tvUsedLetters, twHiddenWord;
    TypedArray images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galge_spil);

        galgelogik = new Galgelogik();
        //buttons
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnGuess = (Button) findViewById(R.id.btnGuess);
        btnGuess.setOnClickListener(this);
        btnMore = (Button) findViewById(R.id.btnMore);
        btnMore.setOnClickListener(this);

        //text box for guess
        eTGuess = (EditText) findViewById(R.id.eTGuess);

////
        //Text view for error or messages
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
    }

    public void onClick(View v) {
        if(v==btnMore){
            startActivity(new Intent(galgeSpilActivity.this, listActivity.class));
        }
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
                imgGalge.setImageResource(images.getResourceId(galgelogik.getAntalForkerteBogstaver(),0));

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

        }
        eTGuess.setText("");
    }

    public void galgeGraphicsOpgadeter() {
        twHiddenWord.setText(galgelogik.getSynligtOrd());
        tvUsedLetters.setText("Brugte Bogstaver"+ galgelogik.getBrugteBogstaver().toString());
        if (galgelogik.erSpilletSlut()) {
            textView2.setText("Vil du spille igen?");
            btnGuess.setText("Nyt Spil");


            if (galgelogik.erSpilletVundet()) {
                textView.setText("Du vandt! woho!");

            } else {
                textView.setText("Du tabte! Boooh!");
                twHiddenWord.setText(galgelogik.getOrdet());
            }


        }
   }
}
