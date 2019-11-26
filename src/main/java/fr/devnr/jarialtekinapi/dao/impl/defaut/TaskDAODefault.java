package fr.devnr.jarialtekinapi.dao.impl.defaut;

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


public class TaskDAODefault implements TaskDAO {

	private final DataSource source;


	public TaskDAODefault(DataSource ds) {
		this.source = ds;
	}


	private static final String QUERY_GetAllTasks = ""
	+ "SELECT * FROM Tasks";
	@Override
	public List<Task> getAllTasks() {
		List<Task> tasks = new ArrayList<>();
		try (
		  Connection c = source.getConnection();
		  Statement stmt = c.createStatement();
		  ResultSet rs = stmt.executeQuery(QUERY_GetAllTasks)
		) {
			while (rs.next()) {
				tasks.add(mapToTask(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}


	private static final String QUERY_GetTaskById = ""
	+ "SELECT * FROM Tasks WHERE id=?";
	@Override
	public Task getTaskById(Long idTask) {
		Task task = null;
		try(
		  Connection c = source.getConnection();
		  PreparedStatement stmt = c.prepareStatement(QUERY_GetTaskById)
		) {
			stmt.setLong(1, idTask);
			try(ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					task = mapToTask(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return task;
	}


	private static final String QUERY_GetParentTask = ""
	+ "SELECT id, name, description, priority, status "
	+ "   FROM Tasks t JOIN ParentTasks p ON t.id=p.idParent "
	+ "   WHERE p.idTask=?";
	@Override
	public Task getParentTask(Long idTask) {
		Task task = null;
		try(
		  Connection c = source.getConnection();
		  PreparedStatement stmt = c.prepareStatement(QUERY_GetParentTask)
		) {
			stmt.setLong(1, idTask);
			try(ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					task = mapToTask(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return task;
	}


	private static final String QUERY_GetSubTasks = ""
	+ "SELECT id, name, description, priority, status "
	+ "   FROM Tasks t JOIN ParentTasks p ON t.id=p.idTask "
	+ "   WHERE p.idParent=?";
	@Override
	public List<Task> getSubTasks(Long idTask) {
		List<Task> tasks = new ArrayList<>();
		try(
		  Connection c = source.getConnection();
		  PreparedStatement stmt = c.prepareStatement(QUERY_GetSubTasks)
		) {
			stmt.setLong(1, idTask);
			try(ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					tasks.add(mapToTask(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}


	private static final String QUERY_GetTaskDependencies = ""
	+ "SELECT id, name, description, priority, status "
	+ "   FROM Tasks t JOIN TasksDependencies d ON d.idDependency=t.id "
	+ "   WHERE d.idTask=?";
	@Override
	public List<Task> getTaskDependencies(Long idTask) {
		List<Task> tasks = new ArrayList<>();
		try(
		  Connection c = source.getConnection();
		  PreparedStatement stmt = c.prepareStatement(QUERY_GetTaskDependencies)
		) {
			stmt.setLong(1, idTask);
			try(ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					tasks.add(mapToTask(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}


	private static final String QUERY_CreateTask = ""
	+ "INSERT INTO Tasks (id, name, description, priority, status) VALUES (NULL, ?, ?, ?, ?)";
	@Override
	public Task createTask(Task task) {
		try(
		  Connection c = source.getConnection();
		  PreparedStatement stmt = c.prepareStatement(QUERY_CreateTask, Statement.RETURN_GENERATED_KEYS)
		) {
			stmt.setString(1, task.getName());
			stmt.setString(2, task.getDescription());
			stmt.setInt(3, task.getPriority().getValue());
			stmt.setInt(4, task.getStatus().getValue());
			if (stmt.executeUpdate() != 1) {
				throw new SQLException("Error while inserting new task.");
			}
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


	private static final String QUERY_UpdateTask = ""
	+ "UPDATE Tasks SET name=?, description=?, priority=?, status=? WHERE id=?";
	@Override
	public Boolean updateTask(Task task) {
		Boolean success = false;
		try(
		  Connection c = source.getConnection();
		  PreparedStatement stmt = c.prepareStatement(QUERY_UpdateTask)
		) {
			stmt.setString(1, task.getName());
			stmt.setString(2, task.getDescription());
			stmt.setInt(3, task.getPriority().getValue());
			stmt.setInt(4, task.getStatus().getValue());
			stmt.setLong(5, task.getId());
			
			success = (stmt.executeUpdate() == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}


	private static final String QUERY_DeleteTask = ""
	+ "DELETE FROM Tasks WHERE id=?";
	@Override
	public Boolean deleteTask(Long idTask) {
		Boolean success = false;
		try(
		  Connection c = source.getConnection();
		  PreparedStatement stmt = c.prepareStatement(QUERY_DeleteTask)
		) {
			stmt.setLong(1, idTask);
			
			success = (stmt.executeUpdate()==1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	

	/**
	 * Extract a task from the given result set.
	 * 
	 * @param rs result set from which to extract a task
	 * @return the extracted task
	 * @throws SQLException
	 */
	private Task mapToTask(ResultSet rs) throws SQLException {
		return new Task(
			rs.getLong("id"),
			rs.getString("name"),
			rs.getString("description"),
			Priority.valueOf(rs.getInt("priority")),
			Status.valueOf(rs.getInt("status"))
		);
	}

}
