package tasks.task;

import tasks.TaskStatus;
import tasks.TaskType;

import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected TaskStatus status;
    protected TaskType type;

    public String getName() {
        return name;
    }

    protected Task(TaskStatus status, String name, String description, TaskType type) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public Task(TaskStatus status, String name, String description) {
        this.status = status;
        this.name = name;
        this.description = description;
        this.type = TaskType.Task;
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
        return id + "," + type + "," + name + "," + status + "," + description + ",";
    }
}