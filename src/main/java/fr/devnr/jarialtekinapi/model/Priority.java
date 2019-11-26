package fr.devnr.jarialtekinapi.model;


public enum Priority implements Model {

	LOW(-1),
	NORMAL(0),
	HIGH(1);

	private Integer value;


	Priority(Integer value) {
		this.value = value;
	}


	public Integer getValue() { return this.value; }
	
	public static Priority valueOf(int n) {
		switch (n) {
			case -1: return Priority.LOW;
			case 1: return Priority.HIGH;
			case 0:
			default: return Priority.NORMAL;
		}
	}

}
