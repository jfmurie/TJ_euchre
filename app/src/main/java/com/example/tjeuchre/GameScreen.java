package com.example.tjeuchre;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.card.Card;
import com.cardvalues.CardValue;
import com.deck.Deck;
import com.player.AI;
import com.player.Player;
import com.player.UserPlayer;
import com.suits.Suit;
import com.team.Team;

import java.util.ArrayList;


public class GameScreen extends AppCompatActivity {
    private ImageButton imageButtonTL;
    private ImageButton imageButtonTC;
    private ImageButton imageButtonTR;
    private ImageButton imageButtonBL;
    private ImageButton imageButtonBR;
    private ImageButton imageButtonOL;
    private ImageButton imageButtonOR;
    private ImageButton imageButtonOF;
    private Button      startButton;
    long animationDuration = 300;//mili

    ImageButton handCard;

    Player[] players;
    Deck deck;
    Team userTeam;
    Team pureAITeam;
    int dealerIndex;


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
        startButton   = findViewById(R.id.startButton);

        initializeGame();
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
    public void setCards(ImageButton handCard, Suit suit, CardValue value){
        //Choose image to show based on player's card
        if (suit == Suit.HEARTS){
            if(value == CardValue.NINE){
                handCard.setImageResource(R.drawable.a091);}
            else if(value == CardValue.TEN){
                handCard.setImageResource(R.drawable.a101);}
            else if(value == CardValue.JACK){
                handCard.setImageResource(R.drawable.a111);}
            else if(value == CardValue.QUEEN){
                handCard.setImageResource(R.drawable.a121);}
            else if(value == CardValue.KING){
                handCard.setImageResource(R.drawable.a131);}
            else if(value == CardValue.ACE){
                handCard.setImageResource(R.drawable.a141);}
        }
        else if (suit == Suit.DIAMONDS){
            if(value == CardValue.NINE){
                handCard.setImageResource(R.drawable.a092);}
            else if(value == CardValue.TEN){
                handCard.setImageResource(R.drawable.a102);}
            else if(value == CardValue.JACK){
                handCard.setImageResource(R.drawable.a112);}
            else if(value == CardValue.QUEEN){
                handCard.setImageResource(R.drawable.a122);}
            else if(value == CardValue.KING){
                handCard.setImageResource(R.drawable.a132);}
            else if(value == CardValue.ACE){
                handCard.setImageResource(R.drawable.a142);}
        }
        else if (suit == Suit.SPADES){
            if(value == CardValue.NINE){
                handCard.setImageResource(R.drawable.a093);}
            else if(value == CardValue.TEN){
                handCard.setImageResource(R.drawable.a103);}
            else if(value == CardValue.JACK){
                handCard.setImageResource(R.drawable.a113);}
            else if(value == CardValue.QUEEN){
                handCard.setImageResource(R.drawable.a123);}
            else if(value == CardValue.KING){
                handCard.setImageResource(R.drawable.a133);}
            else if(value == CardValue.ACE){
                handCard.setImageResource(R.drawable.a143);}
        }
        else if (suit == Suit.CLUBS){
            if(value == CardValue.NINE){
                handCard.setImageResource(R.drawable.a094);}
            else if(value == CardValue.TEN){
                handCard.setImageResource(R.drawable.a104);}
            else if(value == CardValue.JACK){
                handCard.setImageResource(R.drawable.a114);}
            else if(value == CardValue.QUEEN){
                handCard.setImageResource(R.drawable.a124);}
            else if(value == CardValue.KING){
                handCard.setImageResource(R.drawable.a134);}
            else if(value == CardValue.ACE){
                handCard.setImageResource(R.drawable.a144);}
        }
    }
    public void initializeGame(){
        Player userPlayer = new UserPlayer();
        Player ai1 = new AI(1);
        Player ai2 = new AI(2);
        Player ai3 = new AI(3);

        players = new Player[]{userPlayer, ai1, ai2, ai3};

        userTeam = new Team(userPlayer, ai2);
        pureAITeam = new Team(ai1, ai3);

        deck = new Deck();
        dealerIndex = (int)(Math.random() * 4);
    }

    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        playGame();
    }

    public Card dealCards(){
        deck.shuffleDeck();
        dealerIndex = 0;

        Card topCard = deck.deal(players, dealerIndex);
        ArrayList<Card> hand = players[0].getPlayerHand();

        setCards(imageButtonTL, hand.get(0).getSuit(), hand.get(0).getValue());
        setCards(imageButtonTC, hand.get(1).getSuit(), hand.get(1).getValue());
        setCards(imageButtonTR, hand.get(2).getSuit(), hand.get(2).getValue());
        setCards(imageButtonBL, hand.get(3).getSuit(), hand.get(3).getValue());
        setCards(imageButtonBR, hand.get(4).getSuit(), hand.get(4).getValue());

        return topCard;
    }

    public void playGame(){

        Card topCard = dealCards();
//        Round round = new Round();
//        round.decideTrump(players, topCard);

        //round.playRound(userTeam, pureAITeam, players, dealerIndex);

        //dealerIndex = (dealerIndex + 1) % 4;  // increment the dealer index
    }


}