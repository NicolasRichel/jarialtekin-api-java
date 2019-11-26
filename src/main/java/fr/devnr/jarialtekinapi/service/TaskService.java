package fr.devnr.jarialtekinapi.service;

import fr.devnr.jarialtekinapi.dao.factory.DAOFactory;
import fr.devnr.jarialtekinapi.dao.factory.DataSourceFactory;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO;
import fr.devnr.jarialtekinapi.model.Task;
import fr.devnr.jarialtekinapi.model.TaskPlanning;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class TaskService {

	private final TaskDAO taskDao;
	private final TaskPlanningDAO taskPlanningDao;


    public TaskService(TaskDAO taskDAO, TaskPlanningDAO taskPlanningDAO) {
        this.taskDao = taskDAO;
        this.taskPlanningDao = taskPlanningDAO;
    }

	public TaskService() {
		DataSource source = DataSourceFactory.getDataSourceFactory().getDataSource();
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		this.taskDao = daoFactory.getTaskDAO(source);
		this.taskPlanningDao = daoFactory.getTaskPlanningDAO(source);
	}


	public List<Task> getAllTasks() {
		List<Task> tasks = taskDao.getAllTasks();
		tasks.forEach(task -> {
			task.setParentTask( taskDao.getParentTask(task.getId()) );
			task.addSubTasks( taskDao.getSubTasks(task.getId()) );
			task.addDependencies( taskDao.getTaskDependencies(task.getId()) );
		});
		return tasks;
	}

	public List<Task> getTasksByPeriod(LocalDateTime start, LocalDateTime end) {
        return taskPlanningDao.getTaskPlanningsByPeriod(start, end).stream()
				.map(TaskPlanning::getTask)
				.collect(Collectors.toList());
    }

	public Task getTask(Long idTask) {
        return taskDao.getTaskById(idTask);
    }

	public TaskPlanning getTaskPlanning(Long idTask) {
        return taskPlanningDao.getTaskPlanningByTask(idTask);
	}

	public Task createTask(Task task) {
    	return taskDao.createTask(task);
	}

	public Boolean updateTask(Task task) {
		Task newTask = taskDao.getTaskById(task.getId());
		newTask.setName(task.getName());
        newTask.setDescription(task.getDescription());
    	return taskDao.updateTask(newTask);
	}

	public Boolean deleteTask(Long idTask) {
    	return taskDao.deleteTask(idTask);
	}

}
