package Tests;

import Tasks.Task.Task;
import Tasks.TaskStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTest {

    @Test
    public void checkEqualsIdTask1_5_IdTask2_5() {
        Task task1 = new Task(TaskStatus.NEW, "1", "2");
        Task task2 = new Task(TaskStatus.NEW, "2", "3");
        task2.setId(5);
        task1.setId(5);
        assertTrue(task1.equals(task2));
    }

    @Test
    public void checkEqualsIdTask1_5_IdTask2_9() {
        Task task1 = new Task(TaskStatus.NEW, "1", "2");
        Task task2 = new Task(TaskStatus.NEW, "2", "3");
        task2.setId(9);
        task1.setId(5);
        assertFalse(task1.equals(task2));
    }
}