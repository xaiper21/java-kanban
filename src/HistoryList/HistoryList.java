package HistoryList;

import Tasks.Task.Task;

import java.util.*;

public class HistoryList<T extends Task> {
    private Map<Integer,T> historyMap;

    public HistoryList() {
        historyMap = new LinkedHashMap<>();
    }

    public void add(T item) {
        if (historyMap.containsValue(item)){
            historyMap.remove(item.getId());
        }
            historyMap.put(item.getId(), item);
    }

    public List<T> getList() {
        return new ArrayList<>(historyMap.values());
    }

    public void removeById(int id){
        historyMap.remove(id);
    }

}
