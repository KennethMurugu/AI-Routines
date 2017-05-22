package com.zd.kadi.production.entities.routines;

/**
 *"Brain" of the AI. Will serve as a basis for all  actions performed by the AI player
 */

abstract class Routine {

    private State state;

    enum State{
        RUNNING, SUCCESS, FAILURE;
    }

    public void start(){
        this.state = State.RUNNING;
    }

    public abstract void act();

    public abstract void reset();

    public final State getState() {
        return state;
    }

    public final void setState(State state) {
        this.state = state;
    }

    public void success(){
        state = State.SUCCESS;
    }

    public void fail(){
        state = State.FAILURE;
    }

    public final boolean isSuccess(){
        return state == State.SUCCESS;
    }

    public final boolean isRunning(){
        return state == State.RUNNING;
    }
}
