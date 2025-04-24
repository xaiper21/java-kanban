import com.google.gson.Gson;
import http_server.HttpTaskServer;
import http_server.handlers.task_handlers.BaseTaskHandler;
import managers.HistoryManager;
import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import managers.TaskManager;
import tasks.epic.Epic;
import tasks.subtask.Subtask;
import tasks.task.Task;
import tasks.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        HttpTaskServer server = new HttpTaskServer(new InMemoryTaskManager());
        server.start();

    }
}
