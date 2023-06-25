package bstmap.simpletrees;

import java.util.LinkedList;
import java.util.Queue;

public class BSTree implements ITree {
    protected Node root;
    protected int size;

    public BSTree() {
        this.root = null;
        this.size = 0;
    }

    public void add(int value) {
        root = put(root, value);
    }

    // In BST the value/key is unique, so we can use it for searching
    public void delete(int value) {
        root = getNewStructure(root, value);
    }

    // Left, Root, Right
    public void displayInorder() {
        System.out.println("======== In Order ========");
        traverseInorderRecursively(root);
        System.out.println();
    }

    // Root, Left, Right
    public void displayPreorder() {
        System.out.println("======== Pre Order ========");
        traversePreorderRecursively(root);
        System.out.println();
    }

    // Left, Right, Root
    public void displayPostorder() {
        System.out.println("======== Post Order ========");
        traversePostorderRecursively(root);
        System.out.println();
    }

    public void displayInLevelOrder() {
        if (root == null) {
            return;
        }
        System.out.println("======== DFS ========");
        Queue<Node> levels = new LinkedList<>();
        levels.add(root);

        while (!levels.isEmpty()) {
            Node current = levels.remove();

            System.out.print(current.value + " ");

            Node leftSide = current.left;
            Node rightSide = current.right;

            if (leftSide != null) {
                levels.add(leftSide);
            }

            if (rightSide != null) {
                levels.add(rightSide);
            }
        }
    }

    public int getHeight() {
        if (root == null) {
            return -1;
        }
        return getNodeHeight(root);
    }

    // Class utilities
    protected Node put(Node node, int value) {
        if (node == null) {
            return createNewNode(value);
        } else if (value > node.value) {
            node.right = put(node.right, value);
        } else {
            node.left = put(node.left, value);
        }
        return node;
    }

    protected Node getNewStructure(Node node, int key) {
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
        return node;
    }

    protected Node createNewNode(int value) {
        Node newNode = new Node(value);
        size++;
        return newNode;
    }

    protected Node getMaxNode(Node node) {
        if (node == null) {
            return null;
        } else if (node.right == null) {
            return node;
        } else {
            return getMaxNode(node.right);
        }
    }

    protected Node getMinNode(Node node) {
        if (node == null) {
            return null;
        } else if (node.left == null) {
            return node;
        } else {
            return getMinNode(node.left);
        }
    }

    // Get height of any tree not BST only
    protected int getNodeHeight(Node node) {
        if (node == null) {
            return -1;
        }

        int leftNodeHeight = getNodeHeight(node.left);
        int rightNodeHeight = getNodeHeight(node.right);

        return 1 + Math.max(leftNodeHeight, rightNodeHeight);
    }

    protected void traversePostorderRecursively(Node node) {
        if (node != null) {
            traversePostorderRecursively(node.left);
            traversePostorderRecursively(node.right);
            System.out.print(node.value + " ");
        }
    }

    protected void traversePreorderRecursively(Node node) {
        if (node != null) {
            System.out.print(node.value + " ");
            traversePreorderRecursively(node.left);
            traversePreorderRecursively(node.right);
        }
    }

    protected void traverseInorderRecursively(Node node) {
        if (node != null) {
            traverseInorderRecursively(node.left);
            System.out.print(node.value + " ");
            traverseInorderRecursively(node.right);
        }
    }

    protected class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

}
