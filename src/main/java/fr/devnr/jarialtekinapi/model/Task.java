package fr.devnr.jarialtekinapi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Task {
	
	//  Fields
	// ========
	
	private Long id;
	private String name;
	private String description;
	private Priority priority;
	private Status status;
	private Task parentTask;
	private List<Task> subTasks;
	private List<Task> dependencies;
	
	//  Constructors
	// ==============
	
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
	
	//  Getters & Setters
	// ===================

	// Get & Set id
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	// Get & Set name
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	// Gat & Set description
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	// Get & Set priority
	public Priority getPriority() { return priority; }
	public void setPriority(Priority priority) { this.priority = priority; }
	// Get & Set status
	public Status getStatus() { return status; }
	public void setStatus(Status status) { this.status = status; }
	// Get & Set parentTask
	public Task getParentTask() { return parentTask; }
	public void setParentTask(Task parentTask) { this.parentTask = parentTask; }
	// Get subTasks
	public List<Task> getSubTasks() { return subTasks; }
	// Get dependencies
	public List<Task> getDependencies() { return dependencies; }
	
	//  Sub Tasks Handler Methods
	// ===========================
	
	// Add or Remove one task to subTasks
	public Boolean addSubTask(Task task) { return this.subTasks.add(task); }
	public Boolean removeSubTask(Task task) { return this.subTasks.remove(task); }
	// Add or Remove multiple tasks from subTasks
	public Boolean addSubTasks(Collection<Task> tasks) { return this.subTasks.addAll(tasks); }
	public Boolean removeSubTasks(Collection<Task> tasks) { return this.subTasks.removeAll(tasks); }
	
	//  Dependencies Handler Methods
	// ==============================
	
	// Add or Remove one task to dependencies
	public Boolean addDependency(Task task) { return this.dependencies.add(task); }
	public Boolean removeDependency(Task task) { return this.dependencies.remove(task); }
	// Add or Remove multiple tasks from dependencies
	public Boolean addDependencies(Collection<Task> tasks) { return this.dependencies.addAll(tasks); }
	public Boolean removeDependencies(Collection<Task> tasks) { return this.dependencies.removeAll(tasks); }

	//  Equals and HashCode
	// =====================

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Task task = (Task) o;

		return id != null ? id.equals(task.id) : task.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
