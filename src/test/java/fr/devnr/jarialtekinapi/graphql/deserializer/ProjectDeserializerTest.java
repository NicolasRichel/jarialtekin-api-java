package fr.devnr.jarialtekinapi.graphql.deserializer;

import fr.devnr.jarialtekinapi.graphql.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.model.Project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class ProjectDeserializerTest {

    @Test
    void DeserializeNull() {
        assertNull( ProjectDeserializer.deserialize(null) );
    }

    @Test
    void DeserializeTaskDTO() {
        // --{ ARRANGE & ACT }--
        Project project = ProjectDeserializer.deserialize(new ProjectDTO(1L, "P1", "A Project"));
        // --{ ASSERT }--
        assertEquals(Long.valueOf(1), project.getId());
        assertEquals("P1", project.getName());
        assertEquals("A Project", project.getDescription());
    }

}
