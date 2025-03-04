package Managers;

public final class Managers {

    public static final HistoryManager historyManager = new InMemoryHistoryManager();
    public static final TaskManager manager = new InMemoryTaskManager(historyManager);
    public static TaskManager getDefault() {
        return manager;
    }

    public static HistoryManager getDefaultHistory(){
        return historyManager;
    }
}
