package fr.devnr.jarialtekinapi.graphql.deserializer;

import fr.devnr.jarialtekinapi.graphql.dto.TaskDTO;
import fr.devnr.jarialtekinapi.model.Task;


public class TaskDeserializer {

    public static Task deserialize(TaskDTO dto) {
        if (dto != null) {
            Task task = new Task(dto.id, dto.name);
            task.setDescription(dto.description);
            return task;
        }
        return null;
    }

}
