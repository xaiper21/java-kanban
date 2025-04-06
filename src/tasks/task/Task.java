package tasks.task;

import tasks.TaskStatus;
import tasks.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task>{
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

    protected Task(TaskStatus status, String name, String description, TaskType type) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.status = status;
    }

    public Task(TaskStatus status, String name, String description) {
        this(status,name,description,TaskType.Task);
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
        } else durationStr = ""+duration.toMinutes();
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
        return startTime.plus(duration);
    }

    public void setTime() {
        switch (status) {
            case IN_PROGRESS -> startTime = LocalDateTime.now();
            case DONE -> duration = Duration.between(startTime, LocalDateTime.now());
        }
    }

    @Override
    public int compareTo(Task o) {
        if (this.startTime.isBefore(o.startTime)) return  1;
        return  -1;
    }
}