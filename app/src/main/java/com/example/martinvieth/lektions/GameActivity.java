package com.example.martinvieth.lektions;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity implements View.OnClickListener {

    Galgelogik galge;
    ImageView IV1;
    EditText ET1;
    TextView TV1,TV2,TV3,TV4,TV5;
    Button B1,B2;
    TypedArray images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamectivity);

        B1 = (Button) findViewById(R.id.B1);
        B2 = (Button) findViewById(R.id.B2);

        IV1 = (ImageView) findViewById(R.id.IV1);

        TV1 = (TextView) findViewById(R.id.TVresset);
        TV2 = (TextView) findViewById(R.id.TV2);
        TV3 = (TextView) findViewById(R.id.TV3);
        TV4 = (TextView) findViewById(R.id.TV4);
        TV5 = (TextView) findViewById(R.id.TVresset);

        ET1 = (EditText) findViewById(R.id.ET1);

        galge = new Galgelogik();

        B1.setText("Spil");
        B2.setText("Nyt Ord");

        TV2.setText("" + galge.getSynligtOrd());
        TV3.setText("Skriv et bogstav her under og tryk 'Spil' ");
        TV4.setText("Få et nyt ord");
        TV5.setText("Gæt ordet");

        B1.setOnClickListener(this);
        B2.setOnClickListener(this);

        images = getResources().obtainTypedArray(R.array.galge_imgs);


        IV1.setImageResource(images.getResourceId(galge.getAntalForkerteBogstaver(), 0));

    }

    public void onClick(View v) {
        if (v == B1) {
            galge.gætBogstav(ET1.getText().toString());
            if (!galge.erSidsteBogstavKorrekt()){
                TV1.setText("Du gættede forkert");
                IV1.setImageResource(images.getResourceId(galge.getAntalForkerteBogstaver(),0));
            }
            TV2.setText("" + galge.getSynligtOrd());
            TV3.setText("Brugte bogstaver " + galge.getBrugteBogstaver());
            ET1.setText("");

            if(galge.erSpilletSlut()){
                Intent intent;

              //  intent = new Intent(GameActivity.this, RessetActivity.class);
              //  intent.putExtra("erSpilletVundet",galge.erSpilletVundet());
               // intent.putExtra("detRigtigeOrd",galge.getOrdet());

               // startActivity(intent);


            }
        }
        if (v==B2) {
            galge.nulstil();
            TV2.setText("" + galge.getSynligtOrd());
            TV3.setText("Brugte bogstaver " + galge.getBrugteBogstaver());
            ET1.setText("");
            IV1.setImageResource(images.getResourceId(galge.getAntalForkerteBogstaver(), 0));
        }

    }

}