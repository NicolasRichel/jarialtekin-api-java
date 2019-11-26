package fr.devnr.jarialtekinapi.model;

import java.time.LocalDate;


public class Project extends IdentifiableModel {

	private String name;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;


	public Project(Long id, String name, String description, LocalDate startDate, LocalDate endDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate= endDate;
	}

	public Project(Long id, String name) {
		this(id, name, "", LocalDate.now(), LocalDate.now().plusMonths(1));
	}

	public Project(String name) {
		this(null, name, "", LocalDate.now(), LocalDate.now().plusMonths(1));
	}


	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public LocalDate getStartDate() { return startDate; }
	public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
	public LocalDate getEndDate() { return endDate; }
	public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

}
