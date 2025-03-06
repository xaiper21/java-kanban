package test;

import tasks.epic.Epic;
import tasks.subtask.Subtask;
import tasks.TaskStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubtaskTest {

    @Test
    public void testEqualsSubtasks1() {
        Subtask subtask1 = new Subtask(TaskStatus.NEW, "1", "2", 3);
        Subtask subtask2 = new Subtask(TaskStatus.NEW, "2", "3", 3);
        subtask1.setId(2);
        subtask2.setId(2);
        assertTrue(subtask1.equals(subtask2));
    }

    @Test
    public void testEqualsSubtasks2() {
        Subtask subtask1 = new Subtask(TaskStatus.NEW, "1", "2", 3);
        Subtask subtask2 = new Subtask(TaskStatus.NEW, "2", "3", 5);
        subtask1.setId(2);
        subtask2.setId(2);
        assertTrue(subtask1.equals(subtask2));
    }

    @Test
    public void testNoEqualsSubtasks() {
        Subtask subtask1 = new Subtask(TaskStatus.NEW, "1", "2", 3);
        Subtask subtask2 = new Subtask(TaskStatus.NEW, "1", "3", 3);
        subtask1.setId(2);
        subtask2.setId(3);
        assertFalse(subtask1.equals(subtask2));
    }

    @Test
    public void subtaskAddInEpic() {
        Subtask subtask1 = new Subtask(TaskStatus.NEW, "1", "2", 3);
        subtask1.setId(12);
        Epic epic = new Epic("1", "2");
        epic.addSubtask(subtask1);
        assertTrue(epic.getSubtaskById(12) instanceof Subtask);
    }
}