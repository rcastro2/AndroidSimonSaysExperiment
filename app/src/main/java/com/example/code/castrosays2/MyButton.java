package com.example.code.castrosays2;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by CODE on 7/18/2018.
 */

public class MyButton {
    private Button b;
    private int on;
    private int off;
    private int value;
    private MediaPlayer mp;

    @SuppressLint("ClickableViewAccessibility")
    public MyButton(View v, int on, int off, int value, int soundID) {
        this.b = (Button)v;
        this.on = on;
        this.off = off;
        this.value = value;
        mp = MediaPlayer.create(GameActivity.mContext, soundID);
        this.b.setBackgroundColor(off);
        ViewGroup.LayoutParams p = this.b.getLayoutParams();
        p.width = GameActivity.getScreenWidth() / 2;
        p.height = GameActivity.getScreenHeight() / 2;
        this.b.setLayoutParams(p);

        this.b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(GameActivity.playingSequence) return true;
                //Log.d("Castro", " " + GameActivity.playerPosition + " " + GameActivity.sequence.get(GameActivity.playerPosition) + " " + MyButton.this.value );
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    MyButton.this.b.setBackgroundColor(MyButton.this.on);
                    mp.start();
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
        mp.start();
    }
    public void off(){
        this.b.setBackgroundColor(this.off);
    }
}
