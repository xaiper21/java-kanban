package managers;

import tasks.epic.Epic;
import tasks.subtask.Subtask;
import tasks.task.Task;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private int identifier = 0;

    private int getIdentifier() {
        return ++identifier;
    }

    protected Map<Integer, Task> taskTable;
    protected Map<Integer, Epic> epicTable;
    protected HistoryManager historyManager;
    protected TreeSet<Task> prioritizedTasks;

    public InMemoryTaskManager() {
        this(new InMemoryHistoryManager());
    }

    protected void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public InMemoryTaskManager(HistoryManager historyManager) {
        taskTable = new HashMap<>();
        epicTable = new HashMap<>();
        this.historyManager = historyManager;
        prioritizedTasks = new TreeSet<>(Task::compareTo);
    }

    @Override
    public List<Task> getListAllTasks() {
        return taskTable.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Epic> getListAllEpics() {
        return epicTable.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Subtask> getListAllSubtasks() {
       return epicTable.values().stream()
               .flatMap(epic -> epic.getArrayListSubtasks().stream())
               .collect(Collectors.toList());
    }

    @Override
    public void removeAllTasks() {
        for (int taskId : taskTable.keySet()) {
            removePrioritizedTask(taskTable.get(taskId));
            historyManager.remove(taskId);
        }
        taskTable.clear();
    }

    @Override
    public void removeAllEpics() {
        removeAllSubtasks();
        for (int epicId : epicTable.keySet()) {
            removePrioritizedTask(epicTable.get(epicId));
            historyManager.remove(epicId);
        }
        epicTable.clear();
    }

    @Override
    public void removeAllSubtasks() {
        for (Epic epic : epicTable.values()) {
            for (Subtask subtask : epic.getArrayListSubtasks()) {
                historyManager.remove(subtask.getId());
                removePrioritizedTask(subtask);
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
        addPrioritizedTask(task);
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
            task.setTime();
            taskTable.put(task.getId(), task);
        }
        addPrioritizedTask(task);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic == null) return;
        if (!epicTable.containsValue(epic)) return;
        Epic oldEpic = epicTable.get(epic.getId());
        oldEpic.setDescription(epic.getDescription());
        oldEpic.setName(epic.getName());
        addPrioritizedTask(epic);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask == null) return;
        if (!epicTable.containsKey(subtask.getIdMyEpic())) return;
        if (epicTable.get(subtask.getIdMyEpic()).isContainsSubtaskId(subtask.getId())) {
            epicTable.get(subtask.getIdMyEpic()).addSubtask(subtask);
        }
        subtask.setTime();
        addPrioritizedTask(subtask);
    }

    @Override
    public void removeSubtaskById(int id) {
        historyManager.remove(id);
        for (Epic epic : epicTable.values()) {
            if (epic.isContainsSubtaskId(id)) {
                removePrioritizedTask(epic.getSubtaskById(id));
                epic.removeSubtaskById(id);
                return;
            }
        }
    }

    @Override
    public void removeTaskById(int id) {
        historyManager.remove(id);
        removePrioritizedTask(taskTable.get(id));
        taskTable.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epicTable.get(id);
        ArrayList<Subtask> listSubtasks = epic.getArrayListSubtasks();
        for (Subtask subtask : listSubtasks) {
            historyManager.remove(subtask.getId());
            removePrioritizedTask(subtask);
        }
        removePrioritizedTask(epicTable.get(id));
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

    public List<Task> getPrioritizedTasks(){
        return  prioritizedTasks.stream().collect(Collectors.toList());
    }

    private void addPrioritizedTask(Task task){
        if (task == null) return;
        switch (task.getStatus()){
            case NEW, DONE -> prioritizedTasks.remove(task);
            case IN_PROGRESS -> prioritizedTasks.add(task);
        }
    }

    private void removePrioritizedTask(Task task){
        prioritizedTasks.remove(task);
    }

}