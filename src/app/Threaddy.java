package app;

import java.util.ArrayList;
import java.util.List;

public class Threaddy {

    private String name;
    private int priority;
    private int arrivalTime;
    private String bursts;
    private String[] array;
    private List<Integer> CPUBurstList;
    private List<Integer> IOBurstList;

    Threaddy(String name, int priority, int arrivalTime, String bursts){
        this.name = name;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        /**
         * Splitting up the bursts array into two separate arrays
         * and removing unnecessary parentheses etc.
         */
        this.bursts = bursts.replaceAll("\\s", "");
        this.array = this.bursts.split(",");
        this.CPUBurstList = new ArrayList<>();
        this.IOBurstList = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            if (this.array[i].contains("(")){
                this.array[i] = this.array[i].replace("(", "");
            }else if (this.array[i].contains(")")){
                this.array[i] = this.array[i].replace(")", "");
            }
            if (i % 2 != 0) this.IOBurstList.add(Integer.valueOf(this.array[i]));

            else this.CPUBurstList.add(Integer.valueOf(this.array[i]));


        }
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

    public List<Integer> getCPUBurstList() {
        return CPUBurstList;
    }

    public void setCPUBurstList(List<Integer> CPUBurstList) {
        this.CPUBurstList = CPUBurstList;
    }

    public List<Integer> getIOBurstList() {
        return IOBurstList;
    }

    public void setIOBurstList(List<Integer> IOBurstList) {
        this.IOBurstList = IOBurstList;
    }
}
