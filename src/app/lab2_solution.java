package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class lab2_solution {

    private static int timer = 0;

    private static final int NUM_QUEUES = 15;
    private static String THREADNAME;
    private static int PRIORITY;
    private static int ARRIVAL_TIME;
    private static String BURSTS;
    private static List<Threaddy> queue = new ArrayList<>();
    private static List<Threaddy> blockedThreads = new ArrayList<>();
    private static List<Threaddy> finishedThreads = new ArrayList<>();
    private static List[] runningQueues = new ArrayList[NUM_QUEUES];

    private static String FILENAME;
    private static int QUANTUM;
    private static int CONTEXT_SWITCH_TIME;

    private static String line = "";

    private static Threaddy current;
    private static int countQuantum = 0;
    private static int countContextSwitchTime = 0;


    public static void main(String[] args) throws Exception {
        /**
         * Reading arguments from the command line
         */
        FILENAME = args[0];
        QUANTUM = Integer.valueOf(args[1]);
        CONTEXT_SWITCH_TIME = Integer.valueOf(args[2]);
        File file = new File(FILENAME);

        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            for (int i = 0; i < NUM_QUEUES; i++) {
                runningQueues[i] = new ArrayList();
            }
            /**
             * Getting the values from the file and pushing a new Threaddy object
             * in the corresponding Priority queue
             *
             */
            while ((line = reader.readLine()) != null) {
                List<Burst> burstList = new ArrayList<>();
                THREADNAME = line.substring(0, line.indexOf("["));
                PRIORITY = Integer.valueOf(line.substring(line.indexOf("[") + 1, line.indexOf("]")));
                ARRIVAL_TIME = Integer.valueOf(line.substring(line.indexOf(":") + 2, line.indexOf(",")));
                BURSTS = line.substring(line.indexOf(",") + 1, line.length());
                /**
                 * Splitting up the bursts array into two separate arrays
                 * and removing unnecessary parentheses etc.
                 */
                String bursts = BURSTS.replaceAll("\\s", "");
                String[] array = bursts.split(",");
                List<Integer> CPUBurstList = new ArrayList<>();
                List<Integer> IOBurstList = new ArrayList<>();

                for (int i = 0; i < array.length; i++) {
                    if (array[i].contains("(")) {
                        array[i] = array[i].replace("(", "");
                    } else if (array[i].contains(")")) {
                        array[i] = array[i].replace(")", "");
                    }

                    if (i % 2 != 0) IOBurstList.add(Integer.valueOf(array[i]));

                    else CPUBurstList.add(Integer.valueOf(array[i]));
                    if (i % 2 != 0) {
                        burstList.add(new Burst(CPUBurstList.get(0), IOBurstList.get(0)));
                        CPUBurstList.remove(0);
                        IOBurstList.remove(0);
                    }

                }
                queue.add(new Threaddy(THREADNAME, PRIORITY, ARRIVAL_TIME, burstList));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        while (threadsRemaining()) {
            timer++;

            checkArrivalThreads();

            if (current == null) {
                getMaxPrioThreaddy();
            }
            if (current != null) {
                if (countContextSwitchTime > 0) {
                    countContextSwitchTime--;
                    current.live();
                } else {
                    runCurrentThread();
                }
            }

            runThreads();
            runBlockedThreads();


        }
        printFinishedThreads();
    }

    private static void runBlockedThreads() {
        List<Threaddy> remove = new ArrayList<>();
        for (Threaddy t : blockedThreads){
            State state = t.run(false);
            if (state == State.FINISHED) {
                finishedThreads.add(t);
                remove.add(t);
            } else if (state == State.RUNNING) {
                runningQueues[t.getPriority()].add(t);
                remove.add(t);
            }
        }
        for (Threaddy t : remove) {
            blockedThreads.remove(t);
        }
    }

    private static void runThreads() {
        for (List<Threaddy> l : runningQueues) {
            for (Threaddy t : l) {
                t.run(false);
            }
        }
    }

    private static void checkArrivalThreads() {
        List<Threaddy> removing = new ArrayList<>();
        for (Threaddy t : queue) {
            if (t.getArrivalTime() == timer) {
                runningQueues[t.getPriority()].add(t);
                removing.add(t);

            }
        }
        for (Threaddy t: removing){
            queue.remove(t);
        }
    }

    private static void getMaxPrioThreaddy() {
        countContextSwitchTime = CONTEXT_SWITCH_TIME;
        countQuantum = 0;
        if(!runningQueueRemaining()) {
            current = null;
        } else {
            for (List<Threaddy> l : runningQueues){
                if (!l.isEmpty()){
                    current = l.get(0);
                    l.remove(l.get(0));
                    break;
                }
            }
        }

    }

    private static void runCurrentThread(){
        countQuantum++;
        State state = current.run(true);
        if (state == State.BLOCKED) {
            blockedThreads.add(current);
            getMaxPrioThreaddy();
        } else if (state == State.FINISHED) {
            finishedThreads.add(current);
            getMaxPrioThreaddy();
        }
        if (countQuantum == QUANTUM){
            runningQueues[current.getPriority()].add(current);
            getMaxPrioThreaddy();
        }
    }

    private static boolean threadsRemaining(){
        if (runningQueueRemaining()) {
            return true;
        }
        if (!queue.isEmpty()) {
            return true;
        }
        if (!blockedThreads.isEmpty()) {
            return true;
        }
        if (current != null) {
            return true;
        }
        return false;
    }

    private static boolean runningQueueRemaining() {
        for (List<Threaddy> tl : runningQueues) {
            if (!tl.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static void printFinishedThreads() {
        for (Threaddy t : finishedThreads) {
            System.out.println(t.getName() + ": " + t.getArrivalTime() + ", " + t.getTimeAlive());
        }
    }


}
