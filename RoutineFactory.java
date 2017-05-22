package com.zd.kadi.production.entities.routines;

import com.badlogic.gdx.utils.Array;


import com.zd.kadi.production.entities.Card;
import com.zd.kadi.production.entities.G_AI;

/**
 * Created by Kenneth on 03-May-17.
 */

public class RoutineFactory {

    public static Sequence Sequence(){
        return new Sequence();
    }

    public static Sequence Sequence( Routine routine){
        return new Sequence(routine);
    }

    public static Sequence Sequence( Routine... routines){
        return new Sequence(routines);
    }

    public static GetValidCards GetValidCards( Array<Card> hand,  Card topCard, Play playRoutine){
        return new GetValidCards(hand, topCard, playRoutine);
    }

    public static Play PlayRoutine(G_AI g_ai){
        return new Play(g_ai);
    }

}
