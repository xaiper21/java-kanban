package history;

import tasks.task.Task;

import java.util.ArrayList;
import java.util.List;

public class LinkedHistoryList<T extends Task> {
    public Node<T> firstItem;
    public Node<T> lastItem;

    public LinkedHistoryList() {
        lastItem = null;
        firstItem = null;
    }

    public void linkLast(Node<T> newNode) {
        if (lastItem == null) {
            firstItem = newNode;
        } else {
            newNode.prev = lastItem;
            lastItem.next = newNode;
        }
        lastItem = newNode;
    }

    public List<T> getTasks() {
        List<T> list = new ArrayList<>();
        Node<T> node = firstItem;
        while (node != null) {
            list.add(node.value);
            node = node.next;
        }
        return list;
    }

    public void removeNode(Node<T> node) {
        final Node<T> prevNode = node.prev;
        final Node<T> nextNode = node.next;

        if (prevNode == null) {
            firstItem = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            lastItem = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
    }
}
