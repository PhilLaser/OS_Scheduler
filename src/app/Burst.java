package app;

public class Burst {

    private int cpuBurst, ioBurst;


    Burst(int CPUBurst, int IOBurst) {
        this.cpuBurst = CPUBurst;
        this.ioBurst = IOBurst;

    }


    public State run() {
        if (cpuBurst != 0) {
            cpuBurst--;
            if (cpuBurst == 0 && ioBurst == 0) return State.FINISHED;
            if (cpuBurst == 0) return State.BLOCKED;
            else return State.RUNNING;
        } else {
            ioBurst--;
            if (ioBurst == 0) return State.FINISHED;
            return State.BLOCKED;
        }
    }
}
