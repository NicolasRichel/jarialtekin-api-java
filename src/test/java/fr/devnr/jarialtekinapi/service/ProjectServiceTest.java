package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.dao.interfaces.ProjectDAO;
import fr.devnr.jarialtekinapi.model.Project;
import fr.devnr.jarialtekinapi.model.Task;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ProjectServiceTest {

    private ProjectDAO projectDAO = mock(ProjectDAO.class);


    @Test
    void GetAllProjects() {
        // --{ ARRANGE }--
        List<Project> projects = Arrays.asList(
            new Project(1L, "P1"),
            new Project(2L, "P2"),
            new Project(3L, "P3")
        );
        when(projectDAO.getAllProjects()).thenReturn(projects);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        List<Project> result = service.getAllProjects();

        // --{ ASSERT }--
        assertEquals(3, result.size());
        Project project = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), project.getId()),
            () -> assertEquals("P1", project.getName())
        );
        verify(projectDAO, times(1)).getAllProjects();
    }

    @Test
    void GetProject() {
        // --{ ARRANGE }--
        Project project = new Project(1L, "P1");
        project.setDescription("Description du projet.");
        when(projectDAO.getProjectById(eq(1L))).thenReturn(project);
        when(projectDAO.getProjectById(eq(2L))).thenReturn(null);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        Project result1 = service.getProject(1L);
        Project result2 = service.getProject(2L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(1), result1.getId()),
            () -> assertEquals("P1", result1.getName()),
            () -> assertEquals("Description du projet.", result1.getDescription())
        );
        verify(projectDAO, times(1)).getProjectById(eq(1L));
        assertNull(result2);
        verify(projectDAO, times(1)).getProjectById(eq(2L));
    }

    @Test
    void GetProjectByTask() {
        // --{ ARRANGE }--
        Project project = new Project(100L, "P1");
        when(projectDAO.getProjectByTask(eq(1L))).thenReturn(project);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        Project result1 = service.getProjectByTask(1L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(100), result1.getId()),
            () -> assertEquals("P1", result1.getName())
        );
        verify(projectDAO, times(1)).getProjectByTask(eq(1L));
    }

    @Test
    void GetProjectTasks() {
        // --{ ARRANGE }--
        List<Task> tasks = Arrays.asList(
            new Task(1L, "T1"),
            new Task(2L, "T2"),
            new Task(3L, "T3")
        );
        when(projectDAO.getProjectTasks(eq(1L))).thenReturn(tasks);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        List<Task> result = service.getProjectTasks(1L);

        // --{ ASSERT }--
        assertEquals(3, result.size());
        Task task = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), task.getId()),
            () -> assertEquals("T1", task.getName())
        );
        verify(projectDAO, times(1)).getProjectTasks(eq(1L));
    }

    @Test
    void CreateProject() {
        // --{ ARRANGE }--
        Project project = new Project(21L, "Project");
        project.setDescription("Description");
        when(projectDAO.createProject(any(Project.class))).thenReturn(project);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        Project result = service.createProject(project);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(21), result.getId()),
            () -> assertEquals("Project", result.getName()),
            () -> assertEquals("Description", result.getDescription())
        );
        verify(projectDAO, times(1)).createProject(any(Project.class));
    }

    @Test
    void UpdateProject() {
        // --{ ARRANGE }--
        Project project = new Project(22L, "P22");
        when(projectDAO.getProjectById(eq(22L))).thenReturn(project);
        when(projectDAO.updateProject(any(Project.class))).thenReturn(Boolean.TRUE);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        Boolean result = service.updateProject(project);

        // --{ ASSERT }--
        assertTrue(result);
        verify(projectDAO, times(1)).getProjectById(eq(22L));
        verify(projectDAO, times(1)).updateProject(any(Project.class));
        verifyNoMoreInteractions(projectDAO);
    }

    @Test
    void DeleteProject() {
        // --{ ARRANGE }--
        when(projectDAO.deleteProject(eq(23L))).thenReturn(Boolean.TRUE);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        Boolean result = service.deleteProject(23L);

        // --{ ASSERT }--
        assertTrue(result);
        verify(projectDAO, times(1)).deleteProject(eq(23L));
    }

}
