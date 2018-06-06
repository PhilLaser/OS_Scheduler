package app;

public abstract class Burst implements Runnable {
    int timer;


    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return timer;
    }
}
