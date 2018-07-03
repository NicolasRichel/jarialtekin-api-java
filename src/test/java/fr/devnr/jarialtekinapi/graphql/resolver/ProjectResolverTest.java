package fr.devnr.jarialtekinapi.graphql.resolver;

import fr.devnr.jarialtekinapi.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.dto.ProjectDTO;
import fr.devnr.jarialtekinapi.service.ProjectService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectResolverTest {

    private ProjectService projectService = mock(ProjectService.class);


    @Test
    void GetDates() {
        // --{ ARRANGE }--
        PeriodDTO period = new PeriodDTO("2018-02-25T08:00:00", "2019-02-25T20:00:00");
        when(projectService.getPeriodDTO(eq(1L))).thenReturn(period);
        ProjectResolver projectResolver = new ProjectResolver(projectService);

        // --{ ACT }--
        ProjectDTO project = new ProjectDTO(1L, "P1", "");
        PeriodDTO result = projectResolver.dates(project);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals("2018-02-25T08:00:00", result.getStart()),
            () -> assertEquals("2019-02-25T20:00:00", result.getEnd())
        );
        verify(projectService, times(1)).getPeriodDTO(eq(1L));
    }

}
