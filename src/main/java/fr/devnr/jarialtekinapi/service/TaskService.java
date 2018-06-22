package fr.devnr.jarialtekinapi.service;

import java.util.List;
import java.util.stream.Collectors;

import fr.devnr.jarialtekinapi.dao.factory.DAOFactory;
import fr.devnr.jarialtekinapi.dao.factory.DAOFactoryDefault;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO;
import fr.devnr.jarialtekinapi.dto.PeriodDto;
import fr.devnr.jarialtekinapi.dto.TaskDto;
import fr.devnr.jarialtekinapi.model.Task;
import fr.devnr.jarialtekinapi.model.TaskPlanning;

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
			default:
				daoFactory = new DAOFactoryDefault(); break;
		}
		this.taskDao = daoFactory.getTaskDAO();
		this.taskPlanningDao = daoFactory.getTaskPlanningDAO();
	}
	
	
	// ================
	//  Public Methods
	// ================
	
	public List<Task> getAllTasksModel() {
		return taskDao.getAllTasks()
			.stream()
			.map(task -> {
				Task model = new Task(
					task.getId(), 
					task.getName(), 
					task.getDescription(), 
					task.getPriority(),
					task.getStatus()
				);
				model.setParentTask(taskDao.getParentTask(task.getId()));
				model.addSubTasks(taskDao.getSubTasks(task.getId()));
				model.addDependencies(taskDao.getTaskDependencies(task.getId()));
				return model;
			})
			.collect(Collectors.toList());
	}

	public List<TaskDto> getAllTasksDTO() {
    	return taskDao.getAllTasks()
			.stream()
			.map(task -> {
				TaskDto dto = new TaskDto(
					task.getId(),
					task.getName(),
					task.getDescription()
				);
				return dto;
			})
			.collect(Collectors.toList());
	}

	public PeriodDto getPeriodDTO(Long idTask) {
        TaskPlanning planning = taskPlanningDao.getTaskPlanningByTask(idTask);
        return new PeriodDto(
            planning.getStart().toString(),
            planning.getEnd().toString()
        );
	}
	
}
