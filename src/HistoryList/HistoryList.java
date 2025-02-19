package HistoryList;

import Tasks.Task.Task;

import java.util.ArrayList;
import java.util.List;

public class HistoryList<T extends Task> {
    private final int MAX_COUNT_ELEMENTS_IN_LIST = 10;
    private List<T> historyList;

    public HistoryList() {
        historyList = new ArrayList<>();
    }

    public void add(T item) {
        if (historyList.size() == MAX_COUNT_ELEMENTS_IN_LIST) {
            historyList.remove(0);
        }
        historyList.add(item);
    }

    public List<T> getList() {
        return new ArrayList<>(historyList);
    }
}
