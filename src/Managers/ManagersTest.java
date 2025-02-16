package Managers;

import Tasks.Task.Task;
import Tasks.TaskStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    public void checkTaskManagerAddAndGetTaskTrue(){
        Managers managers = new Managers();
         TaskManager inMemoryTaskManager = managers.getDefault();
        Task task1 = new Task(TaskStatus.NEW,"1", "2");
         inMemoryTaskManager.addTask(task1);
         assertTrue(task1.equals(inMemoryTaskManager.getTaskById(1)));
    }

}