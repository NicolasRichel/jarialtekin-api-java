package fr.devnr.jarialtekinapi.dao;

import fr.devnr.jarialtekinapi.dao.factory.DAOFactory;
import fr.devnr.jarialtekinapi.dao.factory.DataSourceFactory;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO;
import fr.devnr.jarialtekinapi.db.DBTestsUtils;
import fr.devnr.jarialtekinapi.model.Task;
import fr.devnr.jarialtekinapi.model.TaskPlanning;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TaskPlanningDAOTest {

    private static final DataSource source = DataSourceFactory.getDataSourceFactory().getDataSource();

    private static final DAOFactory factory = DAOFactory.getDAOFactory();

    private static final String SQL_TestData = "src/test/resources/sql/data_TaskPlanningDAOTest.sql";


    /**
     * Initialize DB data at the beginning of each test.
     */
    @BeforeEach
    void init() {
        DBTestsUtils.initializeData(source, SQL_TestData);
    }

    /**
     * Empty all tables in DB when all tests are done.
     */
    @AfterAll
    static void finalizeAll() {
        DBTestsUtils.emptyTables(source);
    }


    @Test
    void GetAllTaskPlannings() {
        // --{ ARRANGE }--
        TaskPlanningDAO dao = factory.getTaskPlanningDAO(source);

        // --{ ACT }--
        List<TaskPlanning> plannings = dao.getAllTaskPlannings();

        // --{ ASSERT }--
        assertEquals(3, plannings.size());
        TaskPlanning planning = plannings.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), planning.getTask().getId()),
            () -> assertEquals("T1", planning.getTask().getName()),
            () -> assertEquals(LocalDate.of(2018, 1, 1), planning.getStartDate()),
            () -> assertEquals(LocalTime.of(10, 15, 0), planning.getStartTime()),
            () -> assertEquals(LocalDate.of(2018, 1, 1), planning.getEndDate()),
            () -> assertEquals(LocalTime.of(11, 52, 0), planning.getEndTime())
        );
    }

    @Test
    void GetTaskPlanningByTask() {
        // --{ ARRANGE }--
        TaskPlanningDAO dao = factory.getTaskPlanningDAO(source);

        // --{ ACT }--
        TaskPlanning planning = dao.getTaskPlanningByTask(2L);

        // --{ ASSERT }--
        assertAll(
            () -> assertEquals(Long.valueOf(2), planning.getTask().getId()),
            () -> assertEquals("T2", planning.getTask().getName()),
            () -> assertEquals(LocalDate.of(2018, 1, 1), planning.getStartDate()),
            () -> assertEquals(LocalTime.of(13, 30, 0), planning.getStartTime()),
            () -> assertEquals(LocalDate.of(2018, 1, 2), planning.getEndDate()),
            () -> assertEquals(LocalTime.of(12, 0, 0), planning.getEndTime())
        );
    }

    @Test
    void GetTaskPlanningsByPeriod() {
        // --{ ARRANGE }--
        TaskPlanningDAO dao = factory.getTaskPlanningDAO(source);
        LocalDateTime start = LocalDateTime.of(
            LocalDate.of(2018, 1, 1),
            LocalTime.of(8, 0, 0)
        );
        LocalDateTime end = LocalDateTime.of(
            LocalDate.of(2018, 1, 2),
            LocalTime.of(11, 0, 0)
        );

        // --{ ACT }--
        List<TaskPlanning> result = dao.getTaskPlanningsByPeriod(start, end);

        // --{ ASSERT }--
        assertEquals(2, result.size());
        TaskPlanning planning = result.get(0);
        assertAll(
            () -> assertEquals(Long.valueOf(1), planning.getTask().getId()),
            () -> assertEquals(LocalDate.of(2018, 1, 1), planning.getStartDate()),
            () -> assertEquals(LocalTime.of(10, 15, 0), planning.getStartTime()),
            () -> assertEquals(LocalDate.of(2018, 1, 1), planning.getEndDate()),
            () -> assertEquals(LocalTime.of(11, 52, 0), planning.getEndTime())
        );
    }

    @Test
    void CreateTaskPlanning() {
        // --{ ARRANGE }--
        TaskPlanningDAO dao = factory.getTaskPlanningDAO(source);
        LocalDateTime start = LocalDateTime.of(LocalDate.of(2018, 1, 3), LocalTime.of(9, 30, 0));
        LocalDateTime end = LocalDateTime.of(LocalDate.of(2018, 1, 3), LocalTime.of(11, 0, 0));
        TaskPlanning planning = new TaskPlanning(new Task(4L, "T4"), start, end);

        // --{ ACT }--
        TaskPlanning createdPlanning = dao.createTaskPlanning(planning);
        List<TaskPlanning> plannings = dao.getAllTaskPlannings();

        // --{ ASSERT }--
        assertEquals(4, plannings.size());
        assertAll(
            () -> assertEquals(Long.valueOf(4), createdPlanning.getTask().getId()),
            () -> assertEquals("T4", createdPlanning.getTask().getName()),
            () -> assertEquals(LocalDate.of(2018, 1, 3), createdPlanning.getStartDate()),
            () -> assertEquals(LocalTime.of(11, 0, 0), createdPlanning.getEndTime())
        );
    }

    @Test
    void UpdateTaskPlanning() {
        // --{ ARRANGE }--
        TaskPlanningDAO dao = factory.getTaskPlanningDAO(source);
        LocalDateTime start = LocalDateTime.of(LocalDate.of(2018, 1, 3), LocalTime.of(9, 30, 0));
        LocalDateTime end = LocalDateTime.of(LocalDate.of(2018, 1, 3), LocalTime.of(11, 0, 0));
        TaskPlanning planning = new TaskPlanning(new Task(3L, "T3"), start, end);

        // --{ ACT }--
        Boolean isUpdated = dao.updateTaskPlanning(planning);
        TaskPlanning updatedPlanning = dao.getTaskPlanningByTask(3L);

        // --{ ASSERT }--
        assertTrue(isUpdated);
        assertAll(
            () -> assertEquals(Long.valueOf(3), updatedPlanning.getTask().getId()),
            () -> assertEquals(LocalDate.of(2018, 1, 3), updatedPlanning.getStartDate()),
            () -> assertEquals(LocalTime.of(11, 0, 0), updatedPlanning.getEndTime())
        );
    }

    @Test
    void DeleteTaskPlanning() {
        // --{ ARRANGE }--
        TaskPlanningDAO dao = factory.getTaskPlanningDAO(source);

        // --{ ACT }--
        Boolean isDeleted = dao.deleteTaskPlanning(3L);
        TaskPlanning deletedPlanning = dao.getTaskPlanningByTask(3L);
        List<TaskPlanning> plannings = dao.getAllTaskPlannings();

        // --{ ASSERT }--
        assertEquals(2, plannings.size());
        assertTrue(isDeleted);
        assertNull(deletedPlanning);
    }

}
