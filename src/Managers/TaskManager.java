package Managers;

import Tasks.Epic.Epic;
import Tasks.Subtask.Subtask;
import Tasks.Task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    ArrayList<Task> getListAllTalks();

    ArrayList<Epic> getListAllEpics();

    ArrayList<Subtask> getListAllSubtasks();

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

    ArrayList<Subtask> getListSubtasksByIdEpic(int id);


}
