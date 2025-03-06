package test;

import history.LinkedHistoryList;
import history.Node;
import tasks.task.Task;
import tasks.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkedHistoryListTest {

    LinkedHistoryList<Task> list = new LinkedHistoryList<>();
    Task t = new Task(TaskStatus.NEW, "1", "1");
    Task t2 = new Task(TaskStatus.NEW, "2", "2");
    Task t3 = new Task(TaskStatus.NEW, "3", "3");
    Node<Task> task = new Node<>(t);
    Node<Task> task2 = new Node<>(t2);
    Node<Task> task3 = new Node<>(t3);
    Node<Task> copyTask = new Node<>(t);
    Node<Task> copyTask2 = new Node<>(t2);
    Node<Task> copyTask3 = new Node<>(t3);

    @BeforeEach
    public void removeList() {
        list = new LinkedHistoryList<>();
        task = new Node<>(t);
        task2 = new Node<>(t2);
        task3 = new Node<>(t3);
    }

    @Test
    public void testValueLastAndFirstItem() {
        list.linkLast(task);
        assertEquals(list.lastItem, list.firstItem);
    }

    @Test
    public void testValueLastAndFirstItem2() {
        list.linkLast(task);
        list.linkLast(task2);
        assertEquals(list.lastItem, list.firstItem.next);
        assertEquals(list.firstItem, list.lastItem.prev);
        assertEquals(List.of(task.value, task2.value), list.getTasks());
    }

    @Test
    public void testConnectItems() {
        list.linkLast(task);
        list.linkLast(task2);
        list.linkLast(task3);
        assertEquals(list.firstItem.next, list.lastItem.prev);
    }

    @Test
    public void linkLastTestFirstConnect() {
        list.linkLast(task);
        List<Task> tasks = list.getTasks();
        assertEquals(tasks.get(0), task.value);

    }

    @Test
    public void linkLastTestConnectTwo() {
        list.linkLast(task);
        list.linkLast(task2);
        List<Task> tasks = list.getTasks();
        assertEquals(tasks.get(0), task.value);
        assertEquals(tasks.get(1), task2.value);
    }

    @Test
    public void linkLastTestConnectReplace() {
        list.linkLast(task);
        list.linkLast(task2);
        list.linkLast(copyTask);
        List<Task> tasks = list.getTasks();
        assertEquals(tasks.get(1), task.value);
        assertEquals(tasks.get(0), task2.value);
        assertEquals(tasks.get(2), task.value);
    }

    @Test
    public void getTasksTest() {
        list.linkLast(task);
        list.linkLast(task2);
        list.linkLast(copyTask);
        assertEquals(List.of(task.value, task2.value, task.value), list.getTasks());
        assertEquals(list.getTasks().size(), 3);
    }

    @Test
    public void removeNodeTestInCenter() {
        list.linkLast(task);
        list.linkLast(task2);
        list.linkLast(task3);
        list.removeNode(list.firstItem.next);
        assertEquals(List.of(task.value, task3.value), list.getTasks());
        assertEquals(list.getTasks().size(), 2);
    }

    @Test
    public void removeNodeTestFirstItem() {
        list.linkLast(task);
        list.linkLast(task2);
        list.linkLast(task3);
        list.removeNode(list.firstItem);
        assertEquals(list.getTasks(), List.of(task2.value, task3.value));
        assertEquals(list.getTasks().size(), 2);
    }

    @Test
    public void removeNodeTestLastItem() {
        list.linkLast(task);
        list.linkLast(task2);
        list.linkLast(task3);
        list.removeNode(list.lastItem);
        assertEquals(List.of(task.value, task2.value), list.getTasks());
        assertEquals(list.getTasks().size(), 2);
    }

    @Test
    public void removeNodeTestOneItem() {
        list.linkLast(task);
        list.removeNode(list.lastItem);
        assertEquals(List.of(), list.getTasks());
        assertEquals(list.getTasks().size(), 0);
    }
}