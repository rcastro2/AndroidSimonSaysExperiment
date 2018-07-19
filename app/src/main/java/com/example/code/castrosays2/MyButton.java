package com.example.code.castrosays2;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by CODE on 7/18/2018.
 */

public class MyButton {
    private Button b;
    private int on;
    private int off;
    private int value;
    private String sound;

    @SuppressLint("ClickableViewAccessibility")
    public MyButton(View v, int on, int off, int value, String sound) {
        this.b = (Button)v;
        this.on = on;
        this.off = off;
        this.value = value;
        this.sound = sound;
        this.b.setBackgroundColor(off);
        this.b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(GameActivity.playingSequence) return true;
                //Log.d("Castro", " " + GameActivity.playerPosition + " " + GameActivity.sequence.get(GameActivity.playerPosition) + " " + MyButton.this.value );
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    MyButton.this.b.setBackgroundColor(MyButton.this.on);
                    if(GameActivity.sequence.get(GameActivity.playerPosition) != MyButton.this.value){
                        GameActivity.gameOver();
                    }
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    MyButton.this.b.setBackgroundColor(MyButton.this.off);
                    if(GameActivity.playerPosition < GameActivity.sequence.size() - 1){
                        GameActivity.playerPosition++;
                    }else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                GameActivity.playSequence();
                            }
                        }, 1000);
                    }

                }
                return true;
            }
        });
    }
    public void on(){
        this.b.setBackgroundColor(this.on);
    }
    public void off(){
        this.b.setBackgroundColor(this.off);
    }
}
