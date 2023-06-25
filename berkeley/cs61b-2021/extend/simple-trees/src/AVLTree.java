package bstmap.simpletrees;

public class AVLTree{
    private Node root;
    private int size;

    public AVLTree() {
        this.root = null;
        this.size = 0;
    }

    public void add(int value) {
        root = put(root, value);
    }

    private Node put(Node node, int value) {
        if (node == null) {
            return createNewNode(value);
        } else if (value > node.value) {
            node.right = put(node.right, value);
        } else {
            node.left = put(node.left, value);
        }
        calculateBalanceFactor(node);
        return balanceTree(node);
    }

    // Class utilities
    private Node createNewNode(int value) {
        Node newNode = new Node(value);
        this.size++;
        return newNode;
    }

    public void delete(int value) {
        root = getNewStructure(root, value);
    }

    private Node getNewStructure(Node node, int key) {
        if (node == null) {
            return null;
        } else if (key > node.value) {
            // Search in right side
            node.right = getNewStructure(node.right, key);
        } else if (key < node.value) {
            // Search in left side
            node.left = getNewStructure(node.left, key);
        } else {
            // We found the target node, so we have 3 cases
            if (node.left == null) {
                /*
                 * Return child node of target node:
                 *   - in case one is null in right side
                 *   - in case two, it has a node will be relinked
                 * */
                size--;
                return node.right;
            } else if (node.right == null) {
                /*
                 * Return child node of target node:
                 *   - in case one is null in left side
                 *   - in case two, it has a node will be relinked
                 * */
                size--;
                return node.left;
            } else {
                Node maxOfTarget = getMaxNode(node.left);
                node.value = maxOfTarget.value;
                node.left = getNewStructure(maxOfTarget, maxOfTarget.value);
            }
        }

        calculateBalanceFactor(node);
        return balanceTree(node);
    }

    private int getNodeHeight(Node node) {
        if (node == null) {
            return -1;
        }

        int leftNodeHeight = getNodeHeight(node.left);
        int rightNodeHeight = getNodeHeight(node.right);

        return 1 + Math.max(leftNodeHeight, rightNodeHeight);
    }

    private void calculateBalanceFactor(Node node) {
        if (node != null) {
            calculateBalanceFactor(node.left);
            node.balanceFactor = (getNodeHeight(node.left) - getNodeHeight(node.right));
            calculateBalanceFactor(node.right);
        }
    }

    private Node doRRRotation(Node node) {
        // RR case
        Node oldRoot = node;
        Node newRoot = oldRoot.right;

        node = newRoot;
        oldRoot.right = newRoot.left;
        node.left = oldRoot;

        return node;
    }

    private Node doLLRotation(Node node) {
        // LL
        Node oldRoot = node;
        Node newRoot = oldRoot.left;

        node = newRoot;
        oldRoot.left = newRoot.right;
        node.right = oldRoot;

        return node;
    }

    private Node balanceTree(Node node) {

        if (node.balanceFactor == -2 && node.right.balanceFactor == -1) {

            // RR Rotation
            return doRRRotation(node);
        }

        if (node.balanceFactor == 2 && node.left.balanceFactor == 1) {

            // LL Rotation
            return doLLRotation(node);
        }

        if (node.balanceFactor == -2 && node.right.balanceFactor == 1) {

            // RL Rotation
            Node lastNode = node.right;
            Node newRoot = lastNode.left;

            node.right = newRoot;
            lastNode.left = newRoot.right;
            newRoot.right = lastNode;

            return doRRRotation(node);
        }

        if (node.balanceFactor == 2 && node.left.balanceFactor == -1) {

            // LR Rotation
            Node lastNode = node.left;
            Node newRoot = lastNode.right;

            node.left = newRoot;
            lastNode.right = newRoot.left;
            newRoot.left = lastNode;

            return doLLRotation(node);
        }

        return node;
    }

    private Node getMaxNode(Node node) {
        if (node == null) {
            return null;
        } else if (node.right == null) {
            return node;
        } else {
            return getMaxNode(node.right);
        }
    }

    private class Node {
        private int value;
        private Node left;
        private Node right;
        private int balanceFactor;

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.balanceFactor = 0;
        }
    }
}
