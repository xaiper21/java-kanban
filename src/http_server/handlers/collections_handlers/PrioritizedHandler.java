package http_server.handlers.collections_handlers;

import managers.TaskManager;
import tasks.task.Task;

import java.util.List;

public class PrioritizedHandler extends BaseHistoryAndPriortizedHttpHandler {

    public PrioritizedHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    protected List<Task> getCollection() {
        return manager.getPrioritizedTasks();
    }
}