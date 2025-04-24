package http_server;

import com.sun.net.httpserver.HttpServer;
import http_server.handlers.collections_handlers.HistoryHandler;
import http_server.handlers.collections_handlers.PrioritizedHandler;
import http_server.handlers.task_handlers.EpicHandler;
import http_server.handlers.task_handlers.SubtaskHandler;
import http_server.handlers.task_handlers.TaskHandler;
import managers.Managers;
import managers.TaskManager;
import tasks.TaskStatus;
import tasks.epic.Epic;
import tasks.subtask.Subtask;
import tasks.task.Task;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static TaskManager manager = Managers.getDefault();
    private static int PORT = 8080;

    public static void main(String[] args) {
        Task task = new Task(TaskStatus.NEW,"name1", "descr");
        Epic epic = new Epic("epic1","descr");
        manager.addTask(task);
        manager.addEpic(epic);
        Subtask subtask = new Subtask(TaskStatus.NEW,"subtask","descr", epic.getId());
        manager.addSubtask(subtask);
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
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
}