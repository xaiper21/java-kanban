package managers;

import history.HistorySet;
import tasks.task.Task;

import java.util.List;
import java.util.Optional;

public class InMemoryHistoryManager implements HistoryManager {

    private HistorySet<Task> list;

    public InMemoryHistoryManager() {
        list = new HistorySet<Task>();
    }

    @Override
    public void add(Optional<Task> task) {
        if (task.isPresent()) list.add(task.get());
    }

    @Override
    public List<Task> getHistory() {
        return list.getList();
    }

    @Override
    public void remove(int id) {
        list.remove(id);
    }
}