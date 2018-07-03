package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.dto.TaskDTO;
import fr.devnr.jarialtekinapi.service.ProjectService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectQueryTest {

    private ProjectService projectService = mock(ProjectService.class);


    @Test
    void GetAllProjects() {
        // --{ ARRANGE }--
        List<ProjectDTO> projects = Arrays.asList(
            new ProjectDTO(1L, "P1", "Description"),
            new ProjectDTO(2L, "P2", ""),
            new ProjectDTO(3L, "P3", "")
        );
        when(projectService.getAllProjectsDTO()).thenReturn(projects);
        Query query = new Query(null, projectService);

        // --{ ACT }--
        List<ProjectDTO> result = query.allProjects();

        // --{ ASSERT }--
        assertEquals(3, result.size());
        ProjectDTO project = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), project.getId()),
            () -> assertEquals("P1", project.getName()),
            () -> assertEquals("Description", project.getDescription())
        );
        verify(projectService, times(1)).getAllProjectsDTO();
    }

    @Test
    void GetProject() {
        // --{ ARRANGE }--
        ProjectDTO task = new ProjectDTO(1L, "P1", "Description");
        when(projectService.getProjectDTO(eq(1L))).thenReturn(task);
        Query query = new Query(null, projectService);

        // --{ ACT }--
        ProjectDTO result = query.project(1L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(1), result.getId()),
            () -> assertEquals("P1", result.getName()),
            () -> assertEquals("Description", result.getDescription())
        );
        verify(projectService, times(1)).getProjectDTO(eq(1L));
    }

    @Test
    void GetTaskProject() {
        // --{ ARRANGE }--
        ProjectDTO project = new ProjectDTO(1L, "P1", "Description");
        when(projectService.getProjectByTaskDTO(eq(5L))).thenReturn(project);
        Query query = new Query(null, projectService);

        // --{ ACT }--
        ProjectDTO result = query.taskProject(5L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(1), result.getId()),
            () -> assertEquals("P1", result.getName()),
            () -> assertEquals("Description", result.getDescription())
        );
        verify(projectService, times(1)).getProjectByTaskDTO(eq(5L));
    }

    @Test
    void GetProjectTasksDTO() {
        // --{ ARRANGE }--
        List<TaskDTO> tasks = Arrays.asList(
            new TaskDTO(1L, "T1", "Description"),
            new TaskDTO(2L, "T2", ""),
            new TaskDTO(3L, "T3", "")
        );
        when(projectService.getProjectTasksDTO(eq(1L))).thenReturn(tasks);
        Query query = new Query(null, projectService);

        // --{ ACT }--
        List<TaskDTO> result = query.projectTasks(1L);

        // --{ ASSERT }--
        assertEquals(3, result.size());
        TaskDTO task = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), task.getId()),
            () -> assertEquals("T1", task.getName()),
            () -> assertEquals("Description", task.getDescription())
        );
        verify(projectService, times(1)).getProjectTasksDTO(eq(1L));
    }

}
