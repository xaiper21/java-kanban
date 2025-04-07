package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.TaskStatus;
import tasks.epic.Epic;
import tasks.subtask.Subtask;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    Epic epic;
    Epic epic2;
    Subtask subtaskDone1;
    Subtask subtaskDone2;
    Subtask subtaskDone3;
    Subtask subtaskNew1;
    Subtask subtaskNew2;
    Subtask subtaskNew3;
    Subtask subtaskInProgress1;
    Subtask subtaskInProgress2;
    Subtask subtaskInProgress3;


    @BeforeEach
    public void update() {
        epic = new Epic("1", "2");
        epic2 = new Epic("2", "1");
        epic.setId(1);
        epic2.setId(2);
        subtaskDone1 = new Subtask(TaskStatus.DONE, "done", "1", 1);
        subtaskDone2 = new Subtask(TaskStatus.DONE, "done", "2", 1);
        subtaskDone3 = new Subtask(TaskStatus.DONE, "done", "3", 1);
        subtaskDone1.setId(14);
        subtaskDone2.setId(15);
        subtaskDone3.setId(16);
        subtaskNew1 = new Subtask(TaskStatus.NEW, "new", "1", 1);
        subtaskNew2 = new Subtask(TaskStatus.NEW, "new", "2", 1);
        subtaskNew3 = new Subtask(TaskStatus.NEW, "new", "3", 1);
        subtaskNew1.setId(11);
        subtaskNew2.setId(12);
        subtaskNew3.setId(13);
        subtaskInProgress1 = new Subtask(TaskStatus.IN_PROGRESS, "in progress", "1", 1);
        subtaskInProgress2 = new Subtask(TaskStatus.IN_PROGRESS, "in progress", "2", 1);
        subtaskInProgress3 = new Subtask(TaskStatus.IN_PROGRESS, "in progress", "3", 1);
        subtaskInProgress1.setId(17);
        subtaskInProgress2.setId(18);
        subtaskInProgress1.setId(19);
    }


    @Test
    public void equalsByIdEpics() {
        epic.setId(3);
        epic2.setId(3);
        assertTrue(epic.equals(epic2));
        assertTrue(epic2.equals(epic));
    }

    @Test
    public void noEqualsByIdEpics() {
        epic2.setId(3);
        epic.setId(4);
        assertFalse(epic.equals(epic2));
        assertFalse(epic2.equals(epic));
    }

    @Test
    public void checkTypeAddSubtasks() {
        subtaskNew1.setId(10);
        epic.addSubtask(subtaskNew1);
        assertTrue(epic.getSubtaskById(10) instanceof Subtask);
    }

    @Test
    public void checkStatusForAllSubtaskNEW() {
        epic.addSubtask(subtaskNew1);
        epic.addSubtask(subtaskNew2);
        epic.addSubtask(subtaskNew3);
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void checkStatusForAllSubtaskDONE() {
        epic.addSubtask(subtaskDone1);
        epic.addSubtask(subtaskDone2);
        epic.addSubtask(subtaskDone3);
        assertEquals(TaskStatus.DONE, epic.getStatus());
    }

    @Test
    public void checkStatusForAllSubtaskNEWandDONE() {
        epic.addSubtask(subtaskNew1);
        epic.addSubtask(subtaskNew2);
        epic.addSubtask(subtaskNew3);
        epic.addSubtask(subtaskDone1);
        epic.addSubtask(subtaskDone2);
        epic.addSubtask(subtaskDone3);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void checkStatusForAllSubtaskIN_PROGRESS() {
        epic.addSubtask(subtaskInProgress1);
        epic.addSubtask(subtaskInProgress2);
        epic.addSubtask(subtaskInProgress3);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }
}