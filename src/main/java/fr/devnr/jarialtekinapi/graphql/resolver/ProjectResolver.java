package fr.devnr.jarialtekinapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import fr.devnr.jarialtekinapi.graphql.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.graphql.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.graphql.serializer.PeriodSerializer;
import fr.devnr.jarialtekinapi.service.ProjectService;


public class ProjectResolver implements GraphQLResolver<ProjectDTO> {

    private ProjectService projectService;


    public ProjectResolver(ProjectService projectService) {
        this.projectService = projectService;
    }


    public PeriodDTO dates(ProjectDTO project) {
        return PeriodSerializer.serialize( projectService.getProject( project.id ) );
    }

}
