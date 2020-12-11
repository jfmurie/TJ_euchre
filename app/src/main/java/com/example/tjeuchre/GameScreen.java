package com.example.tjeuchre;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.Round.Round;
import com.Trick.Trick;
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
    private ImageButton imageButtonCC;
    private Button      startButton;
    private Button      trumpPassButton;
    private Button      trumpHeartsButton;
    private Button      trumpDiamondsButton;
    private Button      trumpClubsButton;
    private Button      trumpSpadesButton;

    private TextView    userTeamScore;
    private TextView    pureAITeamScore;
    private TextView    tricksTakenCounter;

    long animationDuration = 300000;//mili

    Player[] players;
    Deck deck;
    Team userTeam;
    Team pureAITeam;
    Round currentRound;
    int dealerIndex;
    int currentPlayerIndex;
    boolean userHasToReplaceCard;
    boolean topCardTurnedDown;
    Card topCard;
    final int maxPoints = 3;    //TODO: change this back to 10, it is at 3 for quicker testing

    boolean cardTLPlayed;
    boolean cardTCPlayed;
    boolean cardTRPlayed;
    boolean cardBLPlayed;
    boolean cardBRPlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        userTeamScore      = (TextView) findViewById(R.id.T1Score);
        pureAITeamScore    = (TextView) findViewById(R.id.T2Score);
        tricksTakenCounter = (TextView) findViewById(R.id.tricksTakenCounter);
        initializeButtons();
        initializeListeners();
        initializeGame();

        cardTLPlayed = false;
        cardTCPlayed = false;
        cardTRPlayed = false;
        cardBLPlayed = false;
        cardBRPlayed = false;
    }

    public void initializeButtons(){
        imageButtonTL       = (ImageButton) findViewById(R.id.imageButtonTL);
        imageButtonTC       = (ImageButton) findViewById(R.id.imageButtonTC);
        imageButtonTR       = (ImageButton) findViewById(R.id.imageButtonTR);
        imageButtonBL       = (ImageButton) findViewById(R.id.imageButtonBL);
        imageButtonBR       = (ImageButton) findViewById(R.id.imageButtonBR);
        imageButtonOL       = (ImageButton) findViewById(R.id.imageButtonOL);
        imageButtonOR       = (ImageButton) findViewById(R.id.imageButtonOR);
        imageButtonOF       = (ImageButton) findViewById(R.id.imageButtonOF);
        imageButtonCC       = (ImageButton) findViewById(R.id.imageButtonCC);
        startButton         = findViewById(R.id.startButton);
        trumpPassButton     = findViewById(R.id.trumpPassButton);
        trumpClubsButton    = findViewById(R.id.trumpClubsButton);
        trumpDiamondsButton = findViewById(R.id.trumpDiamondsButton);
        trumpHeartsButton   = findViewById(R.id.trumpHeartsButton);
        trumpSpadesButton   = findViewById(R.id.trumpSpadesButton);
    }
    public void initializeListeners(){
        trumpPassButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                trumpPassButton.setVisibility(View.INVISIBLE);
                imageButtonCC.setVisibility(View.INVISIBLE);
                int timesAround = 0;

                if(dealerIndex == 0 || topCardTurnedDown){
                    topCardTurnedDown = true;
                    timesAround++;
                }
                currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                callTrump(timesAround);
            }
        });
        trumpClubsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                trumpCalled(Suit.CLUBS, userTeam);
            }
        });
        trumpHeartsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                trumpCalled(Suit.HEARTS, userTeam);
            }
        });
        trumpSpadesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                trumpCalled(Suit.SPADES, userTeam);
            }
        });
        trumpDiamondsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                trumpCalled(Suit.DIAMONDS, userTeam);
            }
        });



        imageButtonTL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imageButtonClicked(imageButtonTL, 0);
            }
        });
        imageButtonTC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int i = 1;
                if(imageButtonTL.getVisibility() == View.INVISIBLE){
                    i--;
                }

                imageButtonClicked(imageButtonTC, i);
            }
        });
        imageButtonTR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int i = 2;
                if(imageButtonTL.getVisibility() == View.INVISIBLE){
                    i--;
                }
                if(imageButtonTC.getVisibility() == View.INVISIBLE){
                    i--;
                }

                imageButtonClicked(imageButtonTR, i);
            }
        });
        imageButtonBL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int i = 3;
                if(imageButtonTL.getVisibility() == View.INVISIBLE){
                    i--;
                }
                if(imageButtonTC.getVisibility() == View.INVISIBLE){
                    i--;
                }
                if(imageButtonTR.getVisibility() == View.INVISIBLE){
                    i--;
                }

                imageButtonClicked(imageButtonBL, i);
            }
        });
        imageButtonBR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int i = 4;
                if(imageButtonTL.getVisibility() == View.INVISIBLE){
                    i--;
                }
                if(imageButtonTC.getVisibility() == View.INVISIBLE){
                    i--;
                }
                if(imageButtonTR.getVisibility() == View.INVISIBLE){
                    i--;
                }
                if(imageButtonBL.getVisibility() == View.INVISIBLE){
                    i--;
                }

                imageButtonClicked(imageButtonBR, i);
            }
        });
    }
    public void imageButtonClicked(ImageButton button, int cardIndex){
        //User chose this card to be replaced by the topCard
        if(dealerIndex == 0 && userHasToReplaceCard){
            switch (cardIndex){
                default:break;
                case 0:
                    setCardImage(imageButtonTL, topCard.getSuit(), topCard.getValue());
                    break;
                case 1:
                    setCardImage(imageButtonTC, topCard.getSuit(), topCard.getValue());
                    break;
                case 2:
                    setCardImage(imageButtonTR, topCard.getSuit(), topCard.getValue());
                    break;
                case 3:
                    setCardImage(imageButtonBL, topCard.getSuit(), topCard.getValue());
                    break;
                case 4:
                    setCardImage(imageButtonBR, topCard.getSuit(), topCard.getValue());
                    break;
            }
            players[0].pickItUp(topCard, cardIndex);
            userHasToReplaceCard = false;

            //Onward to the trick
            currentPlayerIndex = 1; //we know user is dealer, so left of dealer is player 1
            startTrick();
        }
        //User is playing this card
        else{
            moveCardToMiddle(button);
            disableAllCardButtons();
            currentRound.getCurrentTrick().playerPlaysCard(players[0].playCard(cardIndex));

            if(button == imageButtonTL){
                cardTLPlayed = true;
            }
            else if(button == imageButtonTC){
                cardTCPlayed = true;
            }
            else if(button == imageButtonTR){
                cardTRPlayed = true;
            }
            else if(button == imageButtonBL){
                cardBLPlayed = true;
            }
            else if(button == imageButtonBR){
                cardBRPlayed = true;
            }

            if(players[1].isLead()){
                endTrick();
            }
            else{
                currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                takeTurn();
            }
        }
    }

    public void moveCardToMiddle(ImageButton cardButton){
        //ObjectAnimator animatorX = ObjectAnimator.ofFloat(cardButton, "x", 400);
        //ObjectAnimator animatorY = ObjectAnimator.ofFloat(cardButton, "y", 380);
        //animatorX.setDuration(animationDuration);
        //AnimatorSet animatorSet = new AnimatorSet();
        //animatorSet.playTogether(animatorX, animatorY);
        //animatorSet.start();
        cardButton.setX(400);
        cardButton.setY(380);
    }
    public void setCardImage(ImageButton handCard, Suit suit, CardValue value){
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
        //Reset to back of card image
        else if (suit == Suit.PASS){
            handCard.setImageResource(R.drawable.cardback);
        }
    }

    public void start(View view) {
        startButton.setVisibility(View.INVISIBLE);
        startRound();
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
        currentPlayerIndex = (dealerIndex + 1) % 4;
        userHasToReplaceCard = false;
        disableAllCardButtons();
        hideAllTrumpButtons();
    }
    public void startRound(){
        if(userTeam.getTeamScore() < maxPoints && pureAITeam.getTeamScore() < maxPoints){
            System.out.println("Starting a new round.");
            System.out.println("Dealer is Player " + dealerIndex);
            currentRound = new Round();
            topCard = dealCards();
            printHands();
            System.out.println("Top Card is: " + topCard.getValue() + " of " + topCard.getSuit());
            callTrump(0);
        }
    }
    public Card dealCards(){
        deck.shuffleDeck();

        Card topCard = deck.deal(players, dealerIndex);
        ArrayList<Card> hand = players[0].getPlayerHand();

        setCardImage(imageButtonTL, hand.get(0).getSuit(), hand.get(0).getValue());
        setCardImage(imageButtonTC, hand.get(1).getSuit(), hand.get(1).getValue());
        setCardImage(imageButtonTR, hand.get(2).getSuit(), hand.get(2).getValue());
        setCardImage(imageButtonBL, hand.get(3).getSuit(), hand.get(3).getValue());
        setCardImage(imageButtonBR, hand.get(4).getSuit(), hand.get(4).getValue());


        return topCard;
    }

    public void callTrump(int timesAround){
        System.out.println("\nAsking Player " + currentPlayerIndex + " to call Trump for the " + timesAround + " time.");
        Pair<Suit, Boolean> result;

        if(players[currentPlayerIndex].isAI()){
            System.out.println("The Player is an AI.");
            result = players[currentPlayerIndex].callTrump(topCard, timesAround == 1, currentPlayerIndex == dealerIndex);
            System.out.println("The Player chose trump as:" + result.first + ".");
            if(result.first != Suit.PASS){
                if(currentPlayerIndex % 2 == 0){
                    trumpCalled(result.first, userTeam);
                }
                else{
                    trumpCalled(result.first, pureAITeam);
                }

            }
            else if(timesAround == 1 && currentPlayerIndex == dealerIndex){
                System.out.println("Houston, we have a PROBLEM. The AI dealer is trying to pass and is not allowed.");
            }
            else if(timesAround == 0 && currentPlayerIndex != dealerIndex){
                currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                callTrump(0);
            }
            else{
                currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                topCardTurnedDown = true;
                callTrump(1);
            }
        }
        else{
            System.out.println("The Player is the USER.");
            if(timesAround == 0){
                showCardUpSuitButton();
            }
            else {
                showOtherTrumpButtons(dealerIndex == 0);
            }
        }
    }
    public void trumpCalled(Suit trump, Team teamCalled){
        currentRound.setTrump(trump);
        currentRound.setTeamCalledTrump(teamCalled);
        currentRound.updateAIOnHRTC(players);

        hideNonTrumpButtons();
        if(topCardTurnedDown){
            currentPlayerIndex = (dealerIndex + 1) % 4;
            startTrick();
        }
        else {
            pickItUp();
        }
    }

    public void showCardUpSuitButton(){
        trumpPassButton.setVisibility(View.VISIBLE);
        imageButtonCC.setVisibility(View.VISIBLE);
        setCardImage(imageButtonCC, topCard.getSuit(), topCard.getValue());
        trumpPassButton.setEnabled(true);

        if(topCard.getSuit() == Suit.DIAMONDS){
            trumpDiamondsButton.setVisibility(View.VISIBLE);
            trumpDiamondsButton.setEnabled(true);
        }
        else if(topCard.getSuit() == Suit.SPADES){
            trumpSpadesButton.setVisibility(View.VISIBLE);
            trumpSpadesButton.setEnabled(true);
        }
        else if(topCard.getSuit() == Suit.HEARTS){
            trumpHeartsButton.setVisibility(View.VISIBLE);
            trumpHeartsButton.setEnabled(true);
        }
        else if(topCard.getSuit() == Suit.CLUBS){
            trumpClubsButton.setVisibility(View.VISIBLE);
            trumpClubsButton.setEnabled(true);
        }
    }
    public void showOtherTrumpButtons(boolean screwTheDealer){
        hideAllTrumpButtons();
        if(topCard.getSuit() != Suit.DIAMONDS){
            trumpDiamondsButton.setVisibility(View.VISIBLE);
            trumpDiamondsButton.setEnabled(true);
        }
        if(topCard.getSuit() != Suit.SPADES){
            trumpSpadesButton.setVisibility(View.VISIBLE);
            trumpSpadesButton.setEnabled(true);
        }
        if(topCard.getSuit() != Suit.HEARTS){
            trumpHeartsButton.setVisibility(View.VISIBLE);
            trumpHeartsButton.setEnabled(true);
        }
        if(topCard.getSuit() != Suit.CLUBS){
            trumpClubsButton.setVisibility(View.VISIBLE);
            trumpClubsButton.setEnabled(true);
        }

        if(!screwTheDealer){
            trumpPassButton.setVisibility(View.VISIBLE);
            trumpPassButton.setEnabled(true);
        }
    }
    public void hideAllTrumpButtons(){
        trumpClubsButton.setVisibility(View.INVISIBLE);
        trumpDiamondsButton.setVisibility(View.INVISIBLE);
        trumpHeartsButton.setVisibility(View.INVISIBLE);
        trumpSpadesButton.setVisibility(View.INVISIBLE);
        trumpPassButton.setVisibility(View.INVISIBLE);
        imageButtonCC.setVisibility(View.INVISIBLE);
    }
    public void enableAllTrumpButtons(){
        trumpClubsButton.setEnabled(true);
        trumpDiamondsButton.setEnabled(true);
        trumpHeartsButton.setEnabled(true);
        trumpSpadesButton.setEnabled(true);
        trumpPassButton.setEnabled(true);
    }
    public void hideNonTrumpButtons(){
        hideAllTrumpButtons();
        switch(this.currentRound.getTrump()){
            default:
            case PASS:
                break;
            case SPADES:
                trumpSpadesButton.setVisibility(View.VISIBLE);
                trumpSpadesButton.setEnabled(false);
                break;
            case CLUBS:
                trumpClubsButton.setVisibility(View.VISIBLE);
                trumpClubsButton.setEnabled(false);
                break;
            case DIAMONDS:
                trumpDiamondsButton.setVisibility(View.VISIBLE);
                trumpDiamondsButton.setEnabled(false);
                break;
            case HEARTS:
                trumpHeartsButton.setVisibility(View.VISIBLE);
                trumpHeartsButton.setEnabled(false);
                break;
        }
    }
    public void disableCardButton(ImageButton btn){
        btn.setEnabled(false);
    }
    public void disableAllCardButtons(){
        disableCardButton(imageButtonTL);
        disableCardButton(imageButtonTC);
        disableCardButton(imageButtonTR);
        disableCardButton(imageButtonBL);
        disableCardButton(imageButtonBR);
    }
    public void enableCardButton(ImageButton btn){
        btn.setEnabled(true);
    }
    public void enableAllCardButtons(){
        enableCardButton(imageButtonTL);
        enableCardButton(imageButtonTC);
        enableCardButton(imageButtonTR);
        enableCardButton(imageButtonBL);
        enableCardButton(imageButtonBR);
    }

    public void pickItUp(){
        System.out.println("Player " + dealerIndex + " must pick up the top card.");
        if(players[dealerIndex].isAI()){
            players[dealerIndex].pickItUp(topCard, -1); //The index value does not matter here

            currentPlayerIndex = (dealerIndex + 1) % 4;
            startTrick();
        }
        else{

            //TODO: show instruction text ("Please choose a card to discard.")
            enableAllCardButtons();
            userHasToReplaceCard = true;
        }
    }

    public void startTrick(){
        System.out.println("New Trick has started, led by Player " + currentPlayerIndex + ".");
        currentRound.setCurrentTrick(new Trick());
        players[currentPlayerIndex].setLead(true);
        takeTurn();
    }

    public void takeTurn(){
        System.out.println("Player " + currentPlayerIndex + " is taking their turn.");

        Handler h = new Handler();
        h.postDelayed(new Runnable(){//waiting animation
        public void run() {
            if (players[currentPlayerIndex].isAI()) {
                Card play = players[currentPlayerIndex].playCard(currentRound.getCurrentTrick().getPlayedCards(), currentRound.getTrump());
                currentRound.getCurrentTrick().playerPlaysCard(play);
                //waitingAnimation();
                switch (currentPlayerIndex) {
                    default:
                        break;
                    case 1:
                        setCardImage(imageButtonOL, play.getSuit(), play.getValue());
                        break;
                    case 2:
                        setCardImage(imageButtonOF, play.getSuit(), play.getValue());
                        break;
                    case 3:
                        setCardImage(imageButtonOR, play.getSuit(), play.getValue());
                        break;
                }

                //Move to next player or end trick
                currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                if (players[currentPlayerIndex].isLead()) {
                    endTrick();
                } else {
                    //waitingAnimation();
                    takeTurn();
                }
            } else {
                enableAllCardButtons();
                //TODO: (optional?)
                // If any of the player's cards are the led suit, disable all the others
                //      else, keep all enabled that are still in hand
            }
        }
        }, 2000);

    }

    public void endTrick(){
        //TODO: Show who won trick through UI
        Handler h = new Handler();
        h.postDelayed(new Runnable(){//waiting animation
            public void run() {
        //Make card played by user invisible
        if(cardTLPlayed){
            imageButtonTL.setVisibility(View.INVISIBLE);
        }
        if(cardTCPlayed){
            imageButtonTC.setVisibility(View.INVISIBLE);
        }
        if(cardTRPlayed){
            imageButtonTR.setVisibility(View.INVISIBLE);
        }
        if(cardBLPlayed){
            imageButtonBL.setVisibility(View.INVISIBLE);
        }
        if(cardBRPlayed){
            imageButtonBR.setVisibility(View.INVISIBLE);
        }

        //find winner
        currentPlayerIndex = currentRound.getCurrentTrick().getWinningPlayerIndex(players, currentRound.getTrump());
        System.out.println("Trick over. Player " + currentPlayerIndex + " won the trick.");

        //Increment respective teams tricksTaken value
        if(userTeam.isPartOfTeam(players[currentPlayerIndex])){
            userTeam.incrementTricksTaken();
        }
        else if(pureAITeam.isPartOfTeam(players[currentPlayerIndex])){
            pureAITeam.incrementTricksTaken();
        }

        //Update tricksTaken on screen
        String tricksTakenStr = userTeam.getTricksTaken() + " - " + pureAITeam.getTricksTaken();
        tricksTakenCounter.setText(tricksTakenStr);

        //Set who leads next trick
        for(Player p:players){
            p.setLead(false);
        }
        players[currentPlayerIndex].setLead(true);

        currentRound.incrementTricksPlayed();
        if(currentRound.getNumTricksPlayed() == 5){
            endRound();
        }
        else {
            resetOtherImageButtons();
            startTrick();
        }

            }
        }, 2000);
    }
    public void endRound(){
        System.out.println("Round over.");

        //Add points to the correct team
        currentRound.awardPoints(userTeam, pureAITeam);
        String score = Integer.toString(userTeam.getTeamScore());
        userTeamScore.setText(score);
        score = Integer.toString(pureAITeam.getTeamScore());
        pureAITeamScore.setText(score);

        //Update tricksTaken on screen
        tricksTakenCounter.setText("0 - 0");

        //System.out.println("User Team has " + userTeam.getTeamScore() + " points.");
        //System.out.println("AI Team has " + pureAITeam.getTeamScore() + " points.");

        resetImageButtons();

        //reset card/image buttons boolean values
        cardTLPlayed = false;
        cardTCPlayed = false;
        cardTRPlayed = false;
        cardBLPlayed = false;
        cardBRPlayed = false;

        //reset trump buttons (including pass)
        hideAllTrumpButtons();
        enableAllTrumpButtons();

        //show deal/start button again to start another round
        startButton.setVisibility(View.VISIBLE);

        //update who is dealer & left of dealer
        dealerIndex = (dealerIndex + 1) % 4;
        currentPlayerIndex = (dealerIndex + 1) % 4;

        //reset other values needed
        userHasToReplaceCard = false;
        disableAllCardButtons();

        for(Player p:players){
            p.setLead(false);
        }

        if(userTeam.getTeamScore() >= maxPoints || pureAITeam.getTeamScore() >= maxPoints){
            endGame();
        }
    }
    public void endGame(){
        startButton.setVisibility(View.INVISIBLE);

        System.out.println("GAME OVER");
        if(userTeam.getTeamScore() >= maxPoints){
            System.out.println("YOU WON");
        }
        else{
            System.out.println("YOU LOST");
        }
    }

    public void resetImageButtons(){
        //Reset Card/image buttons to original position
        imageButtonTL.animate().translationX(0).translationY(0);
        imageButtonTC.animate().translationX(0).translationY(0);
        imageButtonTR.animate().translationX(0).translationY(0);
        imageButtonBL.animate().translationX(0).translationY(0);
        imageButtonBR.animate().translationX(0).translationY(0);

        //Make cards visible again
        imageButtonTL.setVisibility(View.VISIBLE);
        imageButtonTC.setVisibility(View.VISIBLE);
        imageButtonTR.setVisibility(View.VISIBLE);
        imageButtonBL.setVisibility(View.VISIBLE);
        imageButtonBR.setVisibility(View.VISIBLE);
        disableAllCardButtons();

        //set the cards to be the backs
        setCardImage(imageButtonTL, Suit.PASS, CardValue.NINE);
        setCardImage(imageButtonTC, Suit.PASS, CardValue.NINE);
        setCardImage(imageButtonTR, Suit.PASS, CardValue.NINE);
        setCardImage(imageButtonBL, Suit.PASS, CardValue.NINE);
        setCardImage(imageButtonBR, Suit.PASS, CardValue.NINE);

        resetOtherImageButtons();
    }

    public void resetOtherImageButtons(){
        setCardImage(imageButtonOL, Suit.PASS, CardValue.NINE);
        setCardImage(imageButtonOF, Suit.PASS, CardValue.NINE);
        setCardImage(imageButtonOR, Suit.PASS, CardValue.NINE);
    }

    private void printHands(){
        for(Player p:players){
            System.out.println("Player " + p.getPlayerNum());
            for(Card c:p.getPlayerHand()){
                System.out.println(c.getValue() + " of " + c.getSuit());
            }
            System.out.println();
        }

    }
}