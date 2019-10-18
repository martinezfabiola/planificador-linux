public class Dispatcher implements Runnable {
    CPU cpu;
    RBTree<Integer> tree;

    Dispatcher(CPU cpu, RBTree<Integer> tree) {
        this.cpu = cpu;
        this.tree = tree;
    }

    public void run() {
        while (true) {
            if (cpu.coresWorker.cores.contains(false) && tree.hasElements()) {
                System.out.println(cpu.coresWorker.cores);
                cpu.dispatchProcess(tree.getMinimum().process);
            }
        }
    }
}
