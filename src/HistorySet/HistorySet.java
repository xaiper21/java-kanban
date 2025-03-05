package HistorySet;

import Tasks.Task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistorySet<T extends Task> {
    private final Map<Integer, Node<T>> map;
    private final LinkedHistoryList<T> list;

    public HistorySet() {
        map = new HashMap<>();
        list = new LinkedHistoryList<>();
    }

    public List<T> getList() {
        return list.getTasks();
    }

    public void add(T item) {
        if (map.containsKey(item.getId())) {
            Node<T> node = map.get(item.getId());
            list.removeNode(node);
        }
        map.put(item.getId(), new Node<>(item));
        list.linkLast(item);
    }

    public void remove(int id) {
        if (map.containsKey(id)) {
            list.removeNode(map.get(id));
            map.remove(id);
        }
    }

    private class Node<T extends Task> {
        public T value;
        public Node<T> prev;
        public Node<T> next;

        private Node(T item) {
            this.value = item;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }

    private class LinkedHistoryList<T extends Task> {
        private Node<T> firstItem;
        private Node<T> lastItem;

        private void linkLast(T item) {
            Node<T> node = new Node<>(item);
            if (firstItem == null) {
                firstItem = node;
            } else {
                lastItem.next = node;
            }
            lastItem = node;
        }

        private List<T> getTasks() {
            List<T> list = new ArrayList<>();
            Node<T> node = firstItem;
            while (node != null) {
                list.add(node.value);
                node = node.next;
            }
            return list;
        }

        private void removeNode(Node<T> node) {
            Node<T> prevNode = null;
            Node<T> nextNode = null;
            if (node.prev != null) {
                prevNode.setNext(nextNode);
                prevNode = node.prev;
            } else {
                firstItem = firstItem.next;
            }
            if (node.next != null) {
                nextNode = node.next;
                nextNode.setPrev(prevNode);
                lastItem = lastItem.prev;
            }
        }

        public LinkedHistoryList() {
        }
    }
}

