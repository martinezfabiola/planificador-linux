public class RBNode<T extends Comparable<T>> {
    private static final boolean RED   = false;
    private static final boolean BLACK = true;

    boolean color;
    T key;
    int pid;
    int init_time;
    RBNode<T> left;
    RBNode<T> right;
    RBNode<T> parent;


    public RBNode(T key, int pid, int time, boolean color, RBNode parent, RBNode left, RBNode right) {
        this.key = key;
        this.pid = pid;
        this.init_time = time;
        this.color = color;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }


    public RBNode(T key, int pid, int time) {
        this(key, pid, time, RED, null, null, null);
    }
}