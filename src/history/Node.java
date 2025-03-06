package history;

public class Node<T> {
    public T value;
    public Node<T> prev;
    public Node<T> next;

    public Node(T item) {
        this.value = item;
        prev = null;
        next = null;
    }

    public Node(T value, Node<T> next, Node<T> prev) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }
}