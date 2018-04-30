package fr.devnr.jarialtekinapi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO;
import fr.devnr.jarialtekinapi.model.Priority;
import fr.devnr.jarialtekinapi.model.Status;
import fr.devnr.jarialtekinapi.model.Task;

public class TaskDAODefaultImpl implements TaskDAO {
	
	private final DataSource source;
	
	public TaskDAODefaultImpl(DataSource ds) {
		this.source = ds;
	}
	
	
	/**
	 * Get all tasks in database (i.e. the whole content of the Tasks table).
	 */
	// Query
	private static final String REQ_GetAllTasks = "SELECT * FROM Tasks";
	// Method
	@Override
	public List<Task> getAllTasks() {
		
		List<Task> tasks = new ArrayList<>();
		
		try (
		Connection c = source.getConnection();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(REQ_GetAllTasks)) {
			
			while (rs.next()) {
				tasks.add(extractTask(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tasks;
	}
	
	/**
	 * Get one task from the database based on its id.
	 */
	// Query
	private static final String REQ_GetTaskById = "SELECT * FROM Tasks WHERE id=?";
	// Method
	@Override
	public Task getTaskById(Long idTask) {
		
		Task task = null;
		
		try(
		Connection c = source.getConnection();
		PreparedStatement stmt = c.prepareStatement(REQ_GetTaskById)) {
			
			stmt.setLong(1, idTask);
			try(ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					task = extractTask(rs);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return task;
	}

	/**
	 * Get the parent task from database for the task identified by idTask.
	 */
	// Query
	private static final String REQ_GetParentTask = ""
			+ "SELECT id, name, description, priority, status "
			+ "   FROM Tasks t JOIN ParentTasks p ON t.id=p.idParent "
			+ "   WHERE p.idTask=?";
	// Method
	@Override
	public Task getParentTask(Long idTask) {
		
		Task task = null;
		
		try(
		Connection c = source.getConnection();
		PreparedStatement stmt = c.prepareStatement(REQ_GetParentTask)) {
			
			stmt.setLong(1, idTask);
			try(ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					task = extractTask(rs);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return task;
	}

	/**
	 * Get the list of the sub-tasks for the task identified by idTask.
	 */
	// Query
	private static final String REQ_GetSubTasks = ""
			+ "SELECT id, name, description, priority, status "
			+ "   FROM Tasks t JOIN ParentTasks p ON t.id=p.idTask "
			+ "   WHERE p.idParent=?";
	// Method
	@Override
	public List<Task> getSubTasks(Long idTask) {
		
		List<Task> tasks = new ArrayList<>();
		
		try(
		Connection c = source.getConnection();
		PreparedStatement stmt = c.prepareStatement(REQ_GetSubTasks)) {
			
			stmt.setLong(1, idTask);
			try(ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					tasks.add(extractTask(rs));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tasks;
	}

	/**
	 * Get the list of dependencies for the task identified by idTask.
	 */
	// Query
	private static final String REQ_GetTaskDependencies = ""
			+ "SELECT id, name, description, priority, status "
			+ "   FROM Tasks t JOIN TasksDependencies d ON d.idDependency=t.id "
			+ "   WHERE d.idTask=?";
	// Method
	@Override
	public List<Task> getTaskDependencies(Long idTask) {
		
		List<Task> tasks = new ArrayList<>();
		
		try(
		Connection c = source.getConnection();
		PreparedStatement stmt = c.prepareStatement(REQ_GetTaskDependencies)) {
			
			stmt.setLong(1, idTask);
			try(ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					tasks.add(extractTask(rs));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tasks;
	}

	/**
	 * Create a new task in database (i.e. insert a row in the Tasks table).
	 */
	// Query
	private static final String REQ_CreateTask = ""
			+ "INSERT INTO Tasks (id, name, description, priority, status) VALUES (NULL, ?, ?, ?, ?)";
	// Method
	@Override
	public Task createTask(Task task) {
		
		try(
		Connection c = source.getConnection();
		PreparedStatement stmt = c.prepareStatement(REQ_CreateTask, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, task.getName());
			stmt.setString(2, task.getDescription());
			stmt.setInt(3, task.getPriority().getValue());
			stmt.setInt(4, task.getStatus().getValue());
			
			if (stmt.executeUpdate()!=1) { throw new SQLException("Error while inserting new task."); }
			
			try(ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					task.setId(rs.getLong(1));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return task;
	}

	/**
	 * Update a task in database in database according to the given task.
	 */
	// Query
	private static final String REQ_UpdateTask = ""
			+ "UPDATE Tasks SET name=?, description=?, priority=?, status=? WHERE id=?";
	// Method
	@Override
	public Boolean updateTask(Task task) {
		
		Boolean success = false;
		
		try(
		Connection c = source.getConnection();
		PreparedStatement stmt = c.prepareStatement(REQ_UpdateTask)) {
			
			stmt.setString(1, task.getName());
			stmt.setString(2, task.getDescription());
			stmt.setInt(3, task.getPriority().getValue());
			stmt.setInt(4, task.getStatus().getValue());
			stmt.setLong(5, task.getId());
			
			success = (stmt.executeUpdate()==1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}

	/**
	 * Delete the task identified by idTask in database.
	 */
	// Query
	private static final String REQ_DeleteTask = "DELETE FROM Tasks WHERE id=?";
	// Method
	@Override
	public Boolean deleteTask(Long idTask) {
		
		Boolean success = false;
		
		try(
		Connection c = source.getConnection();
		PreparedStatement stmt = c.prepareStatement(REQ_DeleteTask)) {
			
			stmt.setLong(1, idTask);
			
			success = (stmt.executeUpdate()==1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	
	// =================
	//  Private Methods
	// =================
	
	/**
	 * Extract a task from the given result set.
	 * 
	 * @param rs result set from which to extract a task
	 * @return the extracted task
	 * @throws SQLException
	 */
	private Task extractTask(ResultSet rs) throws SQLException {
		return new Task(
			rs.getLong("id"),
			rs.getString("name"),
			rs.getString("description"),
			Priority.valueOf(rs.getInt("priority")),
			Status.valueOf(rs.getInt("status"))
		);
	}
	
}
