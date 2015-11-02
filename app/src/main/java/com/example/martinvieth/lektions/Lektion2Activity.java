package com.example.martinvieth.lektions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Lektion2Activity extends Activity implements OnClickListener {
Button backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lektion2);

        backBtn = (Button) findViewById(R.id.buttonBack);
        backBtn.setOnClickListener(this);
        findViewById(R.id.buttonBack).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Lektion2Activity.this, MainActivity.class));
            }
        });
    }
    public void onClick(View v) {

    }

}
