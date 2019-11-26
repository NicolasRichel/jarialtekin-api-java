package fr.devnr.jarialtekinapi.graphql.deserializer;

import fr.devnr.jarialtekinapi.graphql.dto.PriorityDTO;
import fr.devnr.jarialtekinapi.model.Priority;


public class PriorityDeserializer {

    public static Priority deserialize(PriorityDTO dto) {
        if (dto != null) {
            return Priority.valueOf(dto.index);
        }
        return null;
    }

}
