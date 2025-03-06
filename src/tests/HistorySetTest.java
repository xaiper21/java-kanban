package tests;


import history.HistorySet;
import managers.InMemoryTaskManager;
import tasks.task.Task;
import tasks.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistorySetTest {

    HistorySet<Task> set = new HistorySet<>();
    Task task = new Task(TaskStatus.NEW, "1", "1");
    Task task2 = new Task(TaskStatus.NEW, "2", "2");
    Task task3 = new Task(TaskStatus.NEW, "3", "3");

    InMemoryTaskManager manager;

    @BeforeEach
    public void updateSet() {
        manager = new InMemoryTaskManager();
        set = new HistorySet<>();
        manager.addTask(task);
        manager.addTask(task2);
        manager.addTask(task3);
    }

    @Test
    public void getListTest() {
        set.add(task);
        set.add(task2);
        set.add(task3);
        assertEquals(List.of(task, task2, task3), set.getList());
        assertEquals(3, set.getList().size());
    }

    @Test
    public void addTestFirstItem() {
        set.add(task);
        assertEquals(List.of(task), set.getList());
        assertEquals(1, set.getList().size());
    }

    @Test
    public void addTestAddTwoItem() {
        set.add(task);
        set.add(task2);
        assertEquals(List.of(task, task2), set.getList());
        assertEquals(2, set.getList().size());
    }

    @Test
    public void addTestAddThreeItem() {
        set.add(task);
        set.add(task2);
        set.add(task3);
        assertEquals(List.of(task, task2, task3), set.getList());
        assertEquals(3, set.getList().size());
    }

    @Test
    public void addTestAddEqualsItemInStart() {
        set.add(task);
        assertEquals(List.of(task), set.getList());
        assertEquals(1, set.getList().size());

        set.add(task);
        assertEquals(List.of(task), set.getList());
        assertEquals(1, set.getList().size());
    }

    @Test
    public void addTestAddEqualsItemInCenter() {
        set.add(task);
        set.add(task2);
        set.add(task3);
        set.add(task2);
        assertEquals(List.of(task, task3, task2), set.getList());
        assertEquals(3, set.getList().size());
    }

    @Test
    public void addTestAddEqualsItemInEnd() {
        set.add(task);
        set.add(task2);
        assertEquals(List.of(task, task2), set.getList());
        set.add(task);
        assertEquals(List.of(task2, task), set.getList());
        assertEquals(2, set.getList().size());
    }

    @Test
    public void removeTestStart() {
        set.add(task);
        set.add(task2);
        set.add(task3);
        set.remove(task.getId());
        assertEquals(List.of(task2, task3), set.getList());
        assertEquals(2, set.getList().size());
    }

    @Test
    public void removeTestCenter() {
        set.add(task);
        set.add(task2);
        set.add(task3);
        set.remove(task.getId());
        assertEquals(List.of(task2, task3), set.getList());
        assertEquals(2, set.getList().size());
    }

    @Test
    public void removeTestEnd() {
        set.add(task);
        set.add(task2);
        set.add(task3);
        set.remove(task3.getId());
        assertEquals(List.of(task, task2), set.getList());
        assertEquals(2, set.getList().size());
    }
}