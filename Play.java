package com.zd.kadi.production.entities.routines;

import com.badlogic.gdx.utils.Array;
import com.zd.kadi.production.entities.Card;
import com.zd.kadi.production.entities.CenterPile;
import com.zd.kadi.production.entities.G_AI;
import com.zd.kadi.production.screens.Screen;

import java.util.Random;

/**
 * Created by Kenneth on 03-May-17.
 */

public class Play extends Routine {
    public static final String TAG = Play.class.getSimpleName()+"Routine";

     Array<Card> validCards;
    private boolean PICK_CARD;
    //Test 1: Play Random card
    private Random random;
    private G_AI g_ai;
    private CenterPile centerPile;

    Play(G_AI g_ai){
        this.g_ai = g_ai;
        validCards = new Array<Card>();
        PICK_CARD = false;

        random = new Random();
    }

    @Override
    public void start() {
        super.start();
        Screen.print(TAG, "<<Starting Play>>");
    }

    @Override
    public void reset() {
        Screen.print(TAG, "<<Resetting Play>>");
        validCards.clear();
        random = new Random();
        PICK_CARD = false;
    }

    @Override
    public void act() {
        Screen.print(TAG, "<<Act PlayRoutine>>");
        if(isRunning()){
            if(!PICK_CARD) {
                Screen.print(TAG, "[act] validCards size: "+ validCards.size);
                /*
                TODO: Have the AI make decisions on which card to pick based on Card Value, Difficulty and Learning from Player Strategy(Possibly in later version
                 */
                //Current test, AI randomizes indices and chooses a card(hardly intelligent)
                int cardIndex = 0;
                //if only one card no need to randomize, just pick index 0
                if(validCards.size == 1) cardIndex = 0;
                else//Otherwise randomize
                    cardIndex = random.nextInt(validCards.size);
                Screen.print(TAG, "[act] playing {"+validCards.get(cardIndex).getNumberSuit()+"}");
                g_ai.playCard(validCards.get(cardIndex));
                success();
            }
            else{
                //AI Draw card
                Screen.print(TAG, "<<[act]picking card...>>");
                g_ai.pickCard();
                success();
            }
        }
    }

    @Override
    public void success() {
        super.success();
        Screen.print(TAG, "<<Success PlayRoutine>>");
    }

    public void add(Card card){
        Screen.print(TAG, "<<[add] card {"+card.getNumberSuit()+"} added>>");
        validCards.add(card);
    }

    public void setPICK_CARD(boolean PICK_CARD) {
        this.PICK_CARD = PICK_CARD;
    }
}
