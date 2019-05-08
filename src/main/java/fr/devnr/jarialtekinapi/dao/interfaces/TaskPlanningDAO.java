package fr.devnr.jarialtekinapi.dao.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import fr.devnr.jarialtekinapi.model.TaskPlanning;


public interface TaskPlanningDAO {

	/**
	 * Return all task plannings in the database (i.e. the whole content of TasksPlannings table).
	 * 
	 * @return the list of all tasks plannings in database
	 */
	List<TaskPlanning> getAllTaskPlannings();

	/**
	 * Get the task planning associated to a given task.
	 * 
	 * @param idTask id of the task for which to get the planning
	 * @return the corresponding task planning
	 */
	TaskPlanning getTaskPlanningByTask(Long idTask);

	/**
	 * Get all task plannings within a given period.
	 * 
	 * @param start begining of the period (date and time)
	 * @param end end of the period (date and time)
	 * @return the list of task plannings in this period (possibly an empty list if there is none)
	 */
	List<TaskPlanning> getTaskPlanningsByPeriod(LocalDateTime start, LocalDateTime end);

	/**
	 * Add a new entry in the TasksPlannings table.
	 * 
	 * @param taskPlanning the task planning to add
	 * @return the added task planning
	 */
	TaskPlanning createTaskPlanning(TaskPlanning taskPlanning);

	/**
	 * Update an entry in the TasksPlannings table.
	 * Note : a task planning is identified by its task id.
	 * 
	 * @param taskPlanning the task planning to update (with its new start/end date and time)
	 * @return <code>true</code> if the operation succeed, <code>false</code> otherwise
	 */
	Boolean updateTaskPlanning(TaskPlanning taskPlanning);

	/**
	 * Delete an entry in the TasksPlanning table.
	 * 
	 * @param idTask id of the task for which to delete the task planning
	 * @return <code>true</code> if the operation succeed, <code>false</code> otherwise
	 */
	Boolean deleteTaskPlanning(Long idTask);

}
