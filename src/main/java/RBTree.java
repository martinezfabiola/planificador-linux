public class RBTree<T extends Comparable<T>> {
    private static final boolean RED   = false;
    private static final boolean BLACK = true;

    private RBNode<T> root;

    public RBTree() {
        this.root = null;
    }


    private boolean isRed(RBNode<T> node) {
        return ((node != null)) && node.color == RED;
    }


    private boolean isBlack(RBNode<T> node) {
        return !isRed(node);
    }


    private void setBlack(RBNode<T> node) {
        if (node != null)
            node.color = BLACK;
    }


    private void setRed(RBNode<T> node) {
        if (node != null)
            node.color = RED;
    }


    private boolean colorOf(RBNode<T> node) {
        return (node!=null) ? node.color : BLACK;
    }


    private void setColor(RBNode<T> node, boolean color) {
        if (node != null)
            node.color = color;
    }


    private RBNode<T> parentOf(RBNode<T> node) {
        return (node != null) ? node.parent : null;
    }


    private void setParent(RBNode<T> node, RBNode<T> parent) {
        if (node != null)
            node.parent = parent;
    }


    public void add(T key, int pid, int time) {
        RBNode<T> newNode = new RBNode<>(key, pid, time, RED, null, null, null);
        insert(newNode);
    }


    private RBNode<T> minimum() {
        if (this.root == null)
            return null;

        RBNode<T> tree = this.root;
        while(tree.left != null)
            tree = tree.left;
        return tree;
    }


    public RBNode<T> getMinimum() {
        RBNode<T> minimum = minimum();
        if (minimum != null)
            remove(minimum);
        return minimum;
    }


    private void insert_helper(RBNode<T> node) {
        // Funcion que agarra el nodo a insertar y lo ubica en el arbol

        int cmp;

        RBNode<T> y = null;
        RBNode<T> x = this.root;

        // Empezando desde la raiz, recorremos el arbol en busca de la posicion adecuada del nodo inrgesado
        while (x != null) {
            y = x;
            cmp = node.key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }

        // Una vez encontrada la posicion, le asignamos el padre al nodo a ingresar
        node.parent = y;

        // Si el padre es null, significa que el nodo a ingresar quedaria como la nueva raiz
        if (y == null)
            this.root = node;
        // En caso contrario verificamos si es el hijo derecho o izquierdo del mismo
        else {
            cmp = node.key.compareTo(y.key);
            if (cmp < 0)
                y.left = node;
            else
                y.right = node;
        }
    }


    public void insert(RBNode<T> newNode) {
        // Funcion que inserta un nodo al arbol y lo balancea

        insert_helper(newNode);

        RBNode<T> parent, gparent;

        // Nos vamos moviendo hacia la raiz
        while (((parent = parentOf(newNode))!=null) && isRed(parent)) {
            gparent = parentOf(parent);

            // Obtenemos el hermano del nodo padre
            if (parent == gparent.left) {
                // Modificamos los colores del padre, su hermano y el padre de ambos
                RBNode<T> uncle = gparent.right;
                if (isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    newNode = gparent;
                    continue;
                }

                // Si el nodo de entrada es el hijo de la derecha tenemos que realizar una rotacion hacia la izquierda
                if (parent.right == newNode) {
                    RBNode<T> tmp;
                    leftRotate(parent);
                    tmp = parent;
                    parent = newNode;
                    newNode = tmp;
                }

                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else {
                // Se repiten las instrucciones del caso anterior modificando el hecho de que esta vez el padre es
                // hijo derecho
                RBNode<T> uncle = gparent.left;
                if (isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    newNode = gparent;
                    continue;
                }

                if (parent.left == newNode) {
                    RBNode<T> tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = newNode;
                    newNode = tmp;
                }

                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }

        setBlack(this.root);


    }


    /*      Siendo el nodo eje 'x' la entrada
     *
     *      px                    px
     *     /                     /
     *    x                     y
     *   /  \                  / \
     *  lx   y                x  ry
     *     /   \             /  \
     *    ly   ry           lx  ly
     *
     */
    private void leftRotate(RBNode<T> node) {
        // Funcion que rota los nodos del arbol desde un nodo eje hacia la izquierda como se muestra
        // en la figura anterior

        RBNode<T> y = node.right;

        // Asignamos al hijo de la izquierda de 'y' como hijo de la derecha del eje
        node.right = y.left;
        if (y.left != null)
            y.left.parent = node;

        // Le asignamos el padre del eje como padre a 'y' (verificando el caso cuando el nodo eje es raiz)
        y.parent = node.parent;

        if (node.parent == null) {
            this.root = y;
        } else {
            if (node.parent.left == node)
                node.parent.left = y;
            else
                node.parent.right = y;
        }

        // Finalmente le asignamos a 'y' como hijo izquierdo al eje
        y.left = node;
        node.parent = y;
    }


    /*      Siendo el nodo eje 'y' la entrada
     *
     *        py                    py
     *       /                     /
     *      y                     x
     *     / \                   / \
     *    x  ry                 lx  y
     *   / \                       / \
     *  lx  rx                    rx  ry
     *
     */
    private void rightRotate(RBNode<T> node) {
        // Funcion que rota los nodos del arbol desde un eje nodo hacia la derecha como se muestra
        // en la figura anterior

        RBNode<T> x = node.left;

        // Asignamos al hijo de la derecha de x como el hijo de la izquierda del eje
        node.left = x.right;
        if (x.right != null)
            x.right.parent = node;

        // Le asignamos el padre del eje como padre a x (verificando el caso cuando el nodo eje es raiz)
        x.parent = node.parent;

        if (node.parent == null) {
            this.root = x;
        } else {
            if (node == node.parent.right)
                node.parent.right = x;
            else
                node.parent.left = x;
        }

        // Finalmente le asignamor x como hijo derecho al eje
        x.right = node;

        node.parent = x;
    }

    private void remove(RBNode<T> node) {
        RBNode<T> child, parent;
        boolean color;

        if ( (node.left!=null) && (node.right!=null) ) {
            RBNode<T> replace = node;

            replace = replace.right;
            while (replace.left != null)
                replace = replace.left;

            if (parentOf(node)!=null) {
                if (node.parent.left == node)
                    node.parent.left = replace;
                else
                    node.parent.right = replace;
            } else {
                this.root = replace;
            }

            child = replace.right;
            parent = parentOf(replace);

            color = replace.color;

            if (parent == node) {
                parent = replace;
            } else {
                if (child!=null)
                    child.parent = parent;
                parent.left = child;

                replace.right = node.right;
                setParent(node.right, replace);
            }

            replace.parent = node.parent;
            replace.color = node.color;
            replace.left = node.left;
            node.left.parent = replace;

            if (color == BLACK)
                removeFixUp(child, parent);

            return ;
        }

        if (node.left !=null) {
            child = node.left;
        } else {
            child = node.right;
        }

        parent = node.parent;
        color = node.color;

        if (child!=null)
            child.parent = parent;

        if (parent!=null) {
            if (parent.left == node)
                parent.left = child;
            else
                parent.right = child;
        } else {
            this.root = child;
        }

        if (color == BLACK)
            removeFixUp(child, parent);
    }

    private void removeFixUp(RBNode<T> node, RBNode<T> parent) {
        RBNode<T> other;

        while ((node==null || isBlack(node)) && (node != this.root)) {
            if (parent.left == node) {
                other = parent.right;
                if (isRed(other)) {
                    setBlack(other);
                    setRed(parent);
                    leftRotate(parent);
                    other = parent.right;
                }

                if ((other.left==null || isBlack(other.left)) &&
                        (other.right==null || isBlack(other.right))) {
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.right==null || isBlack(other.right)) {
                        setBlack(other.left);
                        setRed(other);
                        rightRotate(other);
                        other = parent.right;
                    }
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.right);
                    leftRotate(parent);
                    node = this.root;
                    break;
                }
            } else {

                other = parent.left;
                if (isRed(other)) {
                    setBlack(other);
                    setRed(parent);
                    rightRotate(parent);
                    other = parent.left;
                }

                if ((other.left==null || isBlack(other.left)) &&
                        (other.right==null || isBlack(other.right))) {
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.left==null || isBlack(other.left)) {
                        setBlack(other.right);
                        setRed(other);
                        leftRotate(other);
                        other = parent.left;
                    }

                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.left);
                    rightRotate(parent);
                    node = this.root;
                    break;
                }
            }
        }

        if (node!=null)
            setBlack(node);
    }

}