package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.graphql.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.graphql.dto.TaskDTO;
import fr.devnr.jarialtekinapi.model.Project;
import fr.devnr.jarialtekinapi.model.Task;
import fr.devnr.jarialtekinapi.service.ProjectService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class ProjectQueryTest {

    private ProjectService projectService = mock(ProjectService.class);


    @Test
    void GetAllProjects() {
        // --{ ARRANGE }--
        List<Project> projects = Arrays.asList(
            new Project(1L, "P1"),
            new Project(2L, "P2"),
            new Project(3L, "P3")
        );
        when(projectService.getAllProjects()).thenReturn(projects);
        Query query = new Query(null, projectService, null, null);

        // --{ ACT }--
        List<ProjectDTO> result = query.allProjects();

        // --{ ASSERT }--
        assertEquals(3, result.size());
        ProjectDTO project = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), project.id),
            () -> assertEquals("P1", project.name)
        );
        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    void GetProject() {
        // --{ ARRANGE }--
        Project project = new Project(1L, "P1");
        when(projectService.getProject(eq(1L))).thenReturn(project);
        Query query = new Query(null, projectService, null, null);

        // --{ ACT }--
        ProjectDTO result = query.project(1L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(1), result.id),
            () -> assertEquals("P1", result.name)
        );
        verify(projectService, times(1)).getProject(eq(1L));
    }

    @Test
    void GetTaskProject() {
        // --{ ARRANGE }--
        Project project = new Project(1L, "P1");
        when(projectService.getProjectByTask(eq(5L))).thenReturn(project);
        Query query = new Query(null, projectService, null, null);

        // --{ ACT }--
        ProjectDTO result = query.taskProject(5L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(1), result.id),
            () -> assertEquals("P1", result.name)
        );
        verify(projectService, times(1)).getProjectByTask(eq(5L));
    }

    @Test
    void GetProjectTasksDTO() {
        // --{ ARRANGE }--
        List<Task> tasks = Arrays.asList(
            new Task(1L, "T1"),
            new Task(2L, "T2"),
            new Task(3L, "T3")
        );
        when(projectService.getProjectTasks(eq(1L))).thenReturn(tasks);
        Query query = new Query(null, projectService, null, null);

        // --{ ACT }--
        List<TaskDTO> result = query.projectTasks(1L);

        // --{ ASSERT }--
        assertEquals(3, result.size());
        TaskDTO task = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), task.id),
            () -> assertEquals("T1", task.name)
        );
        verify(projectService, times(1)).getProjectTasks(eq(1L));
    }

}
