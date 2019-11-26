package fr.devnr.jarialtekinapi.graphql.serializer;

import fr.devnr.jarialtekinapi.graphql.dto.StatusDTO;
import fr.devnr.jarialtekinapi.model.Status;


public class StatusSerializer {

    public static StatusDTO serialize(Status status) {
        if (status != null) {
            return new StatusDTO(status.getValue(), status.toString());
        }
        return null;
    }

}
