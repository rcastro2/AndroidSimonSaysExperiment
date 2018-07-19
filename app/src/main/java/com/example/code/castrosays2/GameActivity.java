package com.example.code.castrosays2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private MyButton btnRed;
    private MyButton btnGreen;
    private MyButton btnBlue;
    private MyButton btnYellow;
    private static MyButton[] buttons;
    public static ArrayList<Integer> sequence;
    public static boolean playingSequence;
    public static int playerPosition;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mContext = this;

        playingSequence = true;

        btnRed = new MyButton(findViewById(R.id.btnRed), Color.rgb(255,0,0),Color.rgb(175,0,0),0,"red.mp3");
        btnGreen = new MyButton(findViewById(R.id.btnGreen), Color.rgb(0,255,0),Color.rgb(0,175,0),1,"green.mp3");
        btnBlue = new MyButton(findViewById(R.id.btnBlue), Color.rgb(0,0,255),Color.rgb(0,0,175),2,"blue.mp3");
        btnYellow = new MyButton(findViewById(R.id.btnYellow), Color.rgb(255,255,0),Color.rgb(175,175,0),3,"yellow.mp3");
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
    public static int pos, ct, pace = 1000;
    public static void playSequence(){
        playingSequence = true;
        int r = (int)(Math.random()*4);
        sequence.add(r);
        pos = 0;
        ct = 0;
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
                if(pace > 200) pace -= 150;
            }
        }.start();
    }
    public static void gameOver(){
        Intent i = new Intent(mContext,GameOverActivity.class);
        mContext.startActivity(i);
    }
}
