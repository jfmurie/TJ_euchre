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
    private TextView    instructionText;

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
    final int maxPoints = 10;

    boolean cardTLPlayed;
    boolean cardTCPlayed;
    boolean cardTRPlayed;
    boolean cardBLPlayed;
    boolean cardBRPlayed;

    /**
     * Initializes all necessary values
     *
     * @param savedInstanceState the instance of the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        userTeamScore      = (TextView) findViewById(R.id.T1Score);
        pureAITeamScore    = (TextView) findViewById(R.id.T2Score);
        tricksTakenCounter = (TextView) findViewById(R.id.tricksTakenCounter);
        instructionText    = (TextView) findViewById(R.id.instructionText);
        initializeButtons();
        initializeListeners();
        initializeGame();

        cardTLPlayed = false;
        cardTCPlayed = false;
        cardTRPlayed = false;
        cardBLPlayed = false;
        cardBRPlayed = false;
    }

    /**
     * Initializes all image buttons, trump calling buttons, and the start button
     * (aka the global variables that represent them)
     */
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

    /**
     * Initializes listeners and gives logic for when each button is clicked
     */
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

    /**
     * logic for when an image button/card is clicked
     *
     * @param button button clicked
     * @param cardIndex index of card in hav=nd that the button represents
     */
    public void imageButtonClicked(ImageButton button, int cardIndex){
        String text = "";
        instructionText.setText(text);

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

    /**
     * Method to move an image button to the middle
     *
     * @param cardButton the button to be moved
     */
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

    /**
     * sets the image for an image button to one of the card images
     *
     * @param handCard the button to change image of
     * @param suit the suit of the card image wanted
     * @param value the value of the card image wanted
     */
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

    /**
     * When the deal/start button is clicked
     *
     * @param view button
     */
    public void start(View view) {
        startButton.setVisibility(View.INVISIBLE);
        startRound();
    }

    /**
     * initialize variables for the game
     */
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

        String text = "Click 'Deal' to start the game.";
        instructionText.setText(text);
    }

    /**
     * (re)initialize variables for new round
     * call trump method
     */
    public void startRound(){
        if(userTeam.getTeamScore() < maxPoints && pureAITeam.getTeamScore() < maxPoints){
            String text = "";
            switch (dealerIndex){
                case 0:
                    text = "New Round has begun. You are the dealer";
                    break;
                case 1:
                    text = "New Round has begun. Dealer is left of you.";
                    break;
                case 2:
                    text = "New Round has begun. Dealer is your teammate.";
                    break;
                case 3:
                    text = "New Round has begun. Dealer is right of you.";
                    break;
                default: break;
            }
            instructionText.setText(text);

            currentRound = new Round();
            topCard = dealCards();
            printHands();
            callTrump(0);
        }
    }

    /**
     * method to deal cards and show correct cards to user
     *
     * @return topCard
     */
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

    /**
     * Method to ask player to call trump
     *
     * @param timesAround number of times around the "table"
     */
    public void callTrump(int timesAround){
        Pair<Suit, Boolean> result;

        if(players[currentPlayerIndex].isAI()){
            result = players[currentPlayerIndex].callTrump(topCard, timesAround == 1, currentPlayerIndex == dealerIndex);
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
            if(timesAround == 0){
                showCardUpSuitButton();
            }
            else {
                showOtherTrumpButtons(dealerIndex == 0);
            }
        }
    }

    /**
     * when trump is called, tell backend, and call pickItUp or startTrick()
     *
     * @param trump suit that's been called
     * @param teamCalled team that called it
     */
    public void trumpCalled(Suit trump, Team teamCalled){
        String text = "Trump has been called as: " + trump;
        instructionText.setText(text);

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

    /**
     * show only the suit  trumpbutton for user to click or pass
     */
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

    /**
     * show all other buttons other than the suit of the topCard
     * if user is dealer, do not show pass button
     *
     * @param screwTheDealer whether or not the dealer/user HAS to pick a trump
     */
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

    /**
     * hide all trump buttons
     * Used for resets, usually
     */
    public void hideAllTrumpButtons(){
        trumpClubsButton.setVisibility(View.INVISIBLE);
        trumpDiamondsButton.setVisibility(View.INVISIBLE);
        trumpHeartsButton.setVisibility(View.INVISIBLE);
        trumpSpadesButton.setVisibility(View.INVISIBLE);
        trumpPassButton.setVisibility(View.INVISIBLE);
        imageButtonCC.setVisibility(View.INVISIBLE);
    }

    /**
     * enable all Trump Buttons
     * used for reset usually
     */
    public void enableAllTrumpButtons(){
        trumpClubsButton.setEnabled(true);
        trumpDiamondsButton.setEnabled(true);
        trumpHeartsButton.setEnabled(true);
        trumpSpadesButton.setEnabled(true);
        trumpPassButton.setEnabled(true);
    }

    /**
     * hide the buttons that do not represent trump
     */
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

    /**
     * disable a specific image/card button
     *
     * @param btn the button to disable
     */
    public void disableCardButton(ImageButton btn){
        btn.setEnabled(false);
    }

    /**
     * disable all image/card buttons so User cannot click them
     */
    public void disableAllCardButtons(){
        disableCardButton(imageButtonTL);
        disableCardButton(imageButtonTC);
        disableCardButton(imageButtonTR);
        disableCardButton(imageButtonBL);
        disableCardButton(imageButtonBR);
    }

    /**
     * enable specific image/card button
     * @param btn button to enable
     */
    public void enableCardButton(ImageButton btn){
        btn.setEnabled(true);
    }

    /**
     * enable all card/image buttons
     */
    public void enableAllCardButtons(){
        enableCardButton(imageButtonTL);
        enableCardButton(imageButtonTC);
        enableCardButton(imageButtonTR);
        enableCardButton(imageButtonBL);
        enableCardButton(imageButtonBR);
    }

    /**
     * Called when topCard's suit is called as trump
     * the dealer has to discard a card and pick up the top card into hand
     */
    public void pickItUp(){
        if(players[dealerIndex].isAI()){
            players[dealerIndex].pickItUp(topCard, -1); //The index value does not matter here

            currentPlayerIndex = (dealerIndex + 1) % 4;
            startTrick();
        }
        else{
            String text = "You are dealer. Please choose a card to discard.";
            instructionText.setText(text);

            enableAllCardButtons();
            userHasToReplaceCard = true;
        }
    }

    /**
     * start a new trick,
     * set who is leading and call their turn to happen
     */
    public void startTrick(){
        //System.out.println("New Trick has started, led by Player " + currentPlayerIndex + ".");
        currentRound.setCurrentTrick(new Trick());
        players[currentPlayerIndex].setLead(true);
        takeTurn();
    }

    /**
     * have current player to take their turn to play a card
     */
    public void takeTurn(){
        String text = "";
        instructionText.setText(text);

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
                String text = "Please choose a card to play.";
                instructionText.setText(text);
                enableAllCardButtons();
            }
        }
        }, 2000);

    }

    /**
     * reset needed values
     * if 5 tricks have been played, end the round
     * else, just start next trick
     */
    public void endTrick(){
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

        String text = "";
        switch (currentPlayerIndex){
            case 0:
                text = "Trick over. You won the trick.";
                break;
            case 1:
                text = "Trick over. Player left of you took the trick.";
                break;
            case 2:
                text = "Trick over. Your teammate took the trick.";
                break;
            case 3:
                text = "Trick over. Player right of you took the trick.";
                break;
            default: break;
        }
        instructionText.setText(text);


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

    /**
     * award points, reset all buttons, update scoreboard
     * if game it over, call gameOver
     * else start new round, moving dealerIndex by 1
     */
    public void endRound(){

        String text = "Round over. Click Deal to continue";
        instructionText.setText(text);

        //Add points to the correct team
        currentRound.awardPoints(userTeam, pureAITeam);
        String score = Integer.toString(userTeam.getTeamScore());
        userTeamScore.setText(score);
        score = Integer.toString(pureAITeam.getTeamScore());
        pureAITeamScore.setText(score);

        //Update tricksTaken on screen
        tricksTakenCounter.setText("0 - 0");
        userTeam.resetTricksTaken();
        pureAITeam.resetTricksTaken();

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

    /**
     * tell who won and endGame
     */
    public void endGame(){
        startButton.setVisibility(View.INVISIBLE);
        String text = "GAME OVER: ";
        if(userTeam.getTeamScore() >= maxPoints){
            text += "YOU WON";
        }
        else{
            text += "YOU LOST";
        }
        instructionText.setText(text);
    }

    /**
     * method to reset image buttons to original spot and have back of card image
     */
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

    /**
     * reset AIs' image buttons
     */
    public void resetOtherImageButtons(){
        setCardImage(imageButtonOL, Suit.PASS, CardValue.NINE);
        setCardImage(imageButtonOF, Suit.PASS, CardValue.NINE);
        setCardImage(imageButtonOR, Suit.PASS, CardValue.NINE);
    }

    /**
     * For testing purposes, print the hands of all players
     */
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