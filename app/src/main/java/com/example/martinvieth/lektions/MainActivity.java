package com.example.martinvieth.lektions;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;



public class MainActivity extends Activity implements View.OnClickListener {



    Button btnNewGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize buttons and checkbox


        btnNewGame = (Button) findViewById(R.id.btnNewGame);

        btnNewGame.setOnClickListener(this);





    }

    public void onClick(View v) {
        if(v == btnNewGame){
            startActivity(new Intent(MainActivity.this, galgeSpilActivity.class));
        }



    }



}
