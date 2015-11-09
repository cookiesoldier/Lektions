package com.example.martinvieth.lektions;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class listActivity extends Activity implements AdapterView.OnItemClickListener{


    ListView listInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //ListView
        listInfo = (ListView) findViewById(R.id.listInfo);
        listInfo.setOnItemClickListener(this);
        ArrayList<String> ord = galgeSpilActivity.galgelogik.getMuligeOrd();
        listInfo.setAdapter(new ArrayAdapter<String>(this,
                simple_list_item_1,  android.R.id.text1, ord ));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
