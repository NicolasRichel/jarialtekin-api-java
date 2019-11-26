package fr.devnr.jarialtekinapi.graphql.deserializer;

import fr.devnr.jarialtekinapi.graphql.dto.PeriodDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class PeriodDeserializer {

    public static Map<String, LocalDateTime> deserialize(PeriodDTO dto) {
        if (dto != null) {
            Map<String, LocalDateTime> period = new HashMap<>();
            if ( !dto.start.contains("T") ) dto.start += "T00:00:00";
            period.put("start", LocalDateTime.parse(dto.start));
            if ( !dto.end.contains("T") ) dto.end += "T00:00:00";
            period.put("end", LocalDateTime.parse(dto.end));
            return period;
        }
        return null;
    }

}
