import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;

public abstract class TaskManagerTest<T extends TaskManager>{
    protected T taskManager;

    protected abstract T createTaskManager();

    @BeforeEach
    void setTaskManager(){
        taskManager = createTaskManager();
    }

}
