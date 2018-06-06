package app;


public class Process implements Runnable{
    Threaddy threaddy;
    Burst burst;
    State threadState;

    public Process(Threaddy t, Burst b){
        this.threaddy = t;
        this.burst = b;
    }


    @Override
    public void run() {

    }
}
