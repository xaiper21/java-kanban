package tasks.task;

import tasks.TaskStatus;
import tasks.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task> {
    protected String name;
    protected String description;
    protected int id;
    protected TaskStatus status;
    protected TaskType type;
    protected Duration duration;
    protected LocalDateTime startTime;

    public String getName() {
        return name;
    }

    protected Task(TaskStatus status, String name, String description, TaskType type,
                   LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(TaskStatus status, String name, String description) {
        this(status, name, description, TaskType.Task, null, null);
    }

    public Task(TaskStatus status, String name, String description, LocalDateTime startTime, Duration duration) {
        this(status, name, description, TaskType.Task, startTime, duration);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    protected void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id);
    }

    public TaskType getType() {
        return type;
    }

    @Override
    public String toString() {
        String startTimeStr;
        String durationStr;
        if (startTime == null) {
            startTimeStr = "null";
        } else startTimeStr = startTime.toString();
        if (duration == null) {
            durationStr = "null";
        } else durationStr = "" + duration.toMinutes();
        return String.join(",", "" + id, type.toString(), name, status.toString(),
                description, startTimeStr, durationStr + ",");
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        if (startTime == null) return null;
        return startTime.plus(duration);
    }

    @Override
    public int compareTo(Task o) {
        if (id == o.getId()) return 0;
        if (startTime.isBefore(o.getStartTime())) return 1;
        return -1;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}