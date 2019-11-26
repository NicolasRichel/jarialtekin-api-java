package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.graphql.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.model.Project;
import fr.devnr.jarialtekinapi.service.ProjectService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class ProjectMutationTest {

    private ProjectService projectService = mock(ProjectService.class);


    @Test
    void CreateProject() {
        // --{ ARRANGE }--
        Project project = new Project(21L, "ProjectModel");
        when(projectService.createProject(any(Project.class))).thenReturn(project);
        Mutation mutation = new Mutation(null, projectService);

        // --{ ACT }--
        ProjectDTO input = new ProjectDTO(null, "Project", "Description");
        ProjectDTO result = mutation.createProject(input);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(21), result.id),
            () -> assertEquals("ProjectModel", result.name)
        );
        verify(projectService, times(1)).createProject(any(Project.class));
    }

    @Test
    void UpdateProject() {
        // --{ ARRANGE }--
        when(projectService.updateProject(any(Project.class))).thenReturn(Boolean.TRUE);
        Mutation mutation = new Mutation(null, projectService);

        // --{ ACT }--
        ProjectDTO input = new ProjectDTO(22L, "T22", "");
        Boolean result = mutation.updateProject(input);

        // --{ ASSERT }--
        assertTrue(result);
        verify(projectService, times(1)).updateProject(any(Project.class));
    }

    @Test
    void DeleteProject() {
        // --{ ARRANGE }--
        when(projectService.deleteProject(eq(23L))).thenReturn(Boolean.TRUE);
        Mutation mutation = new Mutation(null, projectService);

        // --{ ACT }--
        Boolean result = mutation.deleteProject(23L);

        // --{ ASSERT }--
        assertTrue(result);
        verify(projectService, times(1)).deleteProject(eq(23L));
    }

}
