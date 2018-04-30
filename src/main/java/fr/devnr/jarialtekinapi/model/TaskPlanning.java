package fr.devnr.jarialtekinapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TaskPlanning {
	
	//  Fields
	// ========
	
	private Task task;
	private LocalDateTime start;
	private LocalDateTime end;
	
	//  Constructors
	// ==============
	
	public TaskPlanning(Task task, LocalDateTime start, LocalDateTime end) {
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
	public LocalDate getStartDate() { return this.start.toLocalDate(); }
	public LocalTime getStartTime() { return this.start.toLocalTime(); }
	// Get & Set endDateTime
	public LocalDateTime getEnd() { return end; }
	public void setEnd(LocalDateTime end) { this.end = end; }
	public LocalDate getEndDate() { return this.end.toLocalDate(); }
	public LocalTime getEndTime() { return this.end.toLocalTime(); }
	
}
