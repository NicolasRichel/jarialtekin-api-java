package fr.devnr.jarialtekinapi.graphql.deserializer;

import fr.devnr.jarialtekinapi.graphql.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.model.Project;


public class ProjectDeserializer {

    public static Project deserialize(ProjectDTO dto) {
        if (dto != null) {
            Project project = new Project(dto.id, dto.name);
            project.setDescription(dto.description);
            return project;
        }
        return null;
    }

}
