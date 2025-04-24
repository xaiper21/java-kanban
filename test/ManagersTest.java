package test;

import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.TaskStatus;
import tasks.epic.Epic;
import tasks.subtask.Subtask;
import tasks.task.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManagersTest {
    Managers managers = new Managers();
    TaskManager inMemoryTaskManager = managers.getDefault();

    @Test
    public void checkTaskManagerAddAndGetTaskTrue() {
        Task task1 = new Task(TaskStatus.NEW, "1", "2");
        inMemoryTaskManager.addTask(task1);
        assertTrue(task1.equals(inMemoryTaskManager.getTaskById(task1.getId()).get()));
    }

    @Test
    public void checkTaskManagerAddAndGetEpicTrue() {
        Epic epic = new Epic("1", "2");
        inMemoryTaskManager.addEpic(epic);
        assertEquals(epic, inMemoryTaskManager.getEpicById(epic.getId()).get());
    }

    @Test
    public void addSubtaskTest() {
        Epic epic = new Epic("1", "2");
        inMemoryTaskManager.addEpic(epic);
        Subtask subtask = new Subtask(TaskStatus.NEW, "2", "10", epic.getId());
        inMemoryTaskManager.addSubtask(subtask);
        assertTrue(subtask.equals(inMemoryTaskManager.getSubtaskById(subtask.getId()).get()));
    }

    @Test
    public void checkImmutabilityTaskAndTaskHistory() {
        Task task = new Task(TaskStatus.NEW, "name", "description");
        inMemoryTaskManager.addTask(task);
        assertEquals(TaskStatus.NEW, inMemoryTaskManager.getTaskById(1).get().getStatus());
        assertEquals("name", inMemoryTaskManager.getTaskById(1).get().getName());
        assertEquals("description", inMemoryTaskManager.getTaskById(1).get().getDescription());

        task = new Task(TaskStatus.NEW, "name2", "description");
        inMemoryTaskManager.updateTask(task);
        assertEquals("name", inMemoryTaskManager.getHistory().get(0).getName());
    }
}