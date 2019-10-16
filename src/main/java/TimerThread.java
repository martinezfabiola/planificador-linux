import java.util.concurrent.TimeUnit;

public class TimerThread implements Runnable {
    Timer timer;

    public void run() {
        while (true) {

            try {
                Thread.sleep(this.timer.sleepTime * this.timer.speed);
            } catch (InterruptedException e) {
            }
            timer.tick();

        }

    }
}
