package managers;

import tasks.epic.Epic;
import tasks.subtask.Subtask;
import tasks.task.Task;

import java.util.List;
import java.util.Optional;

public interface TaskManager {
    List<Task> getListAllTasks();

    List<Task> getListAllEpics();

    List<Task> getListAllSubtasks();

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();

    Optional<Task> getTaskById(int id);

    Optional<Task> getEpicById(int id);

    Optional<Task> getSubtaskById(int id);

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    void updateTask(Task task);///

    void updateEpic(Epic epic);///

    void updateSubtask(Subtask subtask);///

    void removeSubtaskById(int id);

    void removeTaskById(int id);

    void removeEpicById(int id);

    List<Subtask> getListSubtasksByIdEpic(int id);

    List<Task> getHistory();

    List<Task> getPrioritizedTasks();

    boolean intersectionCheckFromPrioritizedTasks(Task task); ///
}