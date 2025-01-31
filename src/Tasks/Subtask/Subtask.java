package Tasks.Subtask;

import Tasks.Task.Task;
import Tasks.TaskStatus;

public class Subtask extends Task {
    private int idMyEpic;

    public Subtask(TaskStatus status, String name, String description, int idMyEpic) {
        super(status, name, description);
        this.idMyEpic = idMyEpic;
    }

    public int getIdMyEpic() {
        return idMyEpic;
    }
}
