package fr.devnr.jarialtekinapi.graphql.serializer;

import fr.devnr.jarialtekinapi.graphql.dto.PriorityDTO;
import fr.devnr.jarialtekinapi.model.Priority;


public class PrioritySerializer {

    public static PriorityDTO serialize(Priority priority) {
        if (priority != null) {
            return new PriorityDTO(priority.getValue(), priority.toString());
        }
        return null;
    }

}
