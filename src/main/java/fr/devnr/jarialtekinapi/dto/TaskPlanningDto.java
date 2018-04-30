package fr.devnr.jarialtekinapi.dto;

import java.time.LocalDateTime;

import fr.devnr.jarialtekinapi.model.Task;

public class TaskPlanningDto {
	
	//  Fields
	// ========
	
	private Task task;
	private LocalDateTime start;
	private LocalDateTime end;
	
	//  Constructors
	// ==============
	
	public TaskPlanningDto(Task task, LocalDateTime start, LocalDateTime end) {
		this.task = task;
		this.start = start;
		this.end = end;
	}

	//  Getters & Setters
	// ===================
	
	// Get & Set task
	public Task getTask() { return task; }
	public void setTask(Task task) { this.task = task; }
	// Get & Set startDateTime
	public LocalDateTime getStart() { return start; }
	public void setStart(LocalDateTime start) { this.start = start; }
	// Get & Set endDateTime
	public LocalDateTime getEnd() { return end; }
	public void setEnd(LocalDateTime end) { this.end = end; }
	
}
