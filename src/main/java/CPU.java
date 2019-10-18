import java.util.ArrayList;
import java.util.List;

public class CPU {
    Integer coresQty;
    List<Core> cores = new ArrayList<>();
    CoresWorker coresWorker;
    private RBTree<Integer> tree;
    private Timer timer;


    CPU(Integer quantum, Integer coresQty, RBTree<Integer> tree, Timer timer) {
        this.coresQty = coresQty;
        this.tree = tree;
        this.timer = timer;
        this.coresWorker = new CoresWorker(tree, timer, coresQty, quantum);
        for (Integer i = 0; i < coresQty; i++) {
            Core newCore = new Core(i, this.coresWorker);
            Thread newThread = new Thread(newCore);
            newThread.start();
            cores.add(newCore);
        }
    }

    public synchronized void dispatchProcess(Process process) {
        boolean dispatched = false;
        for (int i = 0; i < this.coresQty; i++) {
            if (!this.coresWorker.cores.get(i)) {
                this.coresWorker.dispatchProcess(process, i);
                dispatched = true;
                break;
            }
        }
        if (!dispatched)
            tree.add(process.vruntime, process);
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
        this.cores.set(id, true);
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
        process.setState("Running");
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
            process.runtime = process.runtime + 1;
        }

        vRuntimeCalculator.updateVRuntime(process, currentTime);

        System.out.println("Corrio proceso " + process.getPid() + " por tiempo " + currentTime );

       if (cpuTime == 0) {
            if (process.hasAnotherIo()){
                process.setState("Waiting");
                IOHandler ioHandler = new IOHandler(this.tree, process, this.timer);
                Thread threadDonatoEsBolsa = new Thread(ioHandler);
                threadDonatoEsBolsa.start();
            } else {
               process.setState("Finished");
            }
        } else {
            process.remainingTime(cpuTime);
            process.setState("Ready");
            tree.add(process.vruntime, process);
        }

       this.cores.set(id, false);
    }
}
