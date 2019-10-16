public class RBNode<T extends Comparable<T>> {
    private static final boolean RED   = false;
    private static final boolean BLACK = true;

    boolean color;
    T key;  // vruntime del proceso, tiempo que ha sido ejecutado en el cpu
    Process process;
    RBNode<T> left;
    RBNode<T> right;
    RBNode<T> parent;


    public RBNode(T key, Process process, boolean color, RBNode<T> parent, RBNode<T> left, RBNode<T> right) {
        this.key = key;
        this.process = process;
        this.color = color;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }


    public RBNode(T key, Process process) {
        this(key, process, RED, null, null, null);
    }
}