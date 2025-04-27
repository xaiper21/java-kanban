import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import httpserver.HttpTaskServer;
import httpserver.handlers.taskshandlers.gson_adapters.DurationAdapter;
import httpserver.handlers.taskshandlers.gson_adapters.LocalDateTimeAdapter;
import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.TaskStatus;
import tasks.epic.Epic;
import tasks.subtask.Subtask;
import tasks.task.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskManagerTasksTest {

    // создаём экземпляр InMemoryTaskManager
    TaskManager manager = new InMemoryTaskManager();
    // передаём его в качестве аргумента в конструктор HttpTaskServer
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    String tasksUri = "http://localhost:8080/tasks";
    String epicsUri = "http://localhost:8080/epics";
    String subtaskUri = "http://localhost:8080/subtasks";

    public HttpTaskManagerTasksTest() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        manager.removeAllEpics();
        manager.removeAllTasks();
        manager.removeAllSubtasks();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    private HttpResponse<String> methodPost(Task task, String uri) throws IOException, InterruptedException {
        String taskJson = gson.toJson(task);

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create(uri);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    private HttpResponse<String> methodGet(String uriString) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(uriString);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    private HttpResponse<String> methodDelete(String uriString) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(uriString);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    @Test
    public void testAddTask() throws IOException, InterruptedException {
        // создаём задачу
        Task task = new Task(TaskStatus.NEW, "Test 2", "Testing task 2",
                LocalDateTime.now(), Duration.ofMinutes(5));
        HttpResponse<String> response = methodPost(task, tasksUri);
        // проверяем код ответа
        assertEquals(201, response.statusCode());

        // проверяем, что создалась одна задача с корректным именем
        List<Task> tasksFromManager = manager.getListAllTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager.get(0).getName(), "Некорректное имя задачи");
    }

    @Test
    public void testAddSubtaskAndEpic() throws IOException, InterruptedException {
        Subtask subtask = new Subtask(TaskStatus.NEW, "subtask", "descriptionsubtask",
                1, LocalDateTime.now(), Duration.ofMinutes(30));
        Epic epic = new Epic("Test epic", "descr");
        HttpResponse<String> responseAddEpic = methodPost(epic, epicsUri);

        assertEquals(201, responseAddEpic.statusCode());
        List<Task> epicsFromManager = manager.getListAllEpics();
        assertNotNull(epicsFromManager, "Задачи не возвращаются");
        assertEquals(1, epicsFromManager.size(), "Некорректное количество задач");
        assertEquals("Test epic", epicsFromManager.get(0).getName(), "Некорректное имя задачи");
        
        HttpResponse<String> responseAddSubtask = methodPost(subtask, subtaskUri);
        assertEquals(201, responseAddSubtask.statusCode());
        List<Task> subtasksFromManager = manager.getListAllSubtasks();
        assertNotNull(subtasksFromManager, "Задачи не возвращаются");
        assertEquals(1, subtasksFromManager.size(), "Некорректное количество задач");
        assertEquals("subtask", subtasksFromManager.get(0).getName(), "Некорректное имя задачи");
    }

    @Test
    public void testAddSubtaskWithRandomIdEpic() throws IOException, InterruptedException {
        Subtask subtask = new Subtask(TaskStatus.NEW, "name", "descr", 22);
        HttpResponse<String> responseAddSubtask = methodPost(subtask, subtaskUri);
        assertEquals(406, responseAddSubtask.statusCode());
        assertTrue(manager.getListAllSubtasks().isEmpty());
    }

    @Test
    public void testGetTaskForNoEmptyManager() throws IOException, InterruptedException {
        HttpResponse<String> responseGetTAsks = methodGet(tasksUri);
        assertEquals(200, responseGetTAsks.statusCode());
        assertEquals("[]", responseGetTAsks.body());
    }

    @Test
    public void testGetTaskForEmptyManager() throws IOException, InterruptedException {
        manager.addTask(new Task(TaskStatus.NEW, "Test 2", "Testing task 2",
                LocalDateTime.now(), Duration.ofMinutes(5)));
        HttpResponse<String> responseGetTasks = methodGet(tasksUri);
        assertEquals(200, responseGetTasks.statusCode());
        assertTrue(responseGetTasks.body().equals(gson.toJson(manager.getListAllTasks())));
    }

    @Test
    public void testGetEpicsForNoEmptyManager() throws IOException, InterruptedException {
        manager.addEpic(new Epic("name", "descr"));
        HttpResponse<String> responseGetEpics = methodGet(epicsUri);
        assertEquals(200, responseGetEpics.statusCode());
        assertEquals(responseGetEpics.body(), gson.toJson(manager.getListAllEpics()));
    }

    @Test
    public void testGetEpicsForEmptyManager() throws IOException, InterruptedException {
        HttpResponse<String> responseGetEpics = methodGet(epicsUri);
        assertEquals(200, responseGetEpics.statusCode());
        assertEquals("[]", responseGetEpics.body());
    }

    @Test
    public void testGetSubtasksForNoEmptyManager() throws IOException, InterruptedException {
        HttpResponse<String> responseGetSubtask = methodGet(subtaskUri);
        assertEquals(200, responseGetSubtask.statusCode());
        assertEquals("[]", responseGetSubtask.body());
    }

    @Test
    public void testGetSubtasksForEmptyManager() throws IOException, InterruptedException {
        manager.addEpic(new Epic("name", "descr"));
        manager.addSubtask(new Subtask(TaskStatus.NEW, "name2", "descr", 1));
        HttpResponse<String> responseGetSubtask = methodGet(subtaskUri);
        assertEquals(200, responseGetSubtask.statusCode());
        assertTrue(responseGetSubtask.body().equals(gson.toJson(manager.getListAllSubtasks())));
    }

    @Test
    public void testGetTasksById() throws IOException, InterruptedException {
        Task task = new Task(TaskStatus.DONE, "name", "descr");
        manager.addTask(task);
        String uri = tasksUri + "/" + task.getId();
        HttpResponse<String> responseGetTaskById = methodGet(uri);
        assertEquals(200, responseGetTaskById.statusCode());
        assertEquals(gson.toJson(task), responseGetTaskById.body());
    }

    @Test
    public void testGetSubtasksByIdEpic() throws IOException, InterruptedException {
        String uri = epicsUri + "/1/subtasks";
        Epic epic = new Epic("name", "descr");
        manager.addEpic(epic);
        manager.addSubtask(new Subtask(TaskStatus.NEW, "name2", "descr", 1));
        HttpResponse<String> responseGetSubtaskByIdEpic = methodGet(uri);
        assertEquals(gson.toJson(epic.getArrayListSubtasks()), responseGetSubtaskByIdEpic.body());
        assertEquals(200, responseGetSubtaskByIdEpic.statusCode());
    }

    @Test
    public void testAddInsertionTask() throws IOException, InterruptedException {
        manager.addTask(new Task(TaskStatus.DONE, "name", "descr",
                LocalDateTime.now(), Duration.ofMinutes(30)));
        HttpResponse<String> responseAddTask = methodPost(new Task(TaskStatus.DONE, "name", "descr",
                LocalDateTime.now(), Duration.ofMinutes(30)), tasksUri);
        assertEquals(406, responseAddTask.statusCode());
        assertEquals(1, manager.getListAllTasks().size());
    }

    @Test
    public void testDeleteActualTask() throws IOException, InterruptedException {
        Task task = new Task(TaskStatus.NEW, "name", "descr");
        manager.addTask(task);
        String uri = tasksUri + "/" + task.getId();
        HttpResponse<String> responseDeleteTask = methodDelete(uri);
        assertEquals(200, responseDeleteTask.statusCode());
        assertEquals("[]", gson.toJson(manager.getListAllTasks()));
    }

    @Test
    public void testDeleteNoActualTask() throws IOException, InterruptedException {
        String uri = tasksUri + "10";
        HttpResponse<String> responseDeleteTask = methodDelete(uri);
        assertEquals(200, responseDeleteTask.statusCode());
    }
} 