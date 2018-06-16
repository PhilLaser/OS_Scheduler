package app;

import java.util.List;

public class Threaddy {

    private State state;
    private String name;
    private int priority;
    private int arrivalTime;
    private List<Burst> bursts;
    private int timeAlive;


    Threaddy(String name, int priority, int arrivalTime, List<Burst> bursts) {
        this.name = name;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.bursts = bursts;
        this.state = State.RUNNING;


    }

    public State run(boolean isCurrentThreaddy) {
        State burstState;
        if (isCurrentThreaddy) {
            burstState = bursts.get(0).run();
            checkBursts(burstState);
            if (burstState == State.BLOCKED) {
                setState(State.BLOCKED);
            }
            timeAlive++;
        } else if (state == State.BLOCKED) {
                burstState = bursts.get(0).run();
                checkBursts(burstState);
                timeAlive++;
            } else {
                timeAlive++;
            }
        return getState();
    }

    public void live() {
        timeAlive++;
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

    public int getTimeAlive() {
        return timeAlive;
    }

    public void setTimeAlive(int timeAlive) {
        this.timeAlive = timeAlive;
    }

    public String getName() {
        return name;
    }
}
