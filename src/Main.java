import Managers.HistoryManager;
import Managers.InMemoryHistoryManager;
import Managers.InMemoryTaskManager;
import Managers.TaskManager;
import Tasks.Epic.Epic;
import Tasks.Subtask.Subtask;
import Tasks.Task.Task;
import Tasks.TaskStatus;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);

        Task task1 = new Task(TaskStatus.NEW, "таска 1", "что-то сделать в первой задаче");
        Task task2 = new Task(TaskStatus.NEW, "таска 2", "что-то сделать во второй задаче");

        Epic epicNoEmpty = new Epic("Эпик 1", "не пустой эпик");
        Epic epicEmpty = new Epic("Эпик 2", "пустой эпик");

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.addEpic(epicNoEmpty);

        Subtask subtask1 = new Subtask(TaskStatus.NEW, "name 1", "description 1", epicNoEmpty.getId() );
        Subtask subtask2 = new Subtask(TaskStatus.NEW, "name 2", "description 2", epicNoEmpty.getId());
        Subtask subtask3 = new Subtask(TaskStatus.NEW, "name 3", "description 3", epicNoEmpty.getId());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);
        taskManager.addEpic(epicEmpty);

        taskManager.getTaskById(task1.getId());
        System.out.println(historyManager.getHistory());
        taskManager.getSubtaskById(subtask2.getId());
        System.out.println(historyManager.getHistory());
        taskManager.getEpicById(epicEmpty.getId());
        System.out.println(historyManager.getHistory());
        taskManager.getTaskById(task2.getId());
        System.out.println(historyManager.getHistory());
        taskManager.getSubtaskById(subtask1.getId());
        System.out.println(historyManager.getHistory());
        taskManager.getSubtaskById(subtask3.getId());
        System.out.println(historyManager.getHistory());
        taskManager.getEpicById(subtask2.getIdMyEpic());
        System.out.println(historyManager.getHistory());
        taskManager.getTaskById(task1.getId());
        System.out.println(historyManager.getHistory());
        taskManager.getTaskById(task1.getId());
        System.out.println(historyManager.getHistory());
        taskManager.getSubtaskById(subtask2.getId());
        System.out.println(historyManager.getHistory());
        taskManager.getEpicById(epicEmpty.getId());
        System.out.println(historyManager.getHistory());
        taskManager.getTaskById(task2.getId());
        System.out.println(historyManager.getHistory());
        taskManager.getSubtaskById(subtask1.getId());
        System.out.println(historyManager.getHistory());
        taskManager.getSubtaskById(subtask3.getId());
        System.out.println(historyManager.getHistory());
        taskManager.getEpicById(subtask2.getIdMyEpic());
        System.out.println(historyManager.getHistory());
        taskManager.getTaskById(task1.getId());
        System.out.println(historyManager.getHistory());

        historyManager.remove(task1.getId());
        historyManager.remove(task2.getId());



        System.out.println(historyManager.getHistory());
        taskManager.removeEpicById(epicNoEmpty.getId());

        System.out.println(historyManager.getHistory());


    }


}
