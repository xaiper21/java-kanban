package Managers;

import HistoryList.HistoryList;
import Tasks.Task.Task;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private HistoryList<Task> list;

    public InMemoryHistoryManager(){
        list = new HistoryList<>();
    }

    @Override
    public void add(Task task) {
        list.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return list.getList();
    }
}
