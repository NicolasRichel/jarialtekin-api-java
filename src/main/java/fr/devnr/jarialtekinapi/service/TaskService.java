package fr.devnr.jarialtekinapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import fr.devnr.jarialtekinapi.dao.factory.DAOFactory;
import fr.devnr.jarialtekinapi.dao.factory.DAOFactoryDefault;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskDAO;
import fr.devnr.jarialtekinapi.dao.interfaces.TaskPlanningDAO;
import fr.devnr.jarialtekinapi.dto.PeriodDTO;
import fr.devnr.jarialtekinapi.dto.TaskDTO;
import fr.devnr.jarialtekinapi.model.Task;
import fr.devnr.jarialtekinapi.model.TaskPlanning;


public class TaskService {

	private final TaskDAO taskDao;
	private final TaskPlanningDAO taskPlanningDao;


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

	public List<TaskDTO> getAllTasksDTO() {
    	return taskDao.getAllTasks()
			.stream()
			.map(t -> new TaskDTO(t.getId(), t.getName(), t.getDescription()))
			.collect(Collectors.toList());
	}

	public List<TaskDTO> getTasksByPeriodDTO(PeriodDTO period) {
        LocalDateTime start = LocalDateTime.parse(period.getStart());
        LocalDateTime end = LocalDateTime.parse(period.getEnd());
        return taskPlanningDao.getTaskPlanningsByPeriod(start, end)
            .stream()
            .map(planning -> {
                Task task = planning.getTask();
                return new TaskDTO(
                    task.getId(),
                    task.getName(),
                    task.getDescription()
                );
            }).collect(Collectors.toList());
    }

	public TaskDTO getTaskDTO(Long idTask) {
        Task task = taskDao.getTaskById(idTask);
        TaskDTO dto = null;
        if (task!=null) {
        	dto = new TaskDTO(task.getId(), task.getName(), task.getDescription());
		}
        return dto;
    }

	public PeriodDTO getPeriodDTO(Long idTask) {
        TaskPlanning planning = taskPlanningDao.getTaskPlanningByTask(idTask);
        PeriodDTO dto = null;
        if (planning!=null) {
        	dto = new PeriodDTO(planning.getStart().toString(), planning.getEnd().toString());
		}
        return dto;
	}

	public TaskDTO createTask(TaskDTO task) {
    	Task newTask = new Task(null, task.getName());
    	newTask.setDescription(task.getDescription());
    	newTask = taskDao.createTask(newTask);
    	return new TaskDTO(
			newTask.getId(),
			newTask.getName(),
			newTask.getDescription()
		);
	}

	public Boolean updateTask(TaskDTO task) {
		Task newTask = taskDao.getTaskById(task.getId());
		newTask.setName(task.getName());
        newTask.setDescription(task.getDescription());
    	return taskDao.updateTask(newTask);
	}

	public Boolean deleteTask(Long idTask) {
    	return taskDao.deleteTask(idTask);
	}

}
