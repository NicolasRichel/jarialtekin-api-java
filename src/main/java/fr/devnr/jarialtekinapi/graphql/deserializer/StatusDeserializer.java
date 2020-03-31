package fr.devnr.jarialtekinapi.graphql.deserializer;

import fr.devnr.jarialtekinapi.graphql.dto.StatusDTO;
import fr.devnr.jarialtekinapi.model.Status;


public class StatusDeserializer {

    public static Status deserialize(StatusDTO dto) {
        if (dto != null) {
            return Status.valueOf(dto.id);
        }
        return null;
    }

}
