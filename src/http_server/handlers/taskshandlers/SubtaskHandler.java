package http_server.handlers.taskshandlers;

import managers.TaskManager;
import tasks.subtask.Subtask;
import tasks.task.Task;

import java.util.List;
import java.util.Optional;

public class SubtaskHandler extends BaseTaskHandler {
    public SubtaskHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    protected List<Task> getList() {
        return manager.getListAllSubtasks();
    }

    @Override
    protected Optional<Task> getTask(int id) {
        return manager.getSubtaskById(id);
    }

    @Override
    protected Task createTask(String valueJson) {
        return gson.fromJson(valueJson, Subtask.class);
    }

    @Override
    protected void updateTask(Task task) {
        manager.updateSubtask((Subtask) task);
    }

    @Override
    protected void deleteTask(int id) {
        manager.removeSubtaskById(id);
    }

    @Override
    protected void addTask(Task task) {
        manager.addSubtask((Subtask) task);
    }
}
