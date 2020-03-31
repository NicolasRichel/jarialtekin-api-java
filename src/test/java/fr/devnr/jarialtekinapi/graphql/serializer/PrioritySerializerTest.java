package fr.devnr.jarialtekinapi.graphql.serializer;

import fr.devnr.jarialtekinapi.graphql.dto.PriorityDTO;
import fr.devnr.jarialtekinapi.model.Priority;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class PrioritySerializerTest {

    @Test
    void MapToNull() {
        assertNull( PrioritySerializer.serialize(null) );
    }

    @Test
    void SerializePriority() {
        // --{ ACT }--
        PriorityDTO dto = PrioritySerializer.serialize( Priority.HIGH );
        // --{ ASSERT }--
        assertEquals(Integer.valueOf(1), dto.id);
        assertEquals("HIGH", dto.label);
    }

}
