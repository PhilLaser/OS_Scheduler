package app;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class lab2_solution {

    private static int timer = 0;
    private static int ioBurst = 0;
    private static int cpuBurst = 0;
    private static final int NUM_QUEUES = 15;
    private static String THREADNAME;
    private static int PRIORITY;
    private static int ARRIVAL_TIME;
    private static String BURSTS;
    private static List<Threaddy> queue = new ArrayList<>();
    private static List[] runningQueues = new ArrayList[NUM_QUEUES];

    private static String FILENAME;
    private static int QUANTUM;
    private static int CONTEXT_SWITCH_TIME;

    private static String line = "";

    private static String bursts;
    private static String[] array;
    private static List<Integer> CPUBurstList;
    private static List<Integer> IOBurstList;


    public static void main(String[] args) throws Exception {
        /**
         * Reading arguments from the command line
         */
        FILENAME = args[0];
        QUANTUM = Integer.valueOf(args[1]);
        CONTEXT_SWITCH_TIME = Integer.valueOf(args[2]);
        File file = new File("/Users/marshmallow/IdeaProjects/OS_Scheduler/src/app/" + FILENAME);

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
                bursts = BURSTS.replaceAll("\\s", "");
                array = bursts.split(",");
                CPUBurstList = new ArrayList<>();
                IOBurstList = new ArrayList<>();

                for (int i = 0; i < array.length; i++) {
                    if (array[i].contains("(")) {
                        array[i] = array[i].replace("(", "");
                    } else if (array[i].contains(")")) {
                        array[i] = array[i].replace(")", "");
                    }

                    if (i % 2 != 0) IOBurstList.add(Integer.valueOf(array[i]));

                    else CPUBurstList.add(Integer.valueOf(array[i]));
                    if (i % 2 != 0){
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


        while (timer < 30) {
            timer++;
            checkArrivalThreads();
            //runThreads();
            //runBlockedThreads();
        }
    }

    private static void runThreads() {
        for (List<Threaddy> l : runningQueues){
            for (Threaddy t : l) t.run();
        }
    }

    private static void checkArrivalThreads() {
        for (Threaddy t : queue) {
            if (t.getArrivalTime() == timer) {
                runningQueues[t.getPriority()].add(t);
                queue.remove(t);
            }
        }
    }




    private boolean threadsExist() {
        for (List rQ : runningQueues) return rQ.isEmpty();
        return false;
    }

    /*private static Threaddy getMaxPrioThreaddy() {
        return waitingQueue.stream().max(Comparator.comparingInt(Threaddy::getPriority)).get();
    }*/


}
