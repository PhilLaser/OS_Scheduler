package app;

import java.util.ArrayList;
import java.util.List;

public class Threaddy {

    private State state;
    private String name;
    private int priority;
    private int arrivalTime;
    private List<Burst> bursts;
    private int timeAlive;
    private boolean isCurrentThreaddy;


    Threaddy(String name, int priority, int arrivalTime, List<Burst> bursts) {
        this.name = name;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.bursts = bursts;
        this.state = State.RUNNING;


    }

    public State run() {
        State burstState;
        if (isCurrentThreaddy) {
            burstState = bursts.get(0).run();
            checkBursts(burstState);
            if (burstState == State.BLOCKED) {
                setState(State.BLOCKED);
            }
            timeAlive++;
        } else {
            if (state == State.BLOCKED) {
                burstState = bursts.get(0).run();
                checkBursts(burstState);
                timeAlive++;
            } else {
                timeAlive++;
            }
        }
        return getState();
    }

    private void checkBursts(State burstState) {
        if (burstState == State.FINISHED) {
            bursts.remove(0);
            if (bursts.isEmpty()) {
                setState(State.FINISHED);
            } else setState(State.RUNNING);
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

}
