package fr.devnr.jarialtekinapi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Task extends IdentifiableModel {

	private String name;
	private String description;
	private Priority priority;
	private Status status;
	private Task parentTask;
	private List<Task> subTasks;
	private List<Task> dependencies;


	public Task(Long id, String name, String description, Priority priority, Status status) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.parentTask = null;
		this.subTasks = new ArrayList<>();
		this.dependencies = new ArrayList<>();
	}

	public Task(Long id, String name) {
		this(id, name, "", Priority.NORMAL, Status.NONE);
	}

	public Task(String name) {
		this(null, name, "", Priority.NORMAL, Status.NONE);
	}


	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public Priority getPriority() { return priority; }
	public void setPriority(Priority priority) { this.priority = priority; }
	public Status getStatus() { return status; }
	public void setStatus(Status status) { this.status = status; }
	public Task getParentTask() { return parentTask; }
	public void setParentTask(Task parentTask) { this.parentTask = parentTask; }

	public List<Task> getSubTasks() { return subTasks; }
	public Boolean addSubTask(Task task) { return this.subTasks.add(task); }
	public Boolean removeSubTask(Task task) { return this.subTasks.remove(task); }
	public Boolean addSubTasks(Collection<Task> tasks) { return this.subTasks.addAll(tasks); }
	public Boolean removeSubTasks(Collection<Task> tasks) { return this.subTasks.removeAll(tasks); }

	public List<Task> getDependencies() { return dependencies; }
	public Boolean addDependency(Task task) { return this.dependencies.add(task); }
	public Boolean removeDependency(Task task) { return this.dependencies.remove(task); }
	public Boolean addDependencies(Collection<Task> tasks) { return this.dependencies.addAll(tasks); }
	public Boolean removeDependencies(Collection<Task> tasks) { return this.dependencies.removeAll(tasks); }

}
