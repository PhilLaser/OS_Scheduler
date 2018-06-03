package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Queue;


public class lab2_solution implements Runnable {
    private static final int NUM_QUEUES = 15;
    private static String THREADNAME;
    private static int PRIORITY;
    private static int ARRIVAL_TIME;
    private static String BURSTS;
    private static List<Threaddy> waitingQueue = new ArrayList<>();
    private static List[] runningQueues = new ArrayList[NUM_QUEUES];

    private static String FILENAME;
    private static int QUANTUM;
    private static int CONTEXT_SWITCH_TIME;

    private static String line = "";


    public static void main(String[] args) throws Exception {
        /**
         * Reading arguments from the command line
         */
        FILENAME = args[0];
        QUANTUM = Integer.valueOf(args[1]);
        CONTEXT_SWITCH_TIME = Integer.valueOf(args[2]);


        File file = new File("/Users/temp/IdeaProjects/lab2_solution/src/app/" + FILENAME);
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
                THREADNAME = line.substring(0, line.indexOf("["));
                PRIORITY = Integer.valueOf(line.substring(line.indexOf("[") + 1, line.indexOf("]")));
                ARRIVAL_TIME = Integer.valueOf(line.substring(line.indexOf(":") + 2, line.indexOf(",")));
                BURSTS = line.substring(line.indexOf(",") + 1, line.length());
                for (int i = 0; i < NUM_QUEUES; i++) {
                    if (i == PRIORITY) runningQueues[i].add(new Threaddy(THREADNAME, PRIORITY, ARRIVAL_TIME, BURSTS));
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static Threaddy getMaxPrioThreaddy () {
        return waitingQueue.stream().max(Comparator.comparingInt(Threaddy::getPriority)).get();
    }

    @Override
    public void run() {

    }
}
