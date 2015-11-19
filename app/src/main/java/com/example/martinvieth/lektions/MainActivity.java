package com.example.martinvieth.lektions;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.MenuItem;
import android.view.Window;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_main);

        if (savedInstanceState == null) {
            Fragment fragment = new Frag_menu();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragWindow, fragment)  // tom container i layout
                    .commit();
        }

        setTitle("Galgespil");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // brugeren vil navigere op i hierakiet
        }
        return super.onOptionsItemSelected(item);
    }

}
