import java.util.ArrayList;
import java.util.List;

public class CPU {
    Integer id;
    Integer coresQty;
    List<Core> cores = new ArrayList<>();
    CoresWorker coresWorker;
    private RBTree<Integer> tree;
    private Timer timer;


    CPU(Integer id, Integer quantum, Integer coresQty, RBTree<Integer> tree, Timer timer, IOHandler handler) {
        this.id = id;
        this.coresQty = coresQty;
        this.tree = tree;
        this.timer = timer;
        this.coresWorker = new CoresWorker(tree, timer, coresQty, quantum);
        for (Integer i = 0; i < coresQty; i++) {
            cores.add(new Core(i, this.coresWorker));
        }
    }

    public synchronized void dispatchProcess(Process process) {
        for (int i = 0; i < this.coresQty; i++) {
            if (this.coresWorker.cores.get(i)) {
                this.coresWorker.dispatchProcess(process, i);
            }
        }
    }
}


class CoresWorker {
    List<Boolean> cores = new ArrayList<>();
    List<Process> processes = new ArrayList<>();
    RBTree<Integer> tree;
    Timer timer;
    Integer quantum;

    CoresWorker(RBTree<Integer> tree, Timer timer, Integer qty, Integer quantum) {
        this.quantum = quantum;
        this.timer = timer;
        this.tree = tree;
        for (Integer i = 0; i < qty; i++){
            cores.add(false);
            processes.add(null);
        }
    }


    public synchronized void dispatchProcess(Process process, int id){
        this.processes.set(id, process);
        this.cores.set(id, false);
    }


    public void runProcess(Integer id) {
        while (!this.cores.get(id)) {
            try {
                Thread.sleep(timer.sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Process process = processes.get(id);
        Integer cpuTime = process.getNextCpuTime();
        processes.set(id, null);
        Integer initTime = this.timer.getTime();
        Integer time = timer.getTime();
        while (time - initTime < quantum && cpuTime > 0) {
            try {
                Thread.sleep(timer.sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time = timer.getTime();
            cpuTime = cpuTime - 1;
        }

        if (cpuTime == 0) {
            new IOHandler(this.tree, process, this.timer);
        } else {
            // TODO: Agregar el proceso de nuevo al arbol cambiando el vruntime
            process.remainingTime(cpuTime);
        }


    }
}
