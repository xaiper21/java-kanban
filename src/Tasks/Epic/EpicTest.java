package Tasks.Epic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class EpicTest {

    @Test
    public void equalsByIdEpics(){
        Epic epic1 = new Epic("1", "2");
        Epic epic2 = new Epic("2", "1");
        epic2.setId(3);
        epic1.setId(3);
        assertTrue(epic1.equals(epic2));
        assertTrue(epic2.equals(epic1));
    }

    @Test
    public void noEqualsByIdEpics(){
        Epic epic1 = new Epic("1", "2");
        Epic epic2 = new Epic("2", "1");
        epic2.setId(3);
        epic1.setId(4);
        assertFalse(epic1.equals(epic2));
        assertFalse(epic2.equals(epic1));
    }
}