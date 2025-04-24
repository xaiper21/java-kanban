package httpserver.handlers.collections_handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import httpserver.handlers.BaseHttpHandler;
import managers.TaskManager;
import tasks.task.Task;

import java.io.IOException;
import java.util.List;

public abstract class BaseHistoryAndPriortizedHttpHandler extends BaseHttpHandler {
    protected TaskManager manager;

    public BaseHistoryAndPriortizedHttpHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    protected void processGet(String[] patch, HttpExchange exchange) throws IOException {
        List<Task> tasks = getCollection();
        Gson gson = new Gson();
        sendText(exchange, gson.toJson(tasks));
    }

    @Override
    protected void processPost(String[] patch, HttpExchange exchange) throws IOException {
        processDefault("POST", exchange);
    }

    @Override
    protected void processDelete(String[] patch, HttpExchange exchange) throws IOException {
        processDefault("DELETE", exchange);
    }

    protected abstract List<Task> getCollection();
}