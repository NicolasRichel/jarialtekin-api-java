package fr.devnr.jarialtekinapi.graphql.serializer;

import fr.devnr.jarialtekinapi.graphql.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.model.Project;
import fr.devnr.jarialtekinapi.model.Task;
import fr.devnr.jarialtekinapi.model.TaskPlanning;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class PeriodSerializerTest {

    @Test
    void MapToNull() {
        assertNull( PeriodSerializer.serialize((TaskPlanning) null) );
        assertNull( PeriodSerializer.serialize((Project) null) );
    }

    @Test
    void SerializeTaskPlanning() {
        // --{ ARRANGE }--
        TaskPlanning planning = new TaskPlanning(
            new Task(1L, "T1"),
            LocalDateTime.of(2019, 11, 3, 10, 0),
            LocalDateTime.of(2019, 11, 4, 15, 30)
        );
        // --{ ACT }--
        PeriodDTO dto = PeriodSerializer.serialize( planning );
        // --{ ASSERT }--
        assertEquals("2019-11-03T10:00", dto.start);
        assertEquals("2019-11-04T15:30", dto.end);
    }

    @Test
    void SerializeProject() {
        // --{ ARRANGE }--
        Project project = new Project(
            1L, "Project", "A good project !",
            LocalDate.of(2019, 11, 1),
            LocalDate.of(2019, 11, 2)
        );
        // --{ ACT }--
        PeriodDTO dto = PeriodSerializer.serialize( project );
        // --{ ASSERT }--
        assertEquals("2019-11-01", dto.start);
        assertEquals("2019-11-02", dto.end);
    }

}
