package com.example.martinvieth.lektions.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martinvieth.lektions.R;
import com.example.martinvieth.lektions.ThisApp;
import com.example.martinvieth.lektions.helper.ShakeDetector;
import com.example.martinvieth.lektions.logic.Galgelogik;

public class Frag_Galgespil extends Fragment implements View.OnClickListener, ShakeDetector.OnShakeListener {

    private Galgelogik gl = null;
    private ShakeDetector shakeDetector;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private GalgeView gv = null;
    private Activity_Main main;
    private ThisApp app;
    protected TextView txtUsedLetters, txtInfo, txtHiddenWords;
    protected Button btnBack, btnNewGame;
    protected ImageView galge, btnMute;
    protected ViewGroup container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View rod = inflater.inflate(R.layout.frag_galgespil_ubrugt, container, false);

        this.container = container;
        main = (Activity_Main) getActivity();
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector(this);

        app = ThisApp.getInstance();
        gl = ThisApp.getLogicInstance();

        if (gv == null) {
            gv = new GalgeView(getActivity(), this);
            // gv = (GalgeView) findViewById(R.id.galgeView);
        }

        final FrameLayout fl = new FrameLayout(getActivity());
        TableLayout tl = new TableLayout(getActivity());
        RelativeLayout rl = new RelativeLayout(getActivity());

        btnMute = new ImageView(getActivity());
        btnMute.setOnClickListener(this);
        rl.addView(btnMute);
        rl.setGravity(Gravity.RIGHT);
        if (!app.isMuted()){
            btnMute.setImageResource(R.drawable.ic_volume_up_black_48dp);
        }else{
            btnMute.setImageResource(R.drawable.ic_volume_off_black_48dp);
        }

        galge = new ImageView(getActivity());
        galge.setMinimumWidth(container.getWidth() / 3);
        galge.setMinimumHeight(container.getWidth() / 3);
        galge.setImageResource(R.mipmap.galge);
        tl.addView(galge);

        txtInfo = new TextView(getActivity());
        txtInfo.setTextColor(Color.rgb(248, 248, 255));
        tl.addView(txtInfo);

        txtHiddenWords = new TextView(getActivity());
        txtHiddenWords.setTextColor(Color.rgb(248, 248, 255));
        txtHiddenWords.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tl.addView(txtHiddenWords);

        txtUsedLetters = new TextView(getActivity());
        txtUsedLetters.setTextColor(Color.rgb(248, 248, 255));
        txtUsedLetters.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tl.addView(txtUsedLetters);

        btnNewGame = new Button(getActivity());
        btnNewGame.setOnClickListener(this);
        btnNewGame.setText("Start et nyt spil");
        btnNewGame.setVisibility(View.GONE);
        tl.addView(btnNewGame);

        btnBack = new Button(getActivity());
        btnBack.setOnClickListener(this);
        btnBack.setText("Afslut");
        btnBack.setWidth(container.getWidth() / 2);
        System.out.println("btnBack w: " + btnBack.getWidth());
        tl.addView(btnBack);

        txtUsedLetters.setText(gl.getBrugteBogstaver().toString());
        txtHiddenWords.setText(gl.getSynligtOrd());

        fl.addView(tl);
        fl.addView(rl);
        fl.addView(gv);

        txtInfo.setId(101);
        txtHiddenWords.setId(102);
        txtUsedLetters.setId(103);
        galge.setId(104);

        return fl;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeDetector);

    }

    @Override
    public void onStop() {
        super.onStop();
        main.stopMediaPlayer();
    }

    public void onClick(View v) {

        if (v == null) {
            Toast.makeText(getActivity(), "Træk et bogstav op på billedet for at gætte!", Toast.LENGTH_LONG);
        }

        txtInfo.setText("");


        if (v == btnNewGame) {
            gl.nulstil();
            if(!(main.isPlaying())) {
                main.playSound(R.raw.arnold);
            }
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragWindow, new Frag_Galgespil())
                    .addToBackStack(null)
                    .commit();
        }

        if (v == btnBack) {
            nulstilSpil();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragWindow, new Frag_menu())
                    .addToBackStack(null)
                    .commit();
        }
        if (v == btnMute){
            if (!app.isMuted()){
                app.mute();
                main.stopMediaPlayer();
                Log.d("btnMute", "Mute");
                btnMute.setImageResource(R.drawable.ic_volume_off_black_48dp);
            }  else {
                app.unMute();
                Log.d("btnMute", "unmute");
                btnMute.setImageResource(R.drawable.ic_volume_up_black_48dp);
            }

        }
    }

    private void nulstilSpil() {
        gl.nulstil();
        txtHiddenWords.setText("");
        txtUsedLetters.setText("");
        updateGalge();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        shakeDetector = null;
        sensorManager = null;
        accelerometer = null;
        container = null;
    }

    private boolean isDialogVisible = false;

    @Override
    public void onShake() {

        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        nulstilSpil();
                        main.playSound(R.raw.arnold);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.fragWindow, new Frag_Galgespil())
                                .addToBackStack(null)
                                .commit();
                        isDialogVisible = false;
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        isDialogVisible = false;
                        break;
                }

            }
        };

        if (!isDialogVisible) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Vil du nulstille spillet?")
                    .setPositiveButton("Ja", dialogListener)
                    .setNegativeButton("Fortryd", dialogListener)
                    .show();
        }
        isDialogVisible = true;
    }

    public void updateGalge() {
        if (gl.getAntalForkerteBogstaver() == 0) {
            galge.setImageResource(R.mipmap.galge);
        }
        if (gl.getAntalForkerteBogstaver() == 1) {
            galge.setImageResource(R.mipmap.forkert1);
        }
        if (gl.getAntalForkerteBogstaver() == 2) {
            galge.setImageResource(R.mipmap.forkert2);
        }
        if (gl.getAntalForkerteBogstaver() == 3) {
            galge.setImageResource(R.mipmap.forkert3);
        }
        if (gl.getAntalForkerteBogstaver() == 4) {
            galge.setImageResource(R.mipmap.forkert4);
        }
        if (gl.getAntalForkerteBogstaver() == 5) {
            galge.setImageResource(R.mipmap.forkert5);
        }
        if (gl.getAntalForkerteBogstaver() == 6) {
            galge.setImageResource(R.mipmap.forkert6);
        }
        if (gl.getAntalForkerteBogstaver() == 7){
            galge.setImageResource(R.mipmap.gravsten);
        }
        if (gl.erSpilletVundet()== true){
            galge.setImageResource(R.mipmap.won);
        }
    }

}
