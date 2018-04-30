package fr.devnr.jarialtekinapi.dao;

import fr.devnr.jarialtekinapi.dao.factory.DAOFactory;
import fr.devnr.jarialtekinapi.dao.factory.DAOFactoryTest;
import fr.devnr.jarialtekinapi.dao.factory.DataSourceFactory;
import fr.devnr.jarialtekinapi.dao.interfaces.ProjectDAO;
import fr.devnr.jarialtekinapi.db.DBTestsUtils;
import fr.devnr.jarialtekinapi.model.Project;
import fr.devnr.jarialtekinapi.model.Task;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectDAOTest {

    private static final DataSource source = DataSourceFactory.getMariadbDataSource("/testdb.properties");

    private static final DAOFactory factory = new DAOFactoryTest();

    private static final String SQL_TestData = "src/test/resources/sql/data_ProjectDAOTest.sql";


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
    void GetAllProjects() {
        // --{ ARRANGE }--
        ProjectDAO dao = factory.getProjectDAO();

        // --{ ACT }--
        List<Project> projects = dao.getAllProjects();

        // --{ ASSERT}--
        assertEquals(2, projects.size());
        Project project = projects.get(0);
        assertAll("project",
            () -> assertEquals(Long.valueOf(1), project.getId()),
            () -> assertEquals("P1", project.getName()),
            () -> assertEquals("Le projet 1", project.getDescription()),
            () -> assertEquals(LocalDate.of(2018, 01, 01), project.getStartDate()),
            () -> assertEquals(LocalDate.of(2018, 01, 10), project.getEndDate())
        );
    }

    @Test
    void GetProjectById() {
        // --{ ARRANGE }--
        ProjectDAO dao = factory.getProjectDAO();

        // --{ ACT }--
        Project project = dao.getProjectById(1L);

        // --{ ASSERT }--
        assertAll("project",
            () -> assertEquals(Long.valueOf(1), project.getId()),
            () -> assertEquals("P1", project.getName()),
            () -> assertEquals("Le projet 1", project.getDescription()),
            () -> assertEquals(LocalDate.of(2018, 01, 01), project.getStartDate()),
            () -> assertEquals(LocalDate.of(2018, 01, 10), project.getEndDate())
        );
    }

    @Test
    void CreateProject() {
        // --{ ARRANGE }--
        ProjectDAO dao = factory.getProjectDAO();
        Project project = new Project(null, "P3", "Description du projet", LocalDate.now(), LocalDate.now().plusMonths(1));

        // --{ ACT }--
        Project createdProject = dao.createProject(project);
        List<Project> projects = dao.getAllProjects();

        // --{ ASSERT }--
        assertEquals(3, projects.size());
        assertAll("project",
            () -> assertEquals("P3", createdProject.getName()),
            () -> assertEquals("Description du projet", createdProject.getDescription()),
            () -> assertEquals(LocalDate.now(), project.getStartDate()),
            () -> assertEquals(LocalDate.now().plusMonths(1), createdProject.getEndDate())
        );
    }

    @Test
    void UpdateProject() {
        // --{ ARRANGE }--
        ProjectDAO dao = factory.getProjectDAO();
        Project project = new Project(1L, "P1", "Description du projet", LocalDate.now(), LocalDate.now().plusMonths(1));

        // --{ ACT }--
        Boolean isUpdated = dao.updateProject(project);
        Project updatedProject = dao.getProjectById(1L);

        // --{ ASSERT }--
        assertTrue(isUpdated);
        assertAll("project",
            () -> assertEquals("P1", updatedProject.getName()),
            () -> assertEquals("Description du projet", updatedProject.getDescription()),
            () -> assertEquals(LocalDate.now(), project.getStartDate()),
            () -> assertEquals(LocalDate.now().plusMonths(1), updatedProject.getEndDate())
        );
    }

    @Test
    void DeleteProject() {
        // --{ ARRANGE }--
        ProjectDAO dao = factory.getProjectDAO();

        // --{ ACT }--
        Boolean isDeleted = dao.deleteProject(2L);
        Project project = dao.getProjectById(2L);
        List<Project> projects = dao.getAllProjects();

        // --{ ASSERT }--
        assertEquals(1, projects.size());
        assertTrue(isDeleted);
        assertNull(project);
    }

    @Test
    void GetProjectTasks() {
        // --{ ARRANGE }--
        ProjectDAO dao = factory.getProjectDAO();

        // --{ ACT }--
        List<Task> tasks = dao.getProjectTasks(1L);

        // --{ ASSERT }--
        assertEquals(3, tasks.size());
        Task task = tasks.get(0);
        assertAll("task",
            () -> assertEquals(Long.valueOf(1), task.getId()),
            () -> assertEquals("T1", task.getName())
        );
    }

}
