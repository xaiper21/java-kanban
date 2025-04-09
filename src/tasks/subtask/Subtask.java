package tasks.subtask;

import tasks.TaskStatus;
import tasks.TaskType;
import tasks.task.Task;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int idMyEpic;

    public Subtask(TaskStatus status, String name, String description, int idMyEpic, LocalDateTime startTime,
                   Duration duration) {
        super(status, name, description, TaskType.Subtask, startTime, duration);
        this.idMyEpic = idMyEpic;
    }

    public Subtask(TaskStatus status, String name, String description, int idMyEpic) {
        this(status, name, description, idMyEpic, null, null);
    }

    public int getIdMyEpic() {
        return idMyEpic;
    }

    @Override
    public String toString() {
        return super.toString() + idMyEpic;
    }
}