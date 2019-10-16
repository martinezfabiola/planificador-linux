import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProcessGenerator implements Runnable {
    private RBTree<Integer> tree;
    private boolean loop;
    private Integer qty;
    private int timeInterval;
    private int processInterruptionRange;

    ProcessGenerator(RBTree<Integer> tree, int timeInterval, int processInterruptionRange, boolean loop, Integer qty) {
        this.tree = tree;
        this.loop = loop;
        this.qty = qty;
        this.timeInterval = timeInterval;
        this.processInterruptionRange = processInterruptionRange;
    }

    ProcessGenerator(RBTree<Integer> tree) {
        this(tree,100, 50, true, 100);
    }

    public void run()
    {
        int id = 0; // Contador para id's de procesos
        Random random = new Random();

        int tiempo_llegada = 0;

        while (id < this.qty || this.loop) {
            List<Integer> cpu = new ArrayList<>();
            List<Integer> io = new ArrayList<>();
            int cantidadCPU = random.nextInt(this.processInterruptionRange) + 1; // Cantidad de CPU
            int cantidadIO = cantidadCPU - 1; // Cantidad de IO

            for (int j = 0; j < cantidadCPU; j++) {
                cpu.add(random.nextInt(100) + 1);
            }

            for (int k = 0; k < cantidadIO; k++) {
                io.add(random.nextInt(100) + 1);
            }

            Process process = new Process(id, random.nextInt(10), tiempo_llegada, cpu, io);

            // TODO: Cual va a ser el tiempo de los procesos
            tree.add(0, process);

            // TODO: Hacer algo con los procesos
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            id = id + 1;
            tiempo_llegada = tiempo_llegada + random.nextInt(this.timeInterval) + 1;
        }

    }
}
