package fr.devnr.jarialtekinapi.db;

import fr.devnr.jarialtekinapi.dao.factory.DataSourceFactory;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Tag("db_tests")
class DBTest {

    private static final DataSource source = DataSourceFactory.getMariadbDataSource("/testdb.properties");

    private static final String TEST_TASKS_PK = "src/test/resources/sql/test_Tasks_pk.sql";
    private static final String TEST_TASKS_NAMENOTNULL = "src/test/resources/sql/test_Tasks_nameNotNull.sql";
    private static final String TEST_PARENTTASKS_PK = "src/test/resources/sql/test_ParentTasks_pk.sql";
    private static final String TEST_PARENTTASKS_FK01 = "src/test/resources/sql/test_ParentTasks_fk01.sql";
    private static final String TEST_PARENTTASKS_FK02 = "src/test/resources/sql/test_ParentTasks_fk02.sql";
    private static final String TEST_PARENTTASKS_IDTASKNOTNULL = "src/test/resources/sql/test_ParentTasks_idTaskNotNull.sql";
    private static final String TEST_PARENTTASKS_IDPARENTNOTNULL = "src/test/resources/sql/test_ParentTasks_idParentNotNull.sql";
    private static final String TEST_TASKSDEPENDENCIES_PK = "src/test/resources/sql/test_TasksDependencies_pk.sql";
    private static final String TEST_TASKSDEPENDENCIES_FK01 = "src/test/resources/sql/test_TasksDependencies_fk01.sql";
    private static final String TEST_TASKSDEPENDENCIES_FK02 = "src/test/resources/sql/test_TasksDependencies_fk02.sql";
    private static final String TEST_TASKSDEPENDENCIES_IDTASKNOTNULL = "src/test/resources/sql/test_TasksDependencies_idTaskNotNull.sql";
    private static final String TEST_TASKSDEPENDENCIES_IDDEPENDENCYNOTNULL = "src/test/resources/sql/test_TasksDependencies_idDependencyNotNull.sql";
    private static final String TEST_TASKSPLANNINGS_PK = "src/test/resources/sql/test_TasksPlannings_pk.sql";
    private static final String TEST_TASKSPLANNINGS_FK01 = "src/test/resources/sql/test_TasksPlannings_fk01.sql";
    private static final String TEST_TASKSPLANNINGS_CK01 = "src/test/resources/sql/test_TasksPlannings_ck01.sql";
    private static final String TEST_TASKSPLANNINGS_IDTASKNOTNULL = "src/test/resources/sql/test_TasksPlannings_idTaskNotNull.sql";
    private static final String TEST_TASKSPLANNINGS_STARTDATENOTNULL = "src/test/resources/sql/test_TasksPlannings_startDateNotNull.sql";
    private static final String TEST_TASKSPLANNINGS_STARTTIMENOTNULL = "src/test/resources/sql/test_TasksPlannings_startTimeNotNull.sql";
    private static final String TEST_TASKSPLANNINGS_ENDDATENOTNULL = "src/test/resources/sql/test_TasksPlannings_endDateNotNull.sql";
    private static final String TEST_TASKSPLANNINGS_ENDTIMENOTNULL = "src/test/resources/sql/test_TasksPlannings_endTimeNotNull.sql";


    /**
     * Empty tables content at the beginning of each test.
     */
    @BeforeEach
    void init() {
        DBTestsUtils.emptyTables(source);
    }

    /**
     * Empty all tables in DB when all tests are done.
     */
    @AfterAll
    static void finalizeAll() {
        DBTestsUtils.emptyTables(source);
    }

    @Test
    @DisplayName("Test Tasks table integrity constraints")
    void testTasksTable() {
        Throwable exception;

        // --{ Test Primary Key Constraint : "pk_tasks" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKS_PK);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("Duplicate entry"));

        // --{ Test Constraint : "name NOT NULL" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKS_NAMENOTNULL);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("cannot be null"));
    }

    @Test
    @DisplayName("Test ParentTasks table integrity constraints")
    void testParentTasksTable() {
        Throwable exception;

        // --{ Test Primary Key Constraint : "pk_parentTasks" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_PARENTTASKS_PK);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("Duplicate entry"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Foreign Key Constraint : "fk_parentTasks_01" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_PARENTTASKS_FK01);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("fk_parentTasks_01"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Foreign Key Constraint : "fk_parentTasks_02" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_PARENTTASKS_FK02);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("fk_parentTasks_02"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Constraint : "idTask NOT NULL" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_PARENTTASKS_IDTASKNOTNULL);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("cannot be null"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Constraint : "idParent NOT NULL" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_PARENTTASKS_IDPARENTNOTNULL);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("cannot be null"));
    }

    @Test
    @DisplayName("Test TasksDependencies table integrity constraints")
    void testTasksDependenciesTable() {
        Throwable exception;

        // --{ Test Primary Key Constraint : "pk_tasksDependencies" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKSDEPENDENCIES_PK);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("Duplicate entry"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Foreign Key Constraint : "fk_tasksDependencies_01" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKSDEPENDENCIES_FK01);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("fk_tasksDependencies_01"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Foreign Key Constraint : "fk_tasksDependencies_02" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKSDEPENDENCIES_FK02);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("fk_tasksDependencies_02"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Constraint : "idTask NOT NULL" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKSDEPENDENCIES_IDTASKNOTNULL);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("cannot be null"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Constraint : "idDependency NOT NULL" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKSDEPENDENCIES_IDDEPENDENCYNOTNULL);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("cannot be null"));
    }

    @Test
    @DisplayName("Test TasksPlannings table integrity constraints")
    void testTasksPlanningsTable() {
        Throwable exception;

        // --{ Test Primary Key Constraint : "pk_tasksPlannings" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKSPLANNINGS_PK);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("Duplicate entry"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Foreign Key Constraint : "fk_tasksPlannings_01" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKSPLANNINGS_FK01);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("fk_tasksPlannings_01"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Check Constraint : "ck_tasksPlannings_01" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKSPLANNINGS_CK01);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("ck_tasksPlannings_01"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Constraint : "idTask NOT NULL" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKSPLANNINGS_IDTASKNOTNULL);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("cannot be null"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Constraint : "startDate NOT NULL" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKSPLANNINGS_STARTDATENOTNULL);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("cannot be null"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Constraint : "startTime NOT NULL" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKSPLANNINGS_STARTTIMENOTNULL);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("cannot be null"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Constraint : "endDate NOT NULL" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKSPLANNINGS_ENDDATENOTNULL);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("cannot be null"));
        DBTestsUtils.emptyTables(source);

        // --{ Test Constraint : "endTime NOT NULL" }--
        exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            try {
                DBTestsUtils.executeBatchFromFile(source, TEST_TASKSPLANNINGS_ENDTIMENOTNULL);
                fail("BatchUpdateException not thrown !");
            } catch (BatchUpdateException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("cannot be null"));
    }

}
