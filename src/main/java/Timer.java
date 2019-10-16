public class Timer {
    private int clock;
    int speed;
    int sleepTime;

    Timer(int sleepTime){
        this.clock = 0;
        this.sleepTime = sleepTime;
    }

    public int getTime() {
        return this.clock;
    }

    public synchronized void tick(){
        this.clock = this.clock + 1;
    }
}
