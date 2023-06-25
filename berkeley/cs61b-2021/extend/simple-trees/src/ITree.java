package bstmap.simpletrees;

public interface ITree {
    public void add(int key);
    public void delete(int key);
    public void displayInorder();
    public void displayPreorder();
    public void displayPostorder();
    public void displayInLevelOrder();
    public int getHeight();
}
