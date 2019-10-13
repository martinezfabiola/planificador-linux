public class RBNode<T extends Comparable<T>> {
    private static final boolean RED   = false;
    private static final boolean BLACK = true;

    boolean color;
    T key;  // vruntime del proceso, tiempo que ha sido ejecutado en el cpu
    Integer init_time;
    Process process;
    RBNode<T> left;
    RBNode<T> right;
    RBNode<T> parent;


    public RBNode(T key, Process process, Integer time, boolean color, RBNode<T> parent, RBNode<T> left, RBNode<T> right) {
        this.key = key;
        this.init_time = time;
        this.process = process;
        this.color = color;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }


    public RBNode(T key, Process process, Integer time) {
        this(key, process, time, RED, null, null, null);
    }
}