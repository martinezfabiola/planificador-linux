public class IOHandler implements Runnable {
    RBTree<Integer> tree;
    Process process;
    Timer timer;

    IOHandler(RBTree<Integer> tree, Process process, Timer timer) {
        this.tree = tree;
        this.process = process;
        this.timer = timer;
    }

    public void run() {
        Integer waitingTime = this.process.getNextIoTime();
        try {
            Thread.sleep(timer.sleepTime * waitingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tree.add(process.vruntime, process);
    }
}
