package Managers;

import Tasks.Epic.Epic;
import Tasks.Subtask.Subtask;
import Tasks.Task.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getListAllTalks();

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
