package fr.devnr.jarialtekinapi.dao.interfaces;

import java.util.List;

import fr.devnr.jarialtekinapi.model.Task;

public interface TaskDAO {
	
	/**
	 * Get all tasks in database (i.e. the whole content of the Tasks table).
	 * 
	 * @return a list of all tasks
	 */
	List<Task> getAllTasks();
	
	/**
	 * Get one task from the database based on its id.
	 * 
	 * @param idTask id of the task to retrieve
	 * @return the task with the specified id or null if it does not exist
	 */
	Task getTaskById(Long idTask);
	
	/**
	 * Get the parent task from database for the task identified by <code>idTask</code>.
	 * 
	 * @param idTask id of the task for which to get the parent
	 * @return the parent task of the specified task or <code>null</code> if it does not exist
	 */
	Task getParentTask(Long idTask);
	
	/**
	 * Get the list of the sub-tasks for the task identified by <code>idTask</code>.
	 * 
	 * @param idTask id of the task for which to get the sub-tasks
	 * @return the list of the sub-tasks of the specified task (possibly an empty list there is none)
	 */
	List<Task> getSubTasks(Long idTask);
	
	/**
	 * Get the list of the dependencies for the task identified by <code>idTask</code>.
	 * That is the list of tasks on which the specified task depends.
	 * 
	 * @param idTask id of the task for which to get the dependencies
	 * @return the list of tdependencies of the specified task (possibly an empty list there is none)
	 */
	List<Task> getTaskDependencies(Long idTask);
	
	/**
	 * Create a new task in database (i.e. insert a row in the Tasks table).
	 * The given task should not have an id yet since it will be setted by the database.
	 * 
	 * @param task the task to create (with no id)
	 * @return the created task (with a freshly generated id)
	 */
	Task createTask(Task task);
	
	/**
	 * Update a task in database in database according to the given task.
	 * The task to update is determined based on the id of the given task,
	 * if the given task has no id or an id that does not exist then the 
	 * database will be likely to throw an error (or maybe do nothing).
	 * 
	 * @param task the task to update with its new id
	 * @return <code>true</code> if the task has been updated successfuly, <code>false</code> otherwise
	 */
	Boolean updateTask(Task task);
	
	/**
	 * Delete the task identified by <code>idTask</code> in database.
	 * 
	 * @param idTask id of the task to delete
	 * @return <code>true</code> if the task has been deleted successfuly, <code>false</code> otherwise
	 */
	Boolean deleteTask(Long idTask);
	
}
