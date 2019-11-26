package fr.devnr.jarialtekinapi.graphql.serializer;

import fr.devnr.jarialtekinapi.graphql.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.model.Project;


public class ProjectSerializer {

    public static ProjectDTO serialize(Project project) {
        if (project != null) {
            return new ProjectDTO(project.getId(), project.getName(), project.getDescription());
        }
        return null;
    }

}
