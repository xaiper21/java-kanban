package httpserver.handlers.taskshandlers;

import com.sun.net.httpserver.HttpExchange;
import exeption.IntersectionCheckFromPrioritizedTasksException;
import exeption.ManagerNoContainsEpic;
import exeption.NoValidJson;
import exeption.RequestValidityException;
import httpserver.handlers.BaseHttpHandler;
import managers.TaskManager;
import tasks.TaskType;
import tasks.subtask.Subtask;
import tasks.task.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class BaseTaskHandler extends BaseHttpHandler {
    protected TaskManager manager;

    public BaseTaskHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    protected void processGet(String[] patch, HttpExchange exchange) throws IOException {
        try {
            if (patch.length == 2) {
                sendText(exchange, gson.toJson(getList()));
            } else if (patch.length == 3) {
                int id = Integer.parseInt(patch[2]);
                Optional<Task> taskOptional = getTask(id);
                if (taskOptional.isEmpty()) {
                    sendNotFound(exchange, "Ничего не найдено");
                    return;
                }
                sendText(exchange, gson.toJson(taskOptional.get()));
            }
        } catch (NumberFormatException e) {
            sendErrorValid(exchange);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void processPost(String[] patch, HttpExchange exchange) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody(),
                StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            while (br.ready()) {
                sb.append(br.readLine());
            }
            if (sb.toString().trim().equals("")) throw new NoValidJson();
            Task task = createTask(sb.toString());
            if (!manager.intersectionCheckFromPrioritizedTasks(task))
                throw new IntersectionCheckFromPrioritizedTasksException();
            if (task.getType() == TaskType.Subtask) {
                Subtask subtask = (Subtask) task;
                if (!manager.containsIdEpic(subtask.getIdMyEpic())) throw new ManagerNoContainsEpic();
            }
            if (task.getId() == 0) {
                addTask(task);
                send(exchange, "задача добавлена успешно, id:" + task.getId(), 201);
            } else {
                updateTask(task);
                send(exchange, "задача обновлена успешно", 201);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RequestValidityException e) {
            sendErrorValid(exchange);
        } catch (IntersectionCheckFromPrioritizedTasksException e) {
            sendHasInteractions(exchange);
        } catch (ManagerNoContainsEpic e) {
            send(exchange, e.getMessage(), 406);
        } catch (NoValidJson e) {
            send(exchange, e.getMessage(), 406);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    protected void processDelete(String[] patch, HttpExchange exchange) throws IOException {
        try {
            if (patch.length == 3) {
                int id = Integer.parseInt(patch[2]);
                deleteTask(id);
            }
            sendText(exchange, "задача удалена");
        } catch (NumberFormatException e) {
            sendErrorValid(exchange);
        }
    }

    protected abstract List<Task> getList();

    protected abstract Optional<Task> getTask(int id);

    protected abstract Task createTask(String valueJson);

    protected abstract void updateTask(Task task);

    protected abstract void deleteTask(int id);

    protected abstract void addTask(Task task);
}