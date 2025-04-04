package tasks.subtask;

import tasks.TaskType;
import tasks.task.Task;
import tasks.TaskStatus;

public class Subtask extends Task {
    private int idMyEpic;

    public Subtask(TaskStatus status, String name, String description, int idMyEpic) {
        super(status, name, description, TaskType.Subtask);
        this.idMyEpic = idMyEpic;
    }

    public int getIdMyEpic() {
        return idMyEpic;
    }

    @Override
    public String toString() {
        return super.toString() + idMyEpic;
    }
}