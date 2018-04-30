package fr.devnr.jarialtekinapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusTest {

    @Test
    void GetValue() {
        // --{ ACT & ASSERT }--
        assertAll("priorities",
            () -> assertEquals(Integer.valueOf(0), Status.TODO.getValue()),
            () -> assertEquals(Integer.valueOf(1), Status.DOING.getValue()),
            () -> assertEquals(Integer.valueOf(2), Status.DONE.getValue()),
            () -> assertEquals(Integer.valueOf(-1), Status.NONE.getValue())
        );
    }

    @Test
    void ValueOf() {
        // --{ ACT & ASSERT }--
        assertAll("priorities",
            () -> assertEquals(Status.TODO, Status.valueOf(0)),
            () -> assertEquals(Status.DOING,  Status.valueOf(1)),
            () -> assertEquals(Status.DONE, Status.valueOf(2)),
            () -> assertEquals(Status.NONE, Status.valueOf(-1)),
            () -> assertEquals(Status.NONE, Status.valueOf(187))
        );
    }

}
