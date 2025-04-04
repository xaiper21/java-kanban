package managers;

import tasks.epic.Epic;
import tasks.subtask.Subtask;
import tasks.task.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getListAllTasks();

    List<Epic> getListAllEpics();

    List<Subtask> getListAllSubtasks();

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void removeSubtaskById(int id);

    void removeTaskById(int id);

    void removeEpicById(int id);

    List<Subtask> getListSubtasksByIdEpic(int id);

    List<Task> getHistory();
}