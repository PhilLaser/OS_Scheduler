
class Threaddy {
    IOBurstList;
    CPUBurstList;
    constructor(name, priority, arrivalTime, bursts) {
        this.name = name;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        /**
         * Splitting up the bursts array into two separate arrays
         * and removing unnecessary parentheses etc.
         */
        this.bursts = bursts.replace("\\s", "");
        this.array = this.bursts.split(",");

        for (let i = 0; i < this.array.length; i++) {
            if (this.array[i].contains("(")) {
                this.array[i] = this.array[i].replace("(", "");
            } else if (this.array[i].contains(")")) {
                this.array[i] = this.array[i].replace(")", "");
            }
            if (i % 2 !== 0) this.IOBurstList.add(this.array[i]);

            else this.CPUBurstList.add(this.array[i]);

        }
    }
}




