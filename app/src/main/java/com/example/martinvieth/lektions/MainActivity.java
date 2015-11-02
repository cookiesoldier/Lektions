package com.example.martinvieth.lektions;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
;

public class MainActivity extends Activity implements OnClickListener {

    Button btnOne;
    Button btnSmall;
    Button search;
    Button vejledningBtn;

    TextView textTopLeft;
    TextView textTop;
    EditText editTextOne;
    EditText browserLink;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize buttons and checkbox


        btnOne = (Button) findViewById(R.id.buttonOne);
        btnSmall = (Button) findViewById(R.id.buttonSmall);
        vejledningBtn = (Button) findViewById(R.id.btnVejl);
        textTopLeft = (TextView) findViewById(R.id.textViewLeftTop);

        textTop = (TextView) findViewById(R.id.Overskrift);
        editTextOne = (EditText) findViewById(R.id.editTextOne);

        btnOne.setOnClickListener(this);
        btnSmall.setOnClickListener(this);
        vejledningBtn.setOnClickListener(this);
        //Browser stuff
        browserLink = (EditText) findViewById(R.id.editTextBrowserLink);
        search = (Button) findViewById(R.id.buttonSearch);
        webView = (WebView) findViewById(R.id.webViewFront);

        search.setOnClickListener(this);
        findViewById(R.id.btnVejl).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Lektion2Activity.class));
            }
        });


    }

    public void onClick(View v) {
        if (v == btnOne){
            System.out.println("Navn skiftet til " +editTextOne.getText());

        } else if (v == btnSmall) {
            textTopLeft.setText("Farvel!!");

        }else if(v == search){
            String stuff = browserLink.getText().toString();
            webView.loadUrl(stuff);


        }else if(v == vejledningBtn){

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
