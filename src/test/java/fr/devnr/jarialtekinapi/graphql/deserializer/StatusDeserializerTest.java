package fr.devnr.jarialtekinapi.graphql.deserializer;

import fr.devnr.jarialtekinapi.graphql.dto.StatusDTO;
import fr.devnr.jarialtekinapi.model.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class StatusDeserializerTest {

    @Test
    void DeserializeNull() {
        assertNull( StatusDeserializer.deserialize(null) );
    }

    @Test
    void DeserializeStatusDTO() {
        // --{ ARRANGE & ACT }--
        Status status = StatusDeserializer.deserialize(new StatusDTO(1, "Status"));
        // --{ ASSERT }--
        assertEquals(Integer.valueOf(1), status.getValue());
    }

}
