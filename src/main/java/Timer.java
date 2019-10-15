import java.util.concurrent.TimeUnit;

public class Timer implements Runnable {
    private int clock;
    private int speed;
    private int sleepTime;

    Timer(int sleepTime, int speed){
        this.clock = 0;
        this.speed = sleepTime;
        this.sleepTime = sleepTime;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int addTime(int timeQty){
        return this.clock + timeQty;
    }

    public void run() {
        try {
            Thread.sleep(this.sleepTime * this.speed);
        } catch (InterruptedException e) {
        }

    }
}
