package com.zd.kadi.production.entities.routines;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.zd.kadi.production.entities.Card;
import com.zd.kadi.production.screens.Screen;

/**
 * Execute the routines in the order they were added
 * The next routine will only execute after the preceding routine succeeds
 */

public class Sequence extends Routine {

    public static final String TAG = Sequence.class.getSimpleName();

    private Routine currentRoutine;
    private Queue<Routine> routineQueue;

    Sequence(){
        currentRoutine = null;
        routineQueue = new Queue<Routine>();
    }

    Sequence(Routine routine){
        currentRoutine = null;
        routineQueue = new Queue<Routine>();
        routineQueue.addFirst(routine);
    }

    Sequence(Routine... routines){
        currentRoutine = null;
        routineQueue = new Queue<Routine>();

        for (Routine routine :
                routines) {
            routineQueue.addFirst(routine);
        }
    }

    @Override
    public void start() {
        super.start();
        Screen.print(TAG, "<<Starting sequence>>");
        Screen.print(TAG, "[start] routineQueue size: "+ routineQueue.size);
        currentRoutine = routineQueue.removeFirst();
        currentRoutine.start();
    }

    @Override
    public void act() {
        Screen.print(TAG, "<<Act Sequence>>");
        if(isRunning()) {
            //Make sure there is a Routine to run
            if (currentRoutine == null) {
                currentRoutine = routineQueue.removeFirst();
            }

            currentRoutine.act();

            if(currentRoutine.getState() == State.FAILURE){
                fail();
                return;
            }

            //If no more routine to run, set state of sequence to state of final routine
            if(routineQueue.size <=0 ){
                this.setState(currentRoutine.getState());
            }
            else{
                if(currentRoutine.isSuccess()) {
                    currentRoutine = routineQueue.removeFirst();
                    currentRoutine.start();
                }
            }

        }

    }

    @Override
    public void reset() {
        Screen.print(TAG, "<<Resetting sequence>>");
        currentRoutine = null;
        routineQueue.clear();
    }

    public void addRoutine(Routine routine) {
        routineQueue.addLast(routine);
        Screen.print(TAG, "[addRoutine] routineQueue size: "+ routineQueue.size );
    }

    public Queue<Routine> getRoutineQueue() {
        return routineQueue;
    }
}
