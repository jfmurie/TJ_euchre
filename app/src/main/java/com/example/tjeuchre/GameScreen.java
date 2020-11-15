package com.example.tjeuchre;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


public class GameScreen extends AppCompatActivity {
    private ImageButton imageButtonTL;
    private ImageButton imageButtonTC;
    private ImageButton imageButtonTR;
    private ImageButton imageButtonBL;
    private ImageButton imageButtonBR;
    long animationDuration = 300;//mili
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        imageButtonTL = (ImageButton) findViewById(R.id.imageButtonTL);
        imageButtonTC = (ImageButton) findViewById(R.id.imageButtonTC);
        imageButtonTR = (ImageButton) findViewById(R.id.imageButtonTR);
        imageButtonBL = (ImageButton) findViewById(R.id.imageButtonBL);
        imageButtonBR = (ImageButton) findViewById(R.id.imageButtonBR);
    }
    public void cardToPlayTL(View view){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageButtonTL, "x", 372f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageButtonTL, "y", 49f);
        animatorX.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }
    public void cardToPlayTC(View view){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageButtonTC, "x", 372f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageButtonTC, "y", 49f);
        animatorX.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }
    public void cardToPlayTR(View view){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageButtonTR, "x", 372f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageButtonTR, "y", 49f);
        animatorX.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }
    public void cardToPlayBL(View view){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageButtonBL, "x", 372f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageButtonBL, "y", 49f);
        animatorX.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }
    public void cardToPlayBR(View view){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageButtonBR, "x", 372f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageButtonBR, "y", 49f);
        animatorX.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }

}