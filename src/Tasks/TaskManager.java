package Tasks;

import Tasks.Epic.Epic;
import Tasks.Subtask.Subtask;
import Tasks.Task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int identifier = 0;

    private int getIdentifier() {
        return identifier++;
    }

    private HashMap<Integer, Task> taskTable;
    private HashMap<Integer, Epic> epicTable;

    public TaskManager() {
        taskTable = new HashMap<>();
        epicTable = new HashMap<>();
    }

    public ArrayList<Task> getListAllTalks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskTable.values()) {
            tasks.add(task);
        }
        return tasks;
    }

    public ArrayList<Epic> getListAllEpics() {
        ArrayList<Epic> epics = new ArrayList<>();
        for (Epic epic : epicTable.values()) {
            epics.add(epic);
        }
        return epics;
    }

    public ArrayList<Subtask> getListAllSubtasks() {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epicTable.values()) {
            for (Subtask subtask : epic.getArrayListSubtasks()) {
                subtasks.add(subtask);
            }
        }
        return subtasks;
    }

    public void removeAllTasks() {
        taskTable.clear();
    }

    public void removeAllEpics() {
        epicTable.clear();
    }

    public void removeAllSubtasks() {
        for (Epic epic : epicTable.values()) {
            epic.removeAllSubtasks();
        }
    }

    public Task getTaskById(int id) {
        return taskTable.get(id);
    }

    public Epic getEpicById(int id) {
        return epicTable.get(id);
    }

    public Subtask getSubtaskById(int id) {
        for (Epic epic : epicTable.values()) {
            if (epic.isContainsSubtaskId(id)) {
                return epic.getSubtaskById(id);
            }
        }
        return null;
    }

    public void addTask(Task task) {
        if (task == null) return;
        task.setId(getIdentifier());
        taskTable.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        if (epic == null) return;
        epic.setId(getIdentifier());
        epicTable.put(epic.getId(), epic);
    }

    public void addSubtask(Subtask subtask) {
        if (subtask == null) return;
        if (epicTable.containsKey(subtask.getIdMyEpic())) {
            subtask.setId(getIdentifier());
            epicTable.get(subtask.getIdMyEpic()).addSubtask(subtask);
        }
    }

    public void updateTask(Task task) {
        if (taskTable.containsValue(task)) {
            taskTable.put(task.getId(), task);
        }
    }

    public void updateEpic(Epic epic) {
        if (epic == null) return;
        if (!epicTable.containsValue(epic)) return;
        Epic oldEpic = epicTable.get(epic.getId());
        oldEpic.setDescription(epic.getDescription());
        oldEpic.setName(epic.getName());
    }

    public void updateSubtask(Subtask subtask) {
        if (subtask == null) return;
        if (epicTable.get(subtask.getIdMyEpic()).isContainsSubtaskId(subtask.getId())) {
            epicTable.get(subtask.getIdMyEpic()).addSubtask(subtask);
        }
    }

    public void removeSubtsakById(int id) {
        for (Epic epic : epicTable.values()) {
            if (epic.isContainsSubtaskId(id)) {
                epic.removeSubtaskById(id);
                return;
            }
        }
    }

    public void removeTastById(int id) {
        taskTable.remove(id);
    }

    public void removeEpicById(int id) {
        epicTable.remove(id);
    }

    public ArrayList<Subtask> getListSubtasksByIdEpic(int id) {
        ArrayList<Subtask> defaultList = new ArrayList<>();
        if (epicTable.containsKey(id)) {
            return epicTable.get(id).getArrayListSubtasks();
        } else return defaultList;
    }
}
