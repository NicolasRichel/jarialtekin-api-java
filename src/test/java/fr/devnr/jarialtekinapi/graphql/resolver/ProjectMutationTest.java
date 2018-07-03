package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.service.ProjectService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectMutationTest {

    private ProjectService projectService = mock(ProjectService.class);


    @Test
    void CreateProject() {
        // --{ ARRANGE }--
        ProjectDTO task = new ProjectDTO(21L, "Project", "Description");
        when(projectService.createProject(any(ProjectDTO.class))).thenReturn(task);
        Mutation mutation = new Mutation(null, projectService);

        // --{ ACT }--
        ProjectDTO input = new ProjectDTO(null, "Project", "Description");
        ProjectDTO result = mutation.createProject(input);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(21), result.getId()),
            () -> assertEquals("Project", result.getName()),
            () -> assertEquals("Description", result.getDescription())
        );
        verify(projectService, times(1)).createProject(any(ProjectDTO.class));
    }

    @Test
    void UpdateProject() {
        // --{ ARRANGE }--
        when(projectService.updateProject(any(ProjectDTO.class))).thenReturn(Boolean.TRUE);
        Mutation mutation = new Mutation(null, projectService);

        // --{ ACT }--
        ProjectDTO input = new ProjectDTO(22L, "T22", "");
        Boolean result = mutation.updateProject(input);

        // --{ ASSERT }--
        assertTrue(result);
        verify(projectService, times(1)).updateProject(any(ProjectDTO.class));
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
