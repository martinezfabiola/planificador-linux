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
    VRuntimeCalculator vRuntimeCalculator = new VRuntimeCalculator();

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
        this.processes.set(id, null);
        Integer initTime = this.timer.getTime();
        Integer time = timer.getTime();
        Integer currentTime = 0;
        while (time - initTime < quantum && cpuTime > 0) {
            try {
                Thread.sleep(timer.sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time = timer.getTime();
            cpuTime = cpuTime - 1;
            currentTime = currentTime + 1;
        }
        process.runtime = process.runtime + currentTime;

        vRuntimeCalculator.updateVRuntime(process, currentTime);

        if (cpuTime == 0) {
            if (process.hasAnotherIo()){
                new IOHandler(this.tree, process, this.timer);
            } else {
                // TODO: Hacer algo con el proceso finalizado
            }

        } else {
            process.remainingTime(cpuTime);
            tree.add(process.vruntime, process);
        }

        this.cores.set(id, false);

    }
}
