package fr.devnr.jarialtekinapi.graphql.serializer;

import fr.devnr.jarialtekinapi.graphql.dto.TaskDTO;
import fr.devnr.jarialtekinapi.model.Task;
import fr.devnr.jarialtekinapi.model.TaskPlanning;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class TaskSerializerTest {

    @Test
    void SerializeNull() {
        assertNull( TaskSerializer.serialize((Task) null) );
        assertNull( TaskSerializer.serialize((TaskPlanning) null) );
    }

    @Test
    void SerializeTask() {
        // --{ ACT }--
        TaskDTO dto = TaskSerializer.serialize( new Task(1L, "T1") );
        // --{ ASSERT }--
        assertEquals(Long.valueOf(1L), dto.id);
        assertEquals("T1", dto.name);
    }

    @Test
    void SerializeTaskPlanning() {
        // --{ ARRANGE }--
        TaskPlanning planning = new TaskPlanning(
            new Task(2L, "T2"),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        // --{ ACT }--
        TaskDTO dto = TaskSerializer.serialize( planning );
        // --{ ASSERT }--
        assertEquals(Long.valueOf(2L), dto.id);
        assertEquals("T2", dto.name);
    }

}
