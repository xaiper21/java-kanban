import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.TaskStatus;
import tasks.epic.Epic;
import tasks.subtask.Subtask;
import tasks.task.Task;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;
    protected Task task1;
    protected Task task2;
    protected Task task3;
    protected Epic epic1;
    protected Epic epic2;
    protected Epic epic3;


    protected abstract T createTaskManager();

    private Subtask generateTestSubtaskByEpic(Epic epic) {
        taskManager.addEpic(epic);
        return new Subtask(TaskStatus.NEW, "Name", "description", epic.getId());
    }

    @BeforeEach
    void setTaskManager() {
        taskManager = createTaskManager();
        task1 = new Task(TaskStatus.NEW, "1 task", "1 task");
        task2 = new Task(TaskStatus.IN_PROGRESS, "2 task", "2 task");
        task3 = new Task(TaskStatus.DONE, "3 task", "3 task");
        epic1 = new Epic("1 epic", "description");
        epic2 = new Epic("2 epic", "description");
        epic3 = new Epic("3 epic", "description");
    }

    @Test
    void addTaskTest() {
        taskManager.addTask(task1);
        assertEquals(task1, taskManager.getTaskById(task1.getId()).get());
    }

    @Test
    void removeTaskTest() {
        taskManager.removeTaskById(task1.getId());
        assertTrue(taskManager.getListAllTasks().isEmpty());
    }

    @Test
    void addEpicTest() {
        taskManager.addEpic(epic1);
        assertEquals(epic1, taskManager.getEpicById(epic1.getId()).get());
    }

    @Test
    void addSubtaskTest() {
        Subtask subtask = generateTestSubtaskByEpic(epic1);
        taskManager.addSubtask(subtask);
        assertEquals(subtask, taskManager.getSubtaskById(subtask.getId()).get());
        assertEquals(1, taskManager.getListAllSubtasks().size());
    }

    @Test
    void removeSubtaskTest() {
        Subtask subtask = generateTestSubtaskByEpic(epic2);
        taskManager.addSubtask(subtask);
        taskManager.removeSubtaskById(subtask.getId());
        assertEquals(0, taskManager.getListAllSubtasks().size());
    }

    @Test
    void removeEpicTest() {
        taskManager.addEpic(epic1);
        taskManager.removeEpicById(epic1.getId());
        assertTrue(taskManager.getListAllEpics().isEmpty());
    }

    @Test
    void getTaskByIdTest() {
        taskManager.addTask(task2);
        assertEquals(task2, taskManager.getTaskById(task2.getId()).get());
    }

    @Test
    void getEpicByIdTest() {
        taskManager.addEpic(epic2);
        assertEquals(epic2, taskManager.getEpicById(epic2.getId()).get());
    }

    @Test
    void getSubtaskByIdTest() {
        Subtask subtask = generateTestSubtaskByEpic(epic2);
        taskManager.addSubtask(subtask);
        assertEquals(subtask, taskManager.getSubtaskById(subtask.getId()).get());
    }

    @Test
    void getListSubtasksByIdEpicTest() {
        Subtask subtask1 = generateTestSubtaskByEpic(epic3);
        Subtask subtask2 = generateTestSubtaskByEpic(epic3);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        assertEquals(2, taskManager.getListSubtasksByIdEpic(epic3.getId()).size());
    }

    @Test
    void getListAllTasksTest() {
        taskManager = createTaskManager();
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        assertEquals(2, taskManager.getListAllTasks().size());
    }

    @Test
    void getListAllEpicsTest() {
        taskManager = createTaskManager();
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        assertEquals(2, taskManager.getListAllEpics().size());
    }

    @Test
    void getListAllSubtasksTest() {
        taskManager = createTaskManager();
        epic1 = new Epic("1 epic", "description");
        taskManager.addSubtask(generateTestSubtaskByEpic(epic1));
        taskManager.addSubtask(generateTestSubtaskByEpic(epic1));
        assertEquals(4, taskManager.getListAllSubtasks().size());
    }

    @Test
    void removeAllTasksTest() {
        taskManager.addTask(task2);
        taskManager.removeAllTasks();
        assertTrue(taskManager.getListAllTasks().isEmpty());
    }

    @Test
    void removeAllSubtasksTest() {
        taskManager = createTaskManager();
        taskManager.addSubtask(generateTestSubtaskByEpic(epic1));
        taskManager.addSubtask(generateTestSubtaskByEpic(epic1));
        taskManager.removeAllSubtasks();
        assertTrue(taskManager.getListAllSubtasks().isEmpty());
    }

    @Test
    void removeAllEpicsTest() {
        taskManager.removeAllEpics();
        assertTrue(taskManager.getListAllEpics().isEmpty());
    }

    @Test
    void getHistoryTestEmpty() {
        setTaskManager();
        assertTrue(taskManager.getHistory().isEmpty());
    }

    @Test
    void getHistoryTestGetTaskTwice() {
        taskManager.addTask(task1);
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task1.getId());
        assertEquals( 1,taskManager.getHistory().size());
    }

    private void setTasksInHistory(){
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task3.getId());
    }

    @Test
    void getHistoryTestRemoveFirstTask() {
        setTaskManager();
        setTasksInHistory();
        taskManager.removeTaskById(task1.getId());
        assertEquals(List.of(task2,task3),taskManager.getHistory());
    }

    @Test
    void getHistoryTestRemoveCenterTask() {
        setTaskManager();
        setTasksInHistory();
        taskManager.removeTaskById(task2.getId());
        assertEquals(List.of(task1,task3),taskManager.getHistory());
    }

    @Test
    void getHistoryTestRemoveLastTask() {
        setTaskManager();
        setTasksInHistory();
        taskManager.removeTaskById(task3.getId());
        assertEquals(List.of(task1,task2),taskManager.getHistory());
    }
}
