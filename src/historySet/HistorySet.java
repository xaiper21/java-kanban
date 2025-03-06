package historySet;

import tasks.task.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistorySet<T extends Task> {
    private final Map<Integer, Node<T>> map;
    private final LinkedHistoryList<T> list;

    public HistorySet() {
        map = new HashMap<>();
        list = new LinkedHistoryList<T>();
    }

    public List<T> getList() {
        return list.getTasks();
    }

    public void add(T item) {
        if (map.containsKey(item.getId())) {
            list.removeNode(map.get(item.getId()));
            map.remove(item.getId());
        }
        Node<T> newNode = new Node<>(item);
        list.linkLast(newNode);
        map.put(item.getId(), newNode);
    }

    public void remove(int id) {
        if (map.containsKey(id)) {
            list.removeNode(map.get(id));
            map.remove(id);
        }
    }
}