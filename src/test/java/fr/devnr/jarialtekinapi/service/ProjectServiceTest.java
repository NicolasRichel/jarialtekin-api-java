package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.dao.interfaces.ProjectDAO;
import fr.devnr.jarialtekinapi.model.Project;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectServiceTest {

    private ProjectDAO projectDAO = mock(ProjectDAO.class);

    // TODO : create class ProjectService
    // TODO : create class ProjectDTO

    @Test
    void GetAllProjectsDTO() {
        // TODO : create method 'getAllProjectsDTO' in ProjectService
        /*// --{ ARRANGE }--
        List<Project> projects = Arrays.asList(
            new Project(1L, "P1"),
            new Project(2L, "P2"),
            new Project(3L, "P3")
        );
        when(projectDAO.getAllProjects()).thenReturn(projects);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        List<ProjectDto> result = service.getAllProjectsDTO();

        // --{ ASSERT }--
        assertEquals(3, result.size());
        ProjectDTO project = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), project.getId()),
            () -> assertEquals("P1", project.getName())
        );
        verify(projectDAO, times(1)).getAllProjects();*/
    }

    @Test
    void GetProjectDTO() {
        // TODO : create method 'getProjectDTO' in ProjectService
        /*// --{ ARRANGE }--
        Project project = new Project(1L, "P1");
        project.setDescription("Description du projet.");
        when(projectDAO.getProjectById(eq(1L))).thenReturn(project);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        ProjectDTO result = service.getProjectDTO(1L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(1), result.getId()),
            () -> assertEquals("P1", result.getName()),
            () -> assertEquals("Description du projet.", result.getDescription())
        );
        verify(projectDAO, times(1)).getProjectById(eq(1L));*/
    }

    @Test
    void GetProjectByTaskDTO() {
        // TODO : create method 'getProjectByTaskDTO' in ProjectService
        /*// --{ ARRANGE }--
        Project project = new Project(100L, "P1");
        when(projectDAO.getProjectByTask(eq(1L))).thenReturn(project);
        TaskService service = new ProjectService(projectDAO);

        // --{ ACT }--
        ProjectDto result = service.getProjectByTaskDTO(1L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(100), result.getId()),
            () -> assertEquals("P1", result.getName())
        );
        verify(projectDAO, times(1)).getProjectByTask(eq(1L));*/
    }

    @Test
    void GetProjectTasksDTO() {
        // TODO : create method 'getProjectTasksDTO' in ProjectService
        // TODO : write test for method 'getProjectTasksDTO'
        fail("Not implemented yet.");
    }

}
