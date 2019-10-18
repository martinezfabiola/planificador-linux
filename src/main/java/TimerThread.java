import java.util.concurrent.TimeUnit;

public class TimerThread implements Runnable {
    Timer timer;

    TimerThread(Timer timer){
        this.timer = timer;
    }

    public void run() {
        while (true) {

            try {
                Thread.sleep(this.timer.sleepTime);
            } catch (InterruptedException e) {
            }
            timer.tick();
        }
    }
}
