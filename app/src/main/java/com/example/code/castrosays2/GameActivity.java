package com.example.code.castrosays2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private MyButton btnRed;
    private MyButton btnGreen;
    private MyButton btnBlue;
    private MyButton btnYellow;
    private static ImageView imgD1;
    private static ImageView imgD2;
    private static int level;
    private static MyButton[] buttons;
    public static ArrayList<Integer> sequence;
    public static boolean playingSequence;
    public static int playerPosition;
    public static Context mContext;
    public static int[] digits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mContext = this;

        playingSequence = true;
        level = 0;

        btnRed = new MyButton(findViewById(R.id.btnRed), Color.rgb(255,0,0),Color.rgb(175,0,0),0,R.raw.red);
        btnGreen = new MyButton(findViewById(R.id.btnGreen), Color.rgb(0,255,0),Color.rgb(0,175,0),1,R.raw.green);
        btnBlue = new MyButton(findViewById(R.id.btnBlue), Color.rgb(0,0,255),Color.rgb(0,0,175),2,R.raw.blue);
        btnYellow = new MyButton(findViewById(R.id.btnYellow), Color.rgb(255,255,0),Color.rgb(175,175,0),3,R.raw.yellow);
        imgD1 = (ImageView)findViewById(R.id.imgD1);
        imgD2 = (ImageView)findViewById(R.id.imgD2);

        assignDigits();
        imgD1.setBackground(getResources().getDrawable(digits[0]));
        imgD2.setBackground(getResources().getDrawable(digits[0]));

        buttons = new MyButton[4];
        sequence = new ArrayList<>();
        buttons[0] = btnRed;
        buttons[1] = btnGreen;
        buttons[2] = btnBlue;
        buttons[3] = btnYellow;
        for(int i = 0; i < 2;i++){
            int r = (int)(Math.random()*4);
            sequence.add(r);
            Log.d("Castro","r: " + r);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                playSequence();
            }
        }, 1000);
    }
    public static int pos, ct, pace = 500;
    public static void assignDigits(){
        digits = new int[10];
        digits[0] = R.drawable.d0;
        digits[1] = R.drawable.d1;
        digits[2] = R.drawable.d2;
        digits[3] = R.drawable.d3;
        digits[4] = R.drawable.d4;
        digits[5] = R.drawable.d5;
        digits[6] = R.drawable.d6;
        digits[7] = R.drawable.d7;
        digits[8] = R.drawable.d8;
        digits[9] = R.drawable.d9;

    }
    public static void playSequence(){
        playingSequence = true;
        int r = (int)(Math.random()*4);
        sequence.add(r);
        pos = 0;
        ct = 0;
        level++;
        imgD2.setBackground(mContext.getResources().getDrawable(digits[level]));
        Log.d("Castro","Pace: " + pace);
        new CountDownTimer(sequence.size() * 2 * pace , pace) {
            public void onTick(long millisUntilFinished) {
                if(ct % 2 == 0){
                    buttons[sequence.get(pos)].on();
                }else{
                    buttons[sequence.get(pos)].off();
                    pos++;
                }
                ct++;
            }

            public void onFinish() {
                // called after count down is finished
                buttons[sequence.get(pos)].off();
                playingSequence = false;
                playerPosition = 0;
                if(pace > 200) pace -= 100;
            }
        }.start();
    }
    public static void gameOver(){
        Intent i = new Intent(mContext,GameOverActivity.class);
        mContext.startActivity(i);
    }
    public static int getScreenWidth(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
