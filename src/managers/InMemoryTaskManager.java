package managers;

import tasks.epic.Epic;
import tasks.subtask.Subtask;
import tasks.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int identifier = 0;

    private int getIdentifier() {
        return ++identifier;
    }

    protected Map<Integer, Task> taskTable;
    protected Map<Integer, Epic> epicTable;
    protected HistoryManager historyManager;

    public InMemoryTaskManager() {
        taskTable = new HashMap<>();
        epicTable = new HashMap<>();
        historyManager = new InMemoryHistoryManager();
    }

    protected void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public InMemoryTaskManager(HistoryManager historyManager) {
        taskTable = new HashMap<>();
        epicTable = new HashMap<>();
        this.historyManager = historyManager;
    }

    @Override
    public List<Task> getListAllTalks() {
        List<Task> tasks = new ArrayList<>();
        for (Task task : taskTable.values()) {
            tasks.add(task);
        }
        return tasks;
    }

    @Override
    public List<Epic> getListAllEpics() {
        List<Epic> epics = new ArrayList<>();
        for (Epic epic : epicTable.values()) {
            epics.add(epic);
        }
        return epics;
    }

    @Override
    public List<Subtask> getListAllSubtasks() {
        List<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epicTable.values()) {
            for (Subtask subtask : epic.getArrayListSubtasks()) {
                subtasks.add(subtask);
            }
        }
        return subtasks;
    }

    @Override
    public void removeAllTasks() {
        for (int taskId : taskTable.keySet()) {
            historyManager.remove(taskId);
        }
        taskTable.clear();
    }

    @Override
    public void removeAllEpics() {
        removeAllSubtasks();
        for (int epicId : epicTable.keySet()) {
            historyManager.remove(epicId);
        }
        epicTable.clear();
    }

    @Override
    public void removeAllSubtasks() {
        for (Epic epic : epicTable.values()) {
            for (Subtask subtask : epic.getArrayListSubtasks()) {
                historyManager.remove(subtask.getId());
            }
            epic.removeAllSubtasks();
        }
    }

    @Override
    public Task getTaskById(int id) {
        Task task = taskTable.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic task = epicTable.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        for (Epic epic : epicTable.values()) {
            if (epic.isContainsSubtaskId(id)) {
                Subtask subtask = epic.getSubtaskById(id);
                historyManager.add(subtask);
                return subtask;
            }
        }
        return null;
    }

    @Override
    public void addTask(Task task) {
        addTask(task, getIdentifier());
    }

    protected void addTask(Task task, int id) {
        if (task == null) return;
        task.setId(id);
        switch (task.getType()) {
            case Task -> taskTable.put(task.getId(), task);
            case Epic -> epicTable.put(task.getId(), (Epic) task);
            case Subtask -> {
                Subtask subtask = (Subtask) task;
                if (epicTable.containsKey(subtask.getIdMyEpic())) {
                    subtask.setId(id);
                    epicTable.get(subtask.getIdMyEpic()).addSubtask(subtask);
                }
            }
        }
    }

    @Override
    public void addEpic(Epic epic) {
        addTask(epic, getIdentifier());
    }

    @Override
    public void addSubtask(Subtask subtask) {
        addTask(subtask, getIdentifier());
    }

    @Override
    public void updateTask(Task task) {
        if (taskTable.containsValue(task)) {
            taskTable.put(task.getId(), task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic == null) return;
        if (!epicTable.containsValue(epic)) return;
        Epic oldEpic = epicTable.get(epic.getId());
        oldEpic.setDescription(epic.getDescription());
        oldEpic.setName(epic.getName());
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask == null) return;
        if (!epicTable.containsKey(subtask.getIdMyEpic())) return;
        if (epicTable.get(subtask.getIdMyEpic()).isContainsSubtaskId(subtask.getId())) {
            epicTable.get(subtask.getIdMyEpic()).addSubtask(subtask);
        }
    }

    @Override
    public void removeSubtaskById(int id) {
        historyManager.remove(id);
        for (Epic epic : epicTable.values()) {
            if (epic.isContainsSubtaskId(id)) {
                epic.removeSubtaskById(id);
                return;
            }
        }
    }

    @Override
    public void removeTaskById(int id) {
        historyManager.remove(id);
        taskTable.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epicTable.get(id);
        ArrayList<Subtask> listSubtasks = epic.getArrayListSubtasks();
        for (Subtask subtask : listSubtasks) {
            historyManager.remove(subtask.getId());
        }
        historyManager.remove(id);
        epicTable.remove(id);
    }

    @Override
    public ArrayList<Subtask> getListSubtasksByIdEpic(int id) {
        ArrayList<Subtask> defaultList = new ArrayList<>();
        if (epicTable.containsKey(id)) {
            return epicTable.get(id).getArrayListSubtasks();
        } else return defaultList;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}