package com.zd.kadi.production.entities.routines;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.zd.kadi.production.entities.Card;
import com.zd.kadi.production.ref.CardValidator;
import com.zd.kadi.production.screens.GameScreen;
import com.zd.kadi.production.screens.Screen;

/**
 *
 */

public class GetValidCards extends Routine {

    public static final String TAG = GetValidCards.class.getSimpleName();

    private Array<Card> hand, validCards;
    private Queue<Card> handQueue;
    private CardValidator cardValidator;
    private Card currentCard, center_card;
    private Play playRoutine;
    private CardValidator.Status status;

    private boolean VALID_CARD_FOUND;


    public GetValidCards(Array<Card> hand, Card center_card, Play playRoutine) {
        this.hand = hand;
        this.center_card = center_card;
        this.playRoutine = playRoutine;
        cardValidator = new CardValidator();
        handQueue = new Queue<Card>();
        validCards = new Array<Card>();
        currentCard = null;
        status = null;

        for (Card card :
                hand) {
            //Add crd to back of queue; will use this queue in validation
            handQueue.addLast(card);
        }
        VALID_CARD_FOUND = false;
    }

    @Override
    public void start() {
        super.start();

        Screen.print(TAG, "<<Starting GetValidCards>>");

        //get first card from hand
        if(handQueue.size > 0)
            currentCard = handQueue.removeFirst();

    }

    /**
     * <b>Must call {@link GetValidCards#setHand(Array)} before calling reset!!</b>
     */
    @Override
    public void reset(){
        Screen.print(TAG, "<<Resetting GetValidCards>>");
        status = null;
        currentCard = null;
        VALID_CARD_FOUND = false;
        center_card = GameScreen.center_pile.getTopCard();
        handQueue.clear();
        for (Card card :
                hand) {
            Screen.print(TAG, "[reset] card in hand {"+card.getNumberSuit()+"}");
            //Add card to back of queue; will use this queue in validation
            handQueue.addLast(card);
        }
        if(handQueue.size > 0)
            currentCard = handQueue.removeFirst();
        //else throw new RuntimeException("queue is empty: try calling setHand first");
    }

    @Override
    public void act() {
        Screen.print(TAG, "<<Act GetValidCards>>");
        /*
        Will check all card from hand against validator
        and add to validCards array
        */
        if(isRunning()) {
            //If queue(hand) empty, then success
            if(handQueue.size <= 0 && currentCard == null){
                if(status != null) {
                    if(!VALID_CARD_FOUND){
                        Screen.print(TAG, "[act] setting Play to PICK...");
                        playRoutine.setPICK_CARD(true);
                        success();
                    }
                    else {
                        Screen.print(TAG, "[act]all cards checked, success");
                        success();
                    }
                }
                else {
                    Screen.print(TAG, "[act] no card in hand, setting Play to PICK...");
                    playRoutine.setPICK_CARD(true);
                    success();
                }
            }
            else{
                Screen.print(TAG, "[act] validating");
                //Take current card and Validate
                //If no card on CenterPile, status is Valid
                if(center_card == null)
                    status = CardValidator.Status.VALID;
                else
                    status = cardValidator.validatePlay(center_card, currentCard);
                //If card is NOT INVALID(Valid), pass to Play
                if(status != CardValidator.Status.INVALID){
                    Screen.print(TAG, "[act]card Valid, adding to Play queue");
                    playRoutine.add(currentCard);
                    VALID_CARD_FOUND = true;
                }
                //Set currentCard to nextCard in Queue
                if(handQueue.size > 0) {
                    currentCard = handQueue.removeFirst();
                    status = null;
                }
                else {
                    currentCard = null;
                }
            }
        }
    }

    public void setHand(Array<Card> hand) {
        this.hand = hand;
    }

    @Override
    public void success() {
        super.success();
        Screen.print(TAG, "<<Success GetValidCards>>");
    }

    @Override
    public void fail() {
        super.fail();
        Screen.print(TAG, "<<Success GetValidCards>>");
    }


}
