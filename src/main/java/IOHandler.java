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
        System.out.println("Corriendo ioHandler");
        try {
            Thread.sleep(timer.sleepTime * waitingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        process.setState("Ready");
        tree.add(process.vruntime, process);
    }
}
