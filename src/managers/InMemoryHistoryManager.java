package managers;

import history.HistorySet;
import tasks.task.Task;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private HistorySet<Task> list;

    public InMemoryHistoryManager() {
        list = new HistorySet<Task>();
    }

    @Override
    public void add(Task task) {
        list.add(task);
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