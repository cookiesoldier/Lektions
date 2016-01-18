package com.example.martinvieth.lektions.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martinvieth.lektions.logic.Galgelogik;
import com.example.martinvieth.lektions.R;
import com.example.martinvieth.lektions.helper.ShakeDetector;

import java.util.ArrayList;
import java.util.Random;

public class Frag_Galgespil extends Fragment implements View.OnClickListener, ShakeDetector.OnShakeListener {

    private ShakeDetector shakeDetector;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Frag_HentOrdFraDR hentOrd = new Frag_HentOrdFraDR();
    private static Galgelogik gl = null;
    private GalgeView gv = null;
    private TextView txtUsedWords, txtInfo, txtHiddenWords;
    private Button btnGuess, btnBack, btnNewGame;
    private ImageView galge;
    private EditText etxtGuess;
    private Point screenSize = new Point();
    private ViewGroup container;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getSize(screenSize);
        this.container = container;

        Log.d("Galge", "container: "+container.toString() + " - " + container.getTransitionName()
                + "\nwidth: "+container.getWidth() + " height: " + container.getHeight());

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector(this);


        if (gl == null) {
            gl = new Galgelogik();
        }

        if (gv == null) {
            gv = new GalgeView(getActivity());
            // gv = (GalgeView) findViewById(R.id.galgeView);
        }

        FrameLayout fl = new FrameLayout(getActivity());
        TableLayout tl = new TableLayout(getActivity());
        galge = new ImageView(getActivity());

        galge.setMinimumWidth(container.getWidth()/3);
        galge.setMinimumHeight(container.getWidth()/3);
        galge.setImageResource(R.mipmap.galge);
        tl.addView(galge);

        txtInfo = new TextView(getActivity());
        txtInfo.setTextColor(Color.rgb(248, 248, 255));

        tl.addView(txtInfo);

        txtHiddenWords = new TextView(getActivity());
        txtHiddenWords.setTextColor(Color.rgb(248, 248, 255));
        txtHiddenWords.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tl.addView(txtHiddenWords);

        txtUsedWords = new TextView(getActivity());
        txtUsedWords.setTextColor(Color.rgb(248, 248, 255));
        txtUsedWords.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tl.addView(txtUsedWords);

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

        txtUsedWords.setText(gl.getBrugteBogstaver().toString());
        txtHiddenWords.setText(gl.getSynligtOrd());

        fl.addView(tl);
        fl.addView(gv);


        txtInfo.setId(101);
        txtHiddenWords.setId(102);
        txtUsedWords.setId(103);


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

    public static Galgelogik getLogic() {
        return gl;
    }

    public void onClick(View v) {

        if (v == null) {
            Toast.makeText(getActivity(), "Træk et bogstav op på billedet for at gætte!", Toast.LENGTH_LONG);
        }

        txtInfo.setText("");


        if(v == btnNewGame) {
            gl.nulstil();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragWindow, new Frag_Galgespil())
                    .addToBackStack(null)
                    .commit();
        }

        if(v == btnBack){
            nulstilSpil();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragWindow, new Frag_menu())
                    .addToBackStack(null)
                    .commit();
        }
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
    }
    private void nulstilSpil() {
        gl.nulstil();
        txtHiddenWords.setText("");
        txtUsedWords.setText("");
        updateGalge();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        shakeDetector = null;
        sensorManager = null;
        accelerometer = null;
    }

    private boolean isDialogVisible = false;

    @Override
    public void onShake() {

        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        nulstilSpil();
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



    class Letter {
        RectF r = new RectF();
        String str;

        public Letter(String s, RectF r) {
            this.r = r;
            str = s;

        }
    }

    class GalgeView extends View {

        PointF finger = new PointF();
        ArrayList<Letter> letters = new ArrayList<>();
        Letter chosenLetter = null;
        Paint textType = new Paint();
        Paint lineType = new Paint();
        //Point screenSize = new Point();
        int viewWidth;
        int viewHeight;

        private void init() {

            textType.setColor(Color.RED);
            textType.setTextSize(container.getWidth()/11);
            textType.setAntiAlias(true);
            lineType.setColor(Color.BLACK);
            lineType.setStyle(Paint.Style.STROKE);

            //WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            //Display display = wm.getDefaultDisplay();
            //display.getSize(screenSize);

            spawnLetters();

            System.out.println("---> Screen size w: " + screenSize.x + " h: " + screenSize.y);

        }

        public void spawnLetters() {
            letters.clear();
            for(int i = 'A'; i <= 'Z'; i++) {
                String letter;
                letter = String.valueOf((char) i);
                //System.out.println("------> "+ letter);
                Letter bogstav = new Letter(letter, getRandomStartRectF());
                letters.add(bogstav);
                System.out.println("--> "+ letter + " was added to the letters list" +
                        "\nat "+bogstav.r.toString());
            }
        }

        private RectF getRandomStartRectF() {
            float left;
            float top;
            float right;
            float bottom;
            int height = container.getHeight();
            int width = container.getWidth();
            float rectwidth = width/15;
            float rectheight = rectwidth;


            Random rand = new Random();
            left = rand.nextInt(width - (int) rectwidth);
            top = rand.nextInt(height/2) + height/2 - rectheight;
            right = left + rectwidth;
            bottom = top + rectheight;

            return new RectF(left, top, right, bottom);
        }


        @Override
        protected void onFinishInflate() {
            super.onFinishInflate();
            viewHeight = getMeasuredHeight();
            viewWidth = getMeasuredWidth();
            System.out.println(">>>---- view inflated - w: " + viewWidth + " h: " + viewHeight);

        }

        @Override
        public void onWindowFocusChanged(boolean hasWindowFocus) {
            super.onWindowFocusChanged(hasWindowFocus);
            viewHeight = this.getHeight();
            viewWidth = this . getWidth() ;
            System.out.println("<<<<---- WindowFocus changed");
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            viewHeight = h;
            viewWidth = w;
            System.out.println("SIZE CHANGED --------");
        }

        public GalgeView(Context c) {
            super(c);
            init();
        }

        public GalgeView(Context c, AttributeSet at) {
            super(c, at);
            init();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            float screenScale = 1;//getWidth()/480f;
            canvas.scale(screenScale, screenScale);

            for(Letter l : letters) {
                if(l == chosenLetter) continue;
                drawLetter(canvas, l.r.left, l.r.top, l);
            }

            //canvas.drawCircle(finger.x, finger.y, 10, textType); //tegn en prik hvor fingeren er/sidst var
            System.out.println("finger - x: " + finger.x + " y: " + finger.y);
            if(chosenLetter != null) drawLetter(canvas, finger.x, finger.y, chosenLetter);
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {

            float screenScale = 1;//getWidth()/480f;
            finger.x = e.getX() / screenScale;
            finger.y = e.getY() / screenScale;

            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                for (Letter l : letters) {
                    if (l.r.contains(finger.x, finger.y)) {
                        chosenLetter = l;
                        Log.d("Galge", "chosen letter is " + l);
                        break;
                    }
                }
                if(chosenLetter == null) return false;
            }

            if (e.getAction() == MotionEvent.ACTION_MOVE) {
                if (chosenLetter != null) {
                    Log.d("Galge", "Finger coordinate = " + finger);
                }
            }


            if (e.getAction() == MotionEvent.ACTION_UP) {
                if (chosenLetter != null) {
                    Rect imageRect = new Rect();
                    galge.getHitRect(imageRect);
                    System.out.println("ImageRect: " + imageRect.toString());
                    if (imageRect.contains((int) finger.x, (int) finger.y)) {
                        //TODO
                        gl.gætBogstav(chosenLetter.str);
                        updateGalge();
                        letters.remove(chosenLetter);
                        txtUsedWords.setText(gl.getBrugteBogstaver().toString());
                        txtHiddenWords.setText(gl.getSynligtOrd());

                        Toast.makeText(getContext(), "Du slap et bogstav på billedet", Toast.LENGTH_SHORT).show();
                        System.out.println("_______ DER BLEV SLUPPET BOGSTAV PÅ BILLEDET!!!!!");
                    }

                    chosenLetter.r.offsetTo(finger.x, finger.y);
                    Log.d("Galge", "chosenLetter.r = " + chosenLetter.r);


            }
                if(gl.erSpilletSlut()){
                    //game reset
                    if(gl.erSpilletVundet()){
                        txtInfo.setText("Du har vundet! \nOrdet var "+ gl.getOrdet());
                    }
                    else if(gl.erSpilletTabt()){
                        txtInfo.setText("Du har tabt! \nOrdet var " + gl.getOrdet());
                    }
                    btnNewGame.setVisibility(View.VISIBLE);
                    gl.nulstil();
                    txtHiddenWords.setText("");
                    txtUsedWords.setText("");
                }

                chosenLetter = null;
            }
            invalidate();
            return true;
        }

        private int getRelativeLeft(View myView) {
            if (myView.getParent() == myView.getRootView())
                return myView.getLeft();
            else
                return myView.getLeft() + getRelativeLeft((View) myView.getParent());
        }

        private int getRelativeTop(View myView) {
            if (myView.getParent() == myView.getRootView())
                return myView.getTop();
            else
                return myView.getTop() + getRelativeTop((View) myView.getParent());
        }

        private void drawLetter(Canvas c, float x, float y, Letter l) {
            RectF r = new RectF(l.r);
            r.offsetTo(x, y);
            c.drawRect(r, lineType);
            c.drawText(l.str, r.left, r.bottom, textType);
        }
    }
}
