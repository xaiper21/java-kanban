import managers.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import tasks.TaskStatus;
import tasks.task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @Override
    protected InMemoryTaskManager createTaskManager() {
        return new InMemoryTaskManager();
    }


    @Test
    void intersectionCheckFromPrioritizedTasksTestIntersection() {
        Task taskTimeTest1 = new Task(TaskStatus.NEW, "name", "description",
                LocalDateTime.now(), Duration.ofMinutes(30));
        Task taskTimeTest2 = new Task(TaskStatus.NEW, "name", "description",
                LocalDateTime.now(), Duration.ofMinutes(15));
        taskManager.addTask(taskTimeTest1);
        taskManager.addTask(taskTimeTest2);
        assertEquals(List.of(taskTimeTest1), taskManager.getPrioritizedTasks());
    }

    @Test
    void intersectionCheckFromPrioritizedTasksTestNotIntersection() {
        Task taskTimeTest1 = new Task(TaskStatus.NEW, "name", "description",
                LocalDateTime.now(), Duration.ofMinutes(30));
        Task taskTimeTest2 = new Task(TaskStatus.NEW, "name", "description",
                LocalDateTime.now().plusDays(2), Duration.ofMinutes(15));
        taskManager.addTask(taskTimeTest1);
        taskManager.addTask(taskTimeTest2);
        assertEquals(List.of(taskTimeTest1, taskTimeTest2), taskManager.getPrioritizedTasks());
    }
}
