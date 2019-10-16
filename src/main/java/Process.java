import java.util.List;

public class Process {
    private Integer pid;
    Integer priority;
    Integer arrivingTime;
    private List<Integer> cpuTimes;
    private List<Integer> ioTimes;
    Integer vruntime;

    Process(Integer pid, Integer priority, Integer arrivingTime, List<Integer> cpuTimes, List<Integer> ioTimes) {
        this.pid = pid;
        this.priority = priority;
        this.arrivingTime = arrivingTime;
        this.cpuTimes = cpuTimes;
        this.ioTimes = ioTimes;
        this.vruntime = 0;
    }


    public Integer getNextCpuTime() {
        if (! this.cpuTimes.isEmpty()){
            Integer nextCpuTime = this.cpuTimes.get(0);
            this.cpuTimes.remove(0);
            return nextCpuTime;
        }

        return null;
    }


    public void remainingTime(Integer time) {
        this.cpuTimes.add(0, time);
    }


    public Integer getNextIoTime() {
        if (! this.ioTimes.isEmpty()){
            Integer nextIoTime = this.ioTimes.get(0);
            this.ioTimes.remove(0);
            return nextIoTime;
        }

        return null;
    }


    public boolean hasAnotherIo() {
        return !this.ioTimes.isEmpty();
    }


    public Integer getPid() {
        return this.pid;
    }
}
