public class Main {
    public static void main(String[] args){
        Integer coreQty = Integer.parseInt(args[0]);
        Integer processQty = Integer.parseInt(args[1]);
        Integer ioRange = Integer.parseInt(args[2]);
        Integer quantum =  Integer.parseInt(args[3]);
        Integer sleepTime = Integer.parseInt(args[4]);
        Boolean loop = false;

        if (processQty == 0)
            loop = true;

        Timer timer = new Timer(sleepTime);

        RBTree<Integer> tree = new RBTree<>();

        CPU cpu = new CPU(quantum, coreQty, tree, timer);

        ProcessGenerator processGenerator = new ProcessGenerator(tree, timer, ioRange, loop, processQty);
        Thread thread1 = new Thread(processGenerator);
        thread1.start();

        Dispatcher dispatcher = new Dispatcher(cpu, tree);
        Thread threadFabiola = new Thread(dispatcher);
        threadFabiola.start();

        TimerThread timerThread = new TimerThread(timer);
        Thread thread2 = new Thread(timerThread);
        thread2.start();
    }
}
