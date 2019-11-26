package fr.devnr.jarialtekinapi.graphql.serializer;

import fr.devnr.jarialtekinapi.graphql.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.model.Project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ProjectSerializerTest {

    @Test
    void MapToNull() {
        assertNull(  ProjectSerializer.serialize(null) );
    }

    @Test
    void SerializeProject() {
        // --{ ACT }--
        ProjectDTO dto = ProjectSerializer.serialize( new Project(1L, "P1") );
        //  --{ ASSERT }--
        assertEquals(Long.valueOf(1L), dto.id);
        assertEquals("P1", dto.name);
    }

}
