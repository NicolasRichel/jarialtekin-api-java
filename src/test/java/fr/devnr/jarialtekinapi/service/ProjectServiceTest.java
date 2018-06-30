package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.dao.interfaces.ProjectDAO;
import fr.devnr.jarialtekinapi.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.dto.TaskDTO;
import fr.devnr.jarialtekinapi.model.Project;
import fr.devnr.jarialtekinapi.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectServiceTest {

    private ProjectDAO projectDAO = mock(ProjectDAO.class);


    @Test
    void GetAllProjectsDTO() {
        // --{ ARRANGE }--
        List<Project> projects = Arrays.asList(
            new Project(1L, "P1"),
            new Project(2L, "P2"),
            new Project(3L, "P3")
        );
        when(projectDAO.getAllProjects()).thenReturn(projects);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        List<ProjectDTO> result = service.getAllProjectsDTO();

        // --{ ASSERT }--
        assertEquals(3, result.size());
        ProjectDTO project = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), project.getId()),
            () -> assertEquals("P1", project.getName())
        );
        verify(projectDAO, times(1)).getAllProjects();
    }

    @Test
    void GetProjectDTO() {
        // --{ ARRANGE }--
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
        verify(projectDAO, times(1)).getProjectById(eq(1L));
    }

    @Test
    void GetProjectByTaskDTO() {
        // --{ ARRANGE }--
        Project project = new Project(100L, "P1");
        when(projectDAO.getProjectByTask(eq(1L))).thenReturn(project);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        ProjectDTO result = service.getProjectByTaskDTO(1L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(100), result.getId()),
            () -> assertEquals("P1", result.getName())
        );
        verify(projectDAO, times(1)).getProjectByTask(eq(1L));
    }

    @Test
    void GetProjectTasksDTO() {
        // --{ ARRANGE }--
        List<Task> tasks = Arrays.asList(
            new Task(1L, "T1"),
            new Task(2L, "T2"),
            new Task(3L, "T3")
        );
        when(projectDAO.getProjectTasks(eq(1L))).thenReturn(tasks);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        List<TaskDTO> result = service.getProjectTasksDTO(1L);

        // --{ ASSERT }--
        assertEquals(3, result.size());
        TaskDTO task = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), task.getId()),
            () -> assertEquals("T1", task.getName())
        );
        verify(projectDAO, times(1)).getProjectTasks(eq(1L));
    }

    @Test
    void GetPeriodDTO() {
        // --{ ARRANGE }--
        Project project = new Project(
            1L, "P1", "Description",
            LocalDate.of(2018, 5, 5),
            LocalDate.of(2018, 6, 6)
        );
        when(projectDAO.getProjectById(1L)).thenReturn(project);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        PeriodDTO result = service.getPeriodDTO(1L);

        // -- { ASSERT }--
        assertAll(
            () -> assertEquals("2018-05-05", result.getStart()),
            () -> assertEquals("2018-06-06", result.getEnd())
        );
        verify(projectDAO, times(1)).getProjectById(eq(1L));
    }

    @Test
    void CreateTask() {
        // --{ ARRANGE }--
        Project project = new Project(21L, "Project");
        project.setDescription("Description");
        when(projectDAO.createProject(any(Project.class))).thenReturn(project);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        ProjectDTO input = new ProjectDTO(null, "Project", "Description");
        ProjectDTO result = service.createProject(input);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(21), result.getId()),
            () -> assertEquals("Project", result.getName()),
            () -> assertEquals("Description", result.getDescription())
        );
        verify(projectDAO, times(1)).createProject(any(Project.class));
    }

    @Test
    void UpdateTask() {
        // --{ ARRANGE }--
        when(projectDAO.updateProject(any(Project.class))).thenReturn(Boolean.TRUE);
        ProjectService service = new ProjectService(projectDAO);

        // --{ ACT }--
        ProjectDTO input = new ProjectDTO(22L, "P22", "");
        Boolean result = service.updateProject(input);

        // --{ ASSERT }--
        assertTrue(result);
        verify(projectDAO, times(1)).updateProject(any(Project.class));
    }

    @Test
    void DeleteTask() {
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
