package fr.devnr.jarialtekinapi.dao;

import fr.devnr.jarialtekinapi.dao.factory.DAOFactory;
import fr.devnr.jarialtekinapi.model.Priority;
import fr.devnr.jarialtekinapi.model.Status;
import fr.devnr.jarialtekinapi.dao.factory.DataSourceFactory;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO;
import fr.devnr.jarialtekinapi.db.DBTestsUtils;
import fr.devnr.jarialtekinapi.model.Task;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TaskDAOTest {

	private static final DataSource source = DataSourceFactory.getDataSourceFactory().getDataSource();

	private static final DAOFactory daoFactory = DAOFactory.getDAOFactory();
	
	private static final String SQL_TestData = "src/test/resources/sql/data_TaskDAOTest.sql";


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
	void GetAllTasks() {
		// --{ ARRANGE }--
		TaskDAO dao = daoFactory.getTaskDAO(source);
		
		// --{ ACT }--
		List<Task> tasks = dao.getAllTasks();
		
		// --{ ASSERT }--
		assertEquals(20, tasks.size());
		Task task = tasks.get(0);
		assertAll("task",
			() -> assertEquals(Long.valueOf(1), task.getId()),
			() -> assertEquals("T1", task.getName()),
			() -> assertEquals("Une tâche élémentaire", task.getDescription()),
			() -> assertEquals(Priority.NORMAL, task.getPriority()),
			() -> assertEquals(Status.TODO, task.getStatus())
		);
	}
	
	@Test
	void GetTaskById() {
		// --{ ARRANGE }--
		TaskDAO dao = daoFactory.getTaskDAO(source);

		// --{ ACT }--
		Task task = dao.getTaskById(1L);

		// --{ ASSERT }--
		assertAll("task",
			() -> assertEquals(Long.valueOf(1), task.getId()),
			() -> assertEquals("T1", task.getName()),
			() -> assertEquals("Une tâche élémentaire", task.getDescription()),
			() -> assertEquals(Priority.NORMAL, task.getPriority()),
			() -> assertEquals(Status.TODO, task.getStatus())
		);
	}
	
	@Test
	void GetParentTask() {
		// --{ ARRANGE }--
		TaskDAO dao = daoFactory.getTaskDAO(source);

		// --{ ACT }--
		Task task = dao.getParentTask(2L);

		// --{ ASSERT }--
		assertAll("task",
			() -> assertEquals(Long.valueOf(1), task.getId()),
			() -> assertEquals("T1", task.getName()),
			() -> assertEquals("Une tâche élémentaire", task.getDescription()),
			() -> assertEquals(Priority.NORMAL, task.getPriority()),
			() -> assertEquals(Status.TODO, task.getStatus())
		);
	}
	
	@Test
	void GetSubTasks() {
		// --{ ARRANGE }--
		TaskDAO dao = daoFactory.getTaskDAO(source);

		// --{ ACT }--
		List<Task> tasks = dao.getSubTasks(1L);

		// --{ ASSERT }--
		assertEquals(3, tasks.size());
		Task task = tasks.get(0);
		assertAll("task",
			() -> assertEquals(Long.valueOf(2), task.getId()),
			() -> assertEquals("T2", task.getName()),
			() -> assertEquals("Une tâche compliquée", task.getDescription()),
			() -> assertEquals(Priority.NORMAL, task.getPriority()),
			() -> assertEquals(Status.DOING, task.getStatus())
		);
	}
	
	@Test
	void GetTaskDependencies() {
		// --{ ARRANGE }--
		TaskDAO dao = daoFactory.getTaskDAO(source);

		// --{ ACT }--
		List<Task> tasks = dao.getTaskDependencies(12L);

		// --{ ASSERT }--
		assertEquals(2, tasks.size());
		Task task = tasks.get(0);
		assertAll("task",
			() -> assertEquals(Long.valueOf(11), task.getId()),
			() -> assertEquals("T11", task.getName())
		);
	}
	
	@Test
	void CreateTask() {
		// --{ ARRANGE }--
        TaskDAO dao = daoFactory.getTaskDAO(source);
        Task task = new Task(null, "T21", "Description", Priority.NORMAL, Status.TODO);

        // --{ ACT }--
        Task createdTask = dao.createTask(task);
        List<Task> tasks = dao.getAllTasks();

        // --{ ASSERT }--
        assertEquals(21, tasks.size());
        assertAll("task",
            () -> assertEquals("T21", createdTask.getName()),
            () -> assertEquals("Description", createdTask.getDescription()),
            () -> assertEquals(Priority.NORMAL, createdTask.getPriority()),
            () -> assertEquals(Status.TODO, createdTask.getStatus())
        );
	}
	
	@Test
	void UpdateTask() {
	    // --{ ARRANGE }--
        TaskDAO dao = daoFactory.getTaskDAO(source);
        Task task = new Task(1L, "Tâche 1", "La tâche numéro 1", Priority.HIGH, Status.DOING);

        // --{ ACT }--
        Boolean isUpdated = dao.updateTask(task);
        Task updatedTask = dao.getTaskById(1L);

        // --{ ASSERT }--
        assertTrue(isUpdated);
        assertAll("task",
            () -> assertEquals(Long.valueOf(1), updatedTask.getId()),
            () -> assertEquals("Tâche 1", updatedTask.getName()),
            () -> assertEquals("La tâche numéro 1", updatedTask.getDescription()),
            () -> assertEquals(Priority.HIGH, updatedTask.getPriority()),
            () -> assertEquals(Status.DOING, updatedTask.getStatus())
        );
    }
	
	@Test
	void DeleteTask() {
	    // --{ ARRANGE }--
        TaskDAO dao = daoFactory.getTaskDAO(source);

        // --{ ACT }--
        Boolean isDeleted = dao.deleteTask(20L);
        Task deletedTask = dao.getTaskById(20L);
        List<Task> tasks = dao.getAllTasks();

        // --{ ASSERT }--
        assertEquals(19, tasks.size());
        assertTrue(isDeleted);
        assertNull(deletedTask);
    }
	
}
