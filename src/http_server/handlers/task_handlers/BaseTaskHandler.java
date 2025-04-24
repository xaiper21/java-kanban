package http_server.handlers.task_handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import exeption.IntersectionCheckFromPrioritizedTasksException;
import exeption.RequestValidityException;
import http_server.handlers.BaseHttpHandler;
import http_server.handlers.task_handlers.gson_adapters.DurationAdapter;
import http_server.handlers.task_handlers.gson_adapters.LocalDateTimeAdapter;
import managers.TaskManager;
import tasks.task.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public abstract class BaseTaskHandler extends BaseHttpHandler {
    protected TaskManager manager;
    protected Gson gson;

    public BaseTaskHandler(TaskManager manager) {
        this.manager = manager;
        gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
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
            Task task = createTask(sb.toString());
            if (!manager.intersectionCheckFromPrioritizedTasks(task))
                throw new IntersectionCheckFromPrioritizedTasksException();
            if (task.getId() == 0) {
                addTask(task);
                send(exchange,"задача добавлена успешно",200);
            } else {

                updateTask(task);
                send(exchange,"задача обновлена успешно",200);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RequestValidityException e) {
            sendErrorValid(exchange);
        } catch (IntersectionCheckFromPrioritizedTasksException e) {
            sendHasInteractions(exchange);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void processDelete(String[] patch, HttpExchange exchange) throws IOException {
        try {
            if (patch.length == 3) {
                int id = Integer.parseInt(patch[2]);
                deleteTask(id);
                sendText(exchange, "задача удалена");
            }
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
