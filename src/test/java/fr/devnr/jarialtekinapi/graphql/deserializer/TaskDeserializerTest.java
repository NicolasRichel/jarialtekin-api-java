package fr.devnr.jarialtekinapi.graphql.deserializer;

import fr.devnr.jarialtekinapi.graphql.dto.TaskDTO;
import fr.devnr.jarialtekinapi.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class TaskDeserializerTest {

    @Test
    void DeserializeNull() {
        assertNull( TaskDeserializer.deserialize(null) );
    }

    @Test
    void DeserializeTaskDTO() {
        // --{ ARRANGE & ACT }--
        Task task = TaskDeserializer.deserialize(new TaskDTO(1L, "T1", "A Task"));
        // --{ ASSERT }--
        assertEquals(Long.valueOf(1), task.getId());
        assertEquals("T1", task.getName());
        assertEquals("A Task", task.getDescription());
    }

}
