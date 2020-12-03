package com.example.tjeuchre;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.media.Image;
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
    private ImageButton imageButtonOL;
    private ImageButton imageButtonOR;
    private ImageButton imageButtonOF;
    long animationDuration = 300;//mili
    int val = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        imageButtonTL = (ImageButton) findViewById(R.id.imageButtonTL);
        imageButtonTC = (ImageButton) findViewById(R.id.imageButtonTC);
        imageButtonTR = (ImageButton) findViewById(R.id.imageButtonTR);
        imageButtonBL = (ImageButton) findViewById(R.id.imageButtonBL);
        imageButtonBR = (ImageButton) findViewById(R.id.imageButtonBR);
        imageButtonOL = (ImageButton) findViewById(R.id.imageButtonOL);
        imageButtonOR = (ImageButton) findViewById(R.id.imageButtonOR);
        imageButtonOF = (ImageButton) findViewById(R.id.imageButtonOF);

        setCards(imageButtonTL);
        setCards(imageButtonTC);
        setCards(imageButtonTR);
        setCards(imageButtonBL);
        setCards(imageButtonBR);
        setCards(imageButtonOF);
    }
    public String getImage(){//can also use image type
        String nineHearts = "@drawable/a091";
        return nineHearts;
    }
    public void cardToPlayTL(View view){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageButtonTL, "x", 400f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageButtonTL, "y", 380f);
        animatorX.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }

    public void cardToPlayTC(View view){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageButtonTC, "x", 400f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageButtonTC, "y", 380f);
        animatorX.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }
    public void cardToPlayTR(View view){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageButtonTR, "x", 400f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageButtonTR, "y", 380f);
        animatorX.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }
    public void cardToPlayBL(View view){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageButtonBL, "x", 400f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageButtonBL, "y", 380f);
        animatorX.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }
    public void cardToPlayBR(View view){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageButtonBR, "x", 400f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageButtonBR, "y", 380f);
        animatorX.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }
    public void setCards(ImageButton handCard){
        //=display player cards

        if (val<=5){//card is a heart

            if(val==0){//9
                handCard.setImageResource(R.drawable.a091);}
            else if(val==1){//10
                handCard.setImageResource(R.drawable.a101);}
            else if(val==2){//Jack
                handCard.setImageResource(R.drawable.a111);}
            else if(val==3){//King
                handCard.setImageResource(R.drawable.a121);}
            else if(val==4){//Queen
                handCard.setImageResource(R.drawable.a131);}
            else if(val==5){//Ace
                handCard.setImageResource(R.drawable.a141);}
        }
        else if (val<=11){//card is a Diamond
            if(val==6){//9
                handCard.setImageResource(R.drawable.a092);}
            else if(val==7){//10
                handCard.setImageResource(R.drawable.a102);}
            else if(val==8){//Jack
                handCard.setImageResource(R.drawable.a112);}
            else if(val==9){//King
                handCard.setImageResource(R.drawable.a122);}
            else if(val==10){//Queen
                handCard.setImageResource(R.drawable.a132);}
            else if(val==11){//Ace
                handCard.setImageResource(R.drawable.a142);}
        }
        else if (val<=17){//card is a Spade
            if(val==12){//9
                handCard.setImageResource(R.drawable.a093);}
            else if(val==13){//10
                handCard.setImageResource(R.drawable.a103);}
            else if(val==14){//Jack
                handCard.setImageResource(R.drawable.a113);}
            else if(val==15){//King
                handCard.setImageResource(R.drawable.a123);}
            else if(val==16){//Queen
                handCard.setImageResource(R.drawable.a133);}
            else if(val==17){//Ace
                handCard.setImageResource(R.drawable.a143);}
        }
        else if (val<=23){//card is a Club
            if(val==18){//9
                handCard.setImageResource(R.drawable.a093);}
            else if(val==19){//10
                handCard.setImageResource(R.drawable.a103);}
            else if(val==20){//Jack
                handCard.setImageResource(R.drawable.a113);}
            else if(val==21){//King
                handCard.setImageResource(R.drawable.a123);}
            else if(val==22){//Queen
                handCard.setImageResource(R.drawable.a133);}
            else if(val==23){//Ace
                handCard.setImageResource(R.drawable.a143);}
        }

        //--------
    }

}