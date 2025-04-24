package http_server;

import com.sun.net.httpserver.HttpServer;
import http_server.handlers.collections_handlers.HistoryHandler;
import http_server.handlers.collections_handlers.PrioritizedHandler;
import http_server.handlers.tasks_handlers.EpicHandler;
import http_server.handlers.tasks_handlers.SubtaskHandler;
import http_server.handlers.tasks_handlers.TaskHandler;
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

    public void start(){
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

    public void stop(){
        server.stop(10);
    }
}