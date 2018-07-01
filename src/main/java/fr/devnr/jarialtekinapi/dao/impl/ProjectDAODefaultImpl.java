package fr.devnr.jarialtekinapi.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import fr.devnr.jarialtekinapi.dao.interfaces.ProjectDAO;
import fr.devnr.jarialtekinapi.model.Priority;
import fr.devnr.jarialtekinapi.model.Project;
import fr.devnr.jarialtekinapi.model.Status;
import fr.devnr.jarialtekinapi.model.Task;

public class ProjectDAODefaultImpl implements ProjectDAO {
	
	private final DataSource source;
	
	public ProjectDAODefaultImpl(DataSource ds) {
		this.source = ds;
	}
	

	/**
	 * Get all projects in database (i.e. the whole content of the Projects table).
	 */
	// Query
	private static final String REQ_GetAllProjects = "SELECT * FROM Projects";
	// Method
	@Override
	public List<Project> getAllProjects() {
		
		List<Project> projects = new ArrayList<>();
		
		try (
		Connection c = source.getConnection();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(REQ_GetAllProjects)) {
			
			while (rs.next()) {
				projects.add(extractProject(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return projects;
	}
	
	/**
	 * Get the project identified by idProject.
	 */
	// Query
	private static final String REQ_GetProjectById = "SELECT * FROM Projects WHERE id=?";
	// Method
	@Override
	public Project getProjectById(Long idProject) {
		
		Project project = null;
		
		try(
		Connection c = source.getConnection();
		PreparedStatement stmt = c.prepareStatement(REQ_GetProjectById)) {
			
			stmt.setLong(1, idProject);
			try(ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					project = extractProject(rs);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return project;
	}

	/**
	 *
	 */
	// Query
	private static final String REQ_GetProjectByTask = ""
    + "SELECT p.id, p.name, p.description, p.startDate, p.endDate "
    + "   FROM Projects p "
    + "   JOIN ProjectsTasks t ON p.id=t.idProject"
    + "   WHERE t.idTask=?";
	// Method
	@Override
	public Project getProjectByTask(Long idTask) {

	    Project project = null;

	    try(
        Connection c = source.getConnection();
        PreparedStatement stmt = c.prepareStatement(REQ_GetProjectByTask)) {

	        stmt.setLong(1, idTask);
	        try(ResultSet rs = stmt.executeQuery()) {
	            if(rs.next()) {
	                project = extractProject(rs);
                }
            }

        } catch (SQLException e) {
	        e.printStackTrace();
        }

	    return project;
    }

	/**
	 * Create a new project in database.
	 */
	// Query
	private static final String REQ_CreateProject = ""
    + "INSERT INTO Projects (id, name, description, startDate, endDate) VALUES (NULL, ?, ?, ?, ?)";
	// Method
	@Override
	public Project createProject(Project project) {
		
		try(
		Connection c = source.getConnection();
		PreparedStatement stmt = c.prepareStatement(REQ_CreateProject, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, project.getName());
			stmt.setString(2, project.getDescription());
			stmt.setDate(3, Date.valueOf(project.getStartDate()));
			stmt.setDate(4, Date.valueOf(project.getEndDate()));
			
			if (stmt.executeUpdate()!=1) { throw new SQLException("Error while inserting new project."); }
			
			try(ResultSet rs = stmt.getGeneratedKeys();) {
				if (rs.next()) {
					project.setId(rs.getLong(1));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return project;
	}

	/**
	 * Update the specified project.
	 */
	// Query
	private static final String REQ_UpdateProject = ""
    + "UPDATE Projects SET name=?, description=?, startDate=?, endDate=? WHERE id=?";
	// Method
	@Override
	public Boolean updateProject(Project project) {
		
		Boolean success = false;
		
		try(
		Connection c = source.getConnection();
		PreparedStatement stmt = c.prepareStatement(REQ_UpdateProject)) {
			
			stmt.setString(1, project.getName());
			stmt.setString(2, project.getDescription());
			stmt.setDate(3, Date.valueOf(project.getStartDate()));
			stmt.setDate(4, Date.valueOf(project.getEndDate()));
			stmt.setLong(5, project.getId());
			
			success = (stmt.executeUpdate()==1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}

	/**
	 * Delete the specified project (identified by idProject).
	 */
	// Query
	private static final String REQ_DeleteProject = "DELETE FROM Projects WHERE id=?";
	// Method
	@Override
	public Boolean deleteProject(Long idProject) {
		
		Boolean success = false;
		
		try(
		Connection c = source.getConnection();
		PreparedStatement stmt = c.prepareStatement(REQ_DeleteProject)) {
			
			stmt.setLong(1, idProject);
			
			success = (stmt.executeUpdate()==1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}

	/**
	 * Get all the tasks associated to the project identified by idProject.
	 */
	// Query
	private static final String REQ_GetProjectTasks = ""
    + "SELECT t.id, t.name, t.description, t.priority, t.status"
    + "   FROM ProjectsTasks p JOIN Tasks t ON p.idTask=t.id"
    + "   WHERE p.idProject=?";
	// Method
	@Override
	public List<Task> getProjectTasks(Long idProject) {
		
		List<Task> tasks = new ArrayList<>();
		
		try(
		Connection c = source.getConnection();
		PreparedStatement stmt = c.prepareStatement(REQ_GetProjectTasks)) {
			
			stmt.setLong(1, idProject);
			try(ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					tasks.add(extractTask(rs));
				}
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return tasks;
	}
	
	
	// =================
	//  Private Methods
	// =================
	
	/**
	 * Extract a project from the given result set.
	 * 
	 * @param rs result set from which to extract a project
	 * @return the extracted project
	 * @throws SQLException
	 */
	private Project extractProject(ResultSet rs) throws SQLException {
		Date startDate = rs.getDate("startDate");
		Date endDate = rs.getDate("endDate");
		return new Project(
			rs.getLong("id"),
			rs.getString("name"),
			rs.getString("description"),
			startDate!=null ? startDate.toLocalDate() : null,
			endDate!=null ? endDate.toLocalDate() : null
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
