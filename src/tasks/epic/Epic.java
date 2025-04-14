package tasks.epic;

import tasks.TaskStatus;
import tasks.TaskType;
import tasks.subtask.Subtask;
import tasks.task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private HashMap<Integer, Subtask> subtasks;
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(TaskStatus.NEW, name, description, TaskType.Epic, null, null);
        subtasks = new HashMap<>();
        endTime = null;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        calculateStatus();
        updateTime();
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
                case TaskStatus.NEW -> isNew = true;
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
        updateTime();
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
        updateTime();
    }

    public ArrayList<Subtask> getArrayListSubtasks() {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        subtasks.addAll(this.subtasks.values());
        return subtasks;
    }

    private void updateTime() {
        LocalDateTime minStartTime = null;
        LocalDateTime maxEndTime = null;
        Duration sumDuration = Duration.ZERO;

        for (Subtask subtask : subtasks.values()) {
            if (subtask.getDuration() != null && subtask.getStartTime() != null) {
                sumDuration.plus(subtask.getDuration());
                if (minStartTime == null && maxEndTime == null) {
                    minStartTime = subtask.getStartTime();
                    maxEndTime = subtask.getEndTime();
                } else {
                    if (minStartTime.isAfter(subtask.getStartTime())) {
                        minStartTime = subtask.getStartTime();
                    }
                    if (maxEndTime.isBefore(subtask.getEndTime())) {
                        maxEndTime = subtask.getEndTime();
                    }
                }
            }
        }
        duration = sumDuration;
        startTime = minStartTime;
        endTime = maxEndTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

}