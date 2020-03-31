package fr.devnr.jarialtekinapi.graphql.serializer;

import fr.devnr.jarialtekinapi.graphql.dto.StatusDTO;
import fr.devnr.jarialtekinapi.model.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class StatusSerializerTest {

    @Test
    void MapToNull() {
        assertNull( StatusSerializer.serialize(null) );
    }

    @Test
    void SerializeStatus() {
        // --{ ACT }--
        StatusDTO dto = StatusSerializer.serialize( Status.TODO );
        // --{ ASSERT }--
        assertEquals(Integer.valueOf(0), dto.id);
        assertEquals("TODO", dto.label);
    }

}
