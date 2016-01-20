package com.example.martinvieth.lektions.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.martinvieth.lektions.R;
import com.example.martinvieth.lektions.logic.Galgelogik;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by thomas on 18/01/16.
 */
public class GalgeView extends View{

    private Frag_Galgespil spil;
    private static Galgelogik gl;
    private PointF finger;
    private PointF fingersPointOnLetter;
    private ArrayList<Letter> letters;
    private Letter chosenLetter = null;
    private Paint textType;
    private Paint lineType;
    private Activity_Main main;


    public GalgeView(Context c, Frag_Galgespil spil) {
        super(c);
        main = (Activity_Main) c;
        this.spil = spil;
        gl = Frag_Galgespil.gl;
        textType = new Paint();
        lineType = new Paint();
        letters = new ArrayList<>();
        fingersPointOnLetter = new PointF();
        finger = new PointF();
        init();
    }

    public GalgeView(Context c, AttributeSet at, Frag_Galgespil spil) {
        super(c, at);
        this.spil = spil;
        init();
        textType = new Paint();
        lineType = new Paint();
        letters = new ArrayList<>();
        fingersPointOnLetter = new PointF();
        finger = new PointF();
    }

    private void init() {

        Typeface font = Typeface.createFromAsset(spil.getActivity().getAssets(), "JLSSpaceGothicC_NC.otf");
        textType.setTypeface(font);
        textType.setColor(Color.parseColor("#000080"));
        textType.setTextSize(spil.container.getWidth() / 11);
        textType.setAntiAlias(true);
        textType.setStyle(Paint.Style.FILL);
        lineType.setColor(Color.BLACK);
        lineType.setStyle(Paint.Style.STROKE);

        spawnLetters();

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
        int height = spil.container.getHeight();
        int width = spil.container.getWidth();
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
    protected void onDraw(Canvas canvas) {
        float screenScale = 1;//getWidth()/480f;
        canvas.scale(screenScale, screenScale);

        for(Letter l : letters) {
            if(l == chosenLetter) continue;
            drawLetter(canvas, l.r.left, l.r.top, l);
        }

        //canvas.drawCircle(finger.x, finger.y, 10, textType); //tegn en prik hvor fingeren er/sidst var
        System.out.println("finger - x: " + finger.x + " y: " + finger.y);
        if(chosenLetter != null) {
            float x = finger.x - fingersPointOnLetter.x;
            float y = finger.y - fingersPointOnLetter.y;
            drawLetter(canvas, x, y, chosenLetter);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float screenScale = 1;//getWidth()/480f;
        finger.x = e.getX() / screenScale;
        finger.y = e.getY() / screenScale;

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            for (Letter l : letters) {
                if (l.r.contains(finger.x, finger.y)) {
                    fingersPointOnLetter.x = finger.x - l.r.left;
                    fingersPointOnLetter.y = finger.y - l.r.top;
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
            if(chosenLetter != null) {
                Rect imageRect = new Rect();
                spil.galge.getHitRect(imageRect);
                System.out.println("ImageRect: " + imageRect.toString());
                if (imageRect.contains((int) finger.x, (int) finger.y)){
                    gl.gætBogstav(chosenLetter.str);
                    spil.updateGalge();
                    letters.remove(chosenLetter);
                    spil.txtUsedLetters.setText(gl.getBrugteBogstaver().toString());
                    spil.txtHiddenWords.setText(gl.getSynligtOrd());

                }
                float x = finger.x - fingersPointOnLetter.x;
                float y = finger.y - fingersPointOnLetter.y;
                chosenLetter.r.offsetTo(x, y);
                Log.d("Galge", "chosenLetter.r = " + chosenLetter.r);

            }

            if(gl.erSpilletSlut()){
                //game reset
                if(gl.erSpilletVundet()){
                    spil.txtInfo.setText("Du har vundet! \nOrdet var "+ gl.getOrdet());
                    main.stopMediaPlayer();
                    main.playSound(R.raw.gamewon);
                    letters.clear();
                }
                else if(gl.erSpilletTabt()){
                    spil.txtInfo.setText("Du har tabt! \nOrdet var " + gl.getOrdet());
                    main.stopMediaPlayer();
                    main.playSound(R.raw.gamelost);
                    letters.clear();
                }
                spil.btnNewGame.setVisibility(View.VISIBLE);
                gl.nulstil();
                spil.txtHiddenWords.setText("");
                spil.txtUsedLetters.setText("");
            }

            chosenLetter = null;
        }
        invalidate();
        return true;
    }

    private void drawLetter(Canvas c, float x, float y, Letter l) {
        RectF r = new RectF(l.r);
        r.offsetTo(x, y);
        //c.drawRect(r, lineType);
        c.drawText(l.str, r.left, r.bottom, textType);
    }

    class Letter {
        RectF r = new RectF();
        String str;

        public Letter(String s, RectF r) {
            this.r = r;
            str = s;

        }

        public float centerX() {
            return r.width()/2;
        }

        public float centerY() {
            return r.height()/2;
        }
    }
}


