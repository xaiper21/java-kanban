package http_server.handlers.tasks_handlers;

import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;
import tasks.epic.Epic;
import tasks.task.Task;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class EpicHandler extends BaseTaskHandler {

    public EpicHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    protected List<Task> getList() {
        return manager.getListAllEpics();
    }

    @Override
    protected Optional<Task> getTask(int id) {
        return manager.getEpicById(id);
    }

    @Override
    protected Task createTask(String valueJson) {
        return gson.fromJson(valueJson, Epic.class);
    }

    @Override
    protected void updateTask(Task task) {
        manager.updateEpic((Epic) task);
    }

    @Override
    protected void processGet(String[] patch, HttpExchange exchange) throws IOException {
        if (patch.length <= 3) {
            super.processGet(patch, exchange);
        } else if (patch.length == 4 && patch[3].equals("subtasks")) {
            int id = Integer.parseInt(patch[2]);
            Optional<Task> taskOptional = getTask(id);
            if (taskOptional.isEmpty()) {
                sendNotFound(exchange, "Ничего не найдено");
                return;
            }
            Epic epic = (Epic) taskOptional.get();

            sendText(exchange, gson.toJson(epic.getArrayListSubtasks()));
        } else {
            sendNotFound(exchange, "Ничего не найдено");
        }
    }

    @Override
    protected void deleteTask(int id) {
        manager.removeEpicById(id);
    }

    @Override
    protected void addTask(Task task) {
        manager.addEpic((Epic) task);
    }
}
