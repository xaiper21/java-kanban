package tasks.epic;

import tasks.subtask.Subtask;
import tasks.task.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private HashMap<Integer, Subtask> subtasks;

    public Epic(String name, String description) {
        super(TaskStatus.NEW, name, description);
        subtasks = new HashMap<>();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        calculateStatus();
    }

    private void calculateStatus() {
        if (subtasks.isEmpty()) {
            this.status = TaskStatus.NEW;
        }
        boolean isProgress = false;
        boolean isNew = false;
        boolean isDone = false;
        for (Subtask subtask : subtasks.values()) {
            TaskStatus statusSubtask = subtask.getStatus();
            switch (statusSubtask) {
                case TaskStatus.DONE -> isDone = true;
                case TaskStatus.IN_PROGRESS -> isProgress = true;
                case null, default -> isNew = true;
            }
        }
        if (!isProgress && !isDone) {
            this.status = TaskStatus.NEW;
        } else if (!isProgress && !isNew) {
            this.status = TaskStatus.DONE;
        } else this.status = TaskStatus.IN_PROGRESS;
    }

    public void removeAllSubtasks() {
        subtasks.clear();
        calculateStatus();
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public boolean isContainsSubtaskId(int id) {
        return subtasks.containsKey(id);
    }

    public void removeSubtaskById(int id) {
        subtasks.remove(id);
        calculateStatus();
    }

    public ArrayList<Subtask> getArrayListSubtasks() {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        subtasks.addAll(this.subtasks.values());
        return subtasks;
    }
}