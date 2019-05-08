package fr.devnr.jarialtekinapi.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO;
import fr.devnr.jarialtekinapi.model.Priority;
import fr.devnr.jarialtekinapi.model.Status;
import fr.devnr.jarialtekinapi.model.Task;
import fr.devnr.jarialtekinapi.model.TaskPlanning;


public class TaskPlanningDAODefaultImpl implements TaskPlanningDAO {

	private final DataSource source;


	public TaskPlanningDAODefaultImpl(DataSource ds) {
		this.source = ds;
	}


	private static final String QUERY_GetAllTaskPlannings = ""
	+ "SELECT id, name, description, priority, status, startDate, startTime, endDate, endTime "
	+ "   FROM TasksPlannings p JOIN Tasks t ON p.idTask=t.id";
	@Override
	public List<TaskPlanning> getAllTaskPlannings() {
		
		List<TaskPlanning> plannings = new ArrayList<>();
		
		try (
		  Connection c = source.getConnection();
		  Statement stmt = c.createStatement();
		  ResultSet rs = stmt.executeQuery(QUERY_GetAllTaskPlannings)
		) {
			
			while (rs.next()) {
				plannings.add(extractTaskPlanning(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return plannings;
	}


	private static final String QUERY_GetTaskPlanningByTask = ""
	+ "SELECT id, name, description, priority, status, startDate, startTime, endDate, endTime "
	+ "   FROM TasksPlannings p JOIN Tasks t ON p.idTask=t.id "
	+ "   WHERE idTask=?";
	@Override
	public TaskPlanning getTaskPlanningByTask(Long idTask) {
		
		TaskPlanning planning = null;
		
		try(
		  Connection c = source.getConnection();
		  PreparedStatement stmt = c.prepareStatement(QUERY_GetTaskPlanningByTask)
		) {
			
			stmt.setLong(1, idTask);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) planning = extractTaskPlanning(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return planning;
	}


	private static final String QUERY_GetTaskPlanningsByPeriod = ""
	+ "SELECT id, name, description, priority, status, startDate, startTime, endDate, endTime "
	+ "   FROM TasksPlannings p JOIN Tasks t ON p.idTask=t.id "
	+ "   WHERE startDate>? or (startDate=? and startTime>=?) "
	+ "   AND endDate<? or (endDate=? and endTime<=?)";
	@Override
	public List<TaskPlanning> getTaskPlanningsByPeriod(LocalDateTime start, LocalDateTime end) {
		
		List<TaskPlanning> plannings = new ArrayList<>();
		
		try (
		  Connection c = source.getConnection();
		  PreparedStatement stmt = c.prepareStatement(QUERY_GetTaskPlanningsByPeriod)
		) {
			
			stmt.setDate(1, Date.valueOf(start.toLocalDate()));
            stmt.setDate(2, Date.valueOf(start.toLocalDate()));
			stmt.setTime(3, Time.valueOf(start.toLocalTime()));
			stmt.setDate(4, Date.valueOf(end.toLocalDate()));
            stmt.setDate(5, Date.valueOf(end.toLocalDate()));
			stmt.setTime(6, Time.valueOf(end.toLocalTime()));
			
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) plannings.add(extractTaskPlanning(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return plannings;
	}


	private static final String QUERY_CreateTaskPlanning = ""
	+ "INSERT INTO TasksPlannings (idTask, startDate, startTime, endDate, endTime) VALUES (?, ?, ?, ?, ?)";
	@Override
	public TaskPlanning createTaskPlanning(TaskPlanning taskPlanning) {
		
		try (
		  Connection c = source.getConnection();
		  PreparedStatement stmt = c.prepareStatement(QUERY_CreateTaskPlanning)
		) {
			
			stmt.setLong(1, taskPlanning.getTask().getId());
			stmt.setDate(2, Date.valueOf(taskPlanning.getStart().toLocalDate()));
			stmt.setTime(3, Time.valueOf(taskPlanning.getStart().toLocalTime()));
			stmt.setDate(4, Date.valueOf(taskPlanning.getEnd().toLocalDate()));
			stmt.setTime(5, Time.valueOf(taskPlanning.getEnd().toLocalTime()));
			
			if (stmt.executeUpdate()!=1) { throw new SQLException("Error while inserting new task planning."); }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return taskPlanning;
	}


	private static final String QUERY_UpdateTaskPlanning = ""
	+ "UPDATE TasksPlannings SET startDate=?, startTime=?, endDate=?, endTime=? WHERE idTask=?";
	@Override
	public Boolean updateTaskPlanning(TaskPlanning taskPlanning) {
		
		Boolean success = false;
		
		try (
		  Connection c = source.getConnection();
		  PreparedStatement stmt = c.prepareStatement(QUERY_UpdateTaskPlanning)
		) {
			
			stmt.setDate(1, Date.valueOf(taskPlanning.getStart().toLocalDate()));
			stmt.setTime(2, Time.valueOf(taskPlanning.getStart().toLocalTime()));
			stmt.setDate(3, Date.valueOf(taskPlanning.getEnd().toLocalDate()));
			stmt.setTime(4, Time.valueOf(taskPlanning.getEnd().toLocalTime()));
			stmt.setLong(5, taskPlanning.getTask().getId());
			
			success = (stmt.executeUpdate()==1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}


	private static final String REQ_DeleteTaskPlanning = ""
	+ "DELETE FROM TasksPlannings WHERE idTask=?";
	@Override
	public Boolean deleteTaskPlanning(Long idTask) {
		
		Boolean success = false;
		
		try(
		  Connection c = source.getConnection();
		  PreparedStatement stmt = c.prepareStatement(REQ_DeleteTaskPlanning)
		) {
			
			stmt.setLong(1, idTask);
			
			success = (stmt.executeUpdate()==1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}



	/**
	 * Extract a task planning from the given result set.
	 * 
	 * @param rs result set from which to extract a task planning
	 * @return the extracted task planning
	 * @throws SQLException
	 */
	private TaskPlanning extractTaskPlanning(ResultSet rs) throws SQLException {
		return new TaskPlanning(
			extractTask(rs), 
			LocalDateTime.of(
                rs.getDate("startDate").toLocalDate(),
                rs.getTime("startTime").toLocalTime()
            ),
			LocalDateTime.of(
                rs.getDate("endDate").toLocalDate(),
                rs.getTime("endTime").toLocalTime()
            )
		);
	}

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
