package fr.devnr.jarialtekinapi.graphql.deserializer;

import fr.devnr.jarialtekinapi.graphql.dto.PeriodDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class PeriodDeserializerTest {

    @Test
    void DeserializeNull() {
        assertNull( PeriodDeserializer.deserialize(null) );
    }

    @Test
    void DeserializePeriodDTO() {
        // --{ ARRANGE & ACT }--
        PeriodDTO dto = new PeriodDTO("2019-01-01", "2019-01-02T14:32:15");
        Map<String, LocalDateTime> period = PeriodDeserializer.deserialize(dto);
        // --{ ASSERT }--
        assertEquals(LocalDateTime.of(2019, 1, 1, 0, 0, 0), period.get("start"));
        assertEquals(LocalDateTime.of(2019, 1, 2, 14, 32, 15), period.get("end"));
    }

}
