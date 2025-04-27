package httpserver;

import com.sun.net.httpserver.HttpServer;
import httpserver.handlers.collections_handlers.HistoryHandler;
import httpserver.handlers.collections_handlers.PrioritizedHandler;
import httpserver.handlers.taskshandlers.EpicHandler;
import httpserver.handlers.taskshandlers.SubtaskHandler;
import httpserver.handlers.taskshandlers.TaskHandler;
import managers.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private final TaskManager manager;
    private static int PORT = 8080;
    private HttpServer server;

    public HttpTaskServer(TaskManager manager) {
        this.manager = manager;
    }

    public void start() {
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/tasks", new TaskHandler(manager));
            server.createContext("/subtasks", new SubtaskHandler(manager));
            server.createContext("/epics", new EpicHandler(manager));
            server.createContext("/history", new HistoryHandler(manager));
            server.createContext("/prioritized", new PrioritizedHandler(manager));
            server.start();
            System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        server.stop(0);
    }
}