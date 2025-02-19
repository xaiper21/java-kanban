package Tests;

import Managers.Managers;
import Managers.TaskManager;
import Tasks.Epic.Epic;
import Tasks.Subtask.Subtask;
import Tasks.Task.Task;
import Tasks.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManagersTest {
    Managers managers = new Managers();
    TaskManager inMemoryTaskManager = managers.getDefault();

    @Test
    public void checkTaskManagerAddAndGetTaskTrue() {
        Task task1 = new Task(TaskStatus.NEW, "1", "2");
        inMemoryTaskManager.addTask(task1);
        assertTrue(task1.equals(inMemoryTaskManager.getTaskById(task1.getId())));
    }

    @Test
    public void checkTaskManagerAddAndGetEpicTrue() {
        Epic epic = new Epic("1", "2");
        inMemoryTaskManager.addEpic(epic);
        assertEquals(epic, inMemoryTaskManager.getEpicById(epic.getId()));
    }

    @Test
    public void addSubtaskTest() {
        Epic epic = new Epic("1", "2");
        inMemoryTaskManager.addEpic(epic);
        Subtask subtask = new Subtask(TaskStatus.NEW, "2", "10", epic.getId());
        inMemoryTaskManager.addSubtask(subtask);
        assertTrue(subtask.equals(inMemoryTaskManager.getSubtaskById(subtask.getId())));
    }

    @Test
    public void checkImmutabilityTaskAndTaskHistory() {
        Task task = new Task(TaskStatus.NEW, "name", "description");
        inMemoryTaskManager.addTask(task);
        assertEquals(TaskStatus.NEW, inMemoryTaskManager.getTaskById(1).getStatus());
        assertEquals("name", inMemoryTaskManager.getTaskById(1).getName());
        assertEquals("description", inMemoryTaskManager.getTaskById(1).getDescription());

        task = new Task(TaskStatus.NEW, "name2", "description");
        inMemoryTaskManager.updateTask(task);
        assertEquals("name", inMemoryTaskManager.getHistory().get(0).getName());
    }

}