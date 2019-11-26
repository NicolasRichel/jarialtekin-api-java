package fr.devnr.jarialtekinapi.graphql.deserializer;

import fr.devnr.jarialtekinapi.graphql.dto.PriorityDTO;
import fr.devnr.jarialtekinapi.model.Priority;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class PriorityDeserializerTest {

    @Test
    void DeserializeNull() {
        assertNull( PriorityDeserializer.deserialize(null) );
    }

    @Test
    void DeserializePriorityDTO() {
        // --{ ARRANGE & ACT }--
        Priority priority = PriorityDeserializer.deserialize(new PriorityDTO(1, "Priority"));
        // --{ ASSERT }--
        assertEquals(Integer.valueOf(1), priority.getValue());
    }

}
