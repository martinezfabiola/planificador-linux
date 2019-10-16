public class Core implements Runnable {

    Integer id;
    CoresWorker coresWorker;

    Core(Integer id, CoresWorker tracker) {
        this.id = id;
        this.coresWorker = tracker;
    }

    public void run() {
        while (true) {
            this.coresWorker.runProcess(this.id);
        }
    }
}
