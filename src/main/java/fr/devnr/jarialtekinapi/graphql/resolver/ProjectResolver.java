package fr.devnr.jarialtekinapi.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import fr.devnr.jarialtekinapi.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.service.ProjectService;

public class ProjectResolver implements GraphQLResolver<ProjectDTO> {

    private ProjectService projectService;

    //  Constructors
    // ==============

    public ProjectResolver(ProjectService projectService) {
        this.projectService = projectService;
    }


    // ================
    //  Public Methods
    // ================

    public PeriodDTO dates(ProjectDTO project) {
        return projectService.getPeriodDTO( project.getId() );
    }

}
