import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProcessGenerator implements Runnable {
    private RBTree<Integer> tree;
    private Timer timer;
    private boolean loop;
    private Integer qty;
    private int processInterruptionRange;

    ProcessGenerator(RBTree<Integer> tree, Timer timer, int processInterruptionRange, boolean loop, Integer qty) {
        this.tree = tree;
        this.timer = timer;
        this.loop = loop;
        this.qty = qty;
        this.processInterruptionRange = processInterruptionRange;
    }

    ProcessGenerator(RBTree<Integer> tree, Timer timer) {
        this(tree, timer, 100, true, 100);
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
                cpu.add(random.nextInt(250) + 1);
            }

            for (int k = 0; k < cantidadIO; k++) {
                io.add(random.nextInt(250) + 1);
            }

            Integer priority = random.nextInt(40) - 20;

            Process process = new Process(id, priority, tiempo_llegada, cpu, io);

            System.out.println("Tiempo" + timer.getTime());

            while (timer.getTime() < tiempo_llegada) {
                try {
                    Thread.sleep(this.timer.sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            tree.add(0, process);

            System.out.println("pid" + id);

            tiempo_llegada = tiempo_llegada + random.nextInt(50) + 1;
            id = id + 1;
        }

    }
}
