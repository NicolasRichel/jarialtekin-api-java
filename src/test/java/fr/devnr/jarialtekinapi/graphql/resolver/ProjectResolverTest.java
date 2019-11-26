package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.graphql.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.graphql.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.model.Project;
import fr.devnr.jarialtekinapi.service.ProjectService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class ProjectResolverTest {

    private ProjectService projectService = mock(ProjectService.class);


    @Test
    void GetDates() {
        // --{ ARRANGE }--
        Project p = new Project(1L, "P1");
        p.setStartDate( LocalDate.of(2019, 11, 1) );
        p.setEndDate( LocalDate.of(2019, 11, 2) );
        when(projectService.getProject(eq(1L))).thenReturn(p);
        ProjectResolver projectResolver = new ProjectResolver(projectService);

        // --{ ACT }--
        ProjectDTO project = new ProjectDTO(1L, "P1", "");
        PeriodDTO result = projectResolver.dates(project);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals("2019-11-01", result.start),
            () -> assertEquals("2019-11-02", result.end)
        );
        verify(projectService, times(1)).getProject(eq(1L));
    }

}
