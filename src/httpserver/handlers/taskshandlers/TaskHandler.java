package httpserver.handlers.taskshandlers;

import managers.TaskManager;
import tasks.task.Task;

import java.util.List;
import java.util.Optional;

public class TaskHandler extends BaseTaskHandler {


    public TaskHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    protected List<Task> getList() {
        return manager.getListAllTasks();
    }

    @Override
    protected Optional<Task> getTask(int id) {
        return manager.getTaskById(id);
    }

    @Override
    protected Task createTask(String valueJson) {
        return gson.fromJson(valueJson,Task.class);
    }

    @Override
    protected void updateTask(Task task) {
        manager.updateTask(task);
    }

    @Override
    protected void deleteTask(int id) {
        manager.removeTaskById(id);
    }

    @Override
    protected void addTask(Task task) {
        manager.addTask(task);
    }
}
