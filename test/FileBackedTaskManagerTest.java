import managers.FileBackedTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.TaskStatus;
import tasks.epic.Epic;
import tasks.subtask.Subtask;
import tasks.task.Task;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class FileBackedTaskManagerTest {

    File file = File.createTempFile("xxx", ".txt");
    FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(file);

    public FileBackedTaskManagerTest() throws IOException {
    }


    @Test
    public void loadEmptyFile() {
        assertTrue(manager.getListAllTasks().isEmpty());
        assertTrue(manager.getListAllEpics().isEmpty());
        assertTrue(manager.getListAllSubtasks().isEmpty());
    }

    @Test
    public void saveEmptyFile() {
        manager.save();
    }

    @Test
    public void checkTaskManagerAddAndGetTaskTrue() {
        Task task1 = new Task(TaskStatus.NEW, "1", "2");
        manager.addTask(task1);
        Assertions.assertTrue(task1.equals(manager.getTaskById(task1.getId())));
    }

    @Test
    public void checkTaskManagerAddAndGetEpicTrue() {
        Epic epic = new Epic("1", "2");
        manager.addEpic(epic);
        Assertions.assertEquals(epic, manager.getEpicById(epic.getId()));
    }

    @Test
    public void addSubtaskTest() {
        Epic epic = new Epic("1", "2");
        manager.addEpic(epic);
        Subtask subtask = new Subtask(TaskStatus.NEW, "2", "10", epic.getId());
        manager.addSubtask(subtask);
        Assertions.assertTrue(subtask.equals(manager.getSubtaskById(subtask.getId())));
    }
}
