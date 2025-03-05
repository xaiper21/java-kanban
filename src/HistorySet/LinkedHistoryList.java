//package HistorySet;
//
//import Tasks.Task.Task;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class LinkedHistoryList<T extends Task> {
//    private Node<T> firstItem;
//    private Node<T> lastItem;
//
//    public void linkLast(T item) {
//        Node<T> node = new Node<>(item);
//        if (firstItem == null) {
//            firstItem = node;
//        } else {
//            node = lastItem.getNext();
//        }
//        lastItem = node;
//    }
//
//    public List<T> getTasks() {
//        List<T> list = new ArrayList<>();
//        Node<T> node = firstItem;
//        while (node != null) {
//            list.add(node.getValue());
//            node = node.getNext();
//        }
//        return list;
//    }
//
//    public void removeNode(Node<T> node) {
//        Node<T> prevNode = null;
//        Node<T> nextNode = null;
//        if (node.getPrev() != null) {
//            prevNode.setNext(nextNode);
//            prevNode = node.getPrev();
//        } else {
//            firstItem = firstItem.getNext();
//        }
//        if (node.getNext() != null) {
//            nextNode = node.getNext();
//            nextNode.setPrev(prevNode);
//            lastItem = lastItem.getPrev();
//        }
//    }
//
//    public LinkedHistoryList() {
//    }
//}
