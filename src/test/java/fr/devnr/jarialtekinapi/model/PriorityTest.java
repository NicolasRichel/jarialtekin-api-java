package fr.devnr.jarialtekinapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PriorityTest {

    @Test
    void GetValue() {
        // --{ ACT & ASSERT }--
        assertAll("priorities",
            () -> assertEquals(Integer.valueOf(0), Priority.NORMAL.getValue()),
            () -> assertEquals(Integer.valueOf(1), Priority.HIGH.getValue()),
            () -> assertEquals(Integer.valueOf(-1), Priority.LOW.getValue())
        );
    }

    @Test
    void ValueOf() {
        // --{ ACT & ASSERT }--
        assertAll("priorities",
            () -> assertEquals(Priority.NORMAL, Priority.valueOf(0)),
            () -> assertEquals(Priority.HIGH,  Priority.valueOf(1)),
            () -> assertEquals(Priority.LOW, Priority.valueOf(-1)),
            () -> assertEquals(Priority.NORMAL, Priority.valueOf(187))
        );
    }

}
