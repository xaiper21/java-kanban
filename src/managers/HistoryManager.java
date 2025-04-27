package managers;

import tasks.task.Task;

import java.util.List;
import java.util.Optional;

public interface HistoryManager {
    void add(Optional<Task> task);

    List<Task> getHistory();

    void remove(int id);
}