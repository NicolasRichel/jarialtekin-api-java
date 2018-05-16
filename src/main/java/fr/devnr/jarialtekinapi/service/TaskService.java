package fr.devnr.jarialtekinapi.service;

import java.util.List;
import java.util.stream.Collectors;

import fr.devnr.jarialtekinapi.dao.factory.DAOFactory;
import fr.devnr.jarialtekinapi.dao.factory.DAOFactoryDefault;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO;
import fr.devnr.jarialtekinapi.dto.TaskDto;

public class TaskService {
	
	private final TaskDAO taskDao;
	private final TaskPlanningDAO taskPlanningDao;
	
	//  Constructors
	// ==============

    public TaskService(TaskDAO taskDAO, TaskPlanningDAO taskPlanningDAO) {
        this.taskDao = taskDAO;
        this.taskPlanningDao = taskPlanningDAO;
    }

	public TaskService(String daoImpl) {
		DAOFactory daoFactory;
		switch (daoImpl.toUpperCase()) {
			case "DEFAULT":
				daoFactory = new DAOFactoryDefault(); break;
			case "MARIADB":
				daoFactory = new DAOFactoryDefault(); break;
			case "MONGODB":
				daoFactory = null; break;
			default:
				daoFactory = new DAOFactoryDefault(); break;
		}
		this.taskDao = daoFactory.getTaskDAO();
		this.taskPlanningDao = daoFactory.getTaskPlanningDAO();
	}
	
	
	// ================
	//  Public Methods
	// ================
	
	public List<TaskDto> getAllTasks() {
		return taskDao.getAllTasks()
			.stream()
			.map(task -> {
				TaskDto dto = new TaskDto(
					task.getId(), 
					task.getName(), 
					task.getDescription(), 
					task.getPriority(),
					task.getStatus()
				);
				dto.setParentTask(taskDao.getParentTask(task.getId()));
				dto.addSubTasks(taskDao.getSubTasks(task.getId()));
				dto.addDependencies(taskDao.getTaskDependencies(task.getId()));
				return dto;
			})
			.collect(Collectors.toList());
	}
	
}
