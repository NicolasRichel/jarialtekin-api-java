package fr.devnr.jarialtekinapi.model;


public enum Priority {

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
			case 0: return Priority.NORMAL;
			case 1: return Priority.HIGH;
			default: return Priority.NORMAL;
		}
	}

}
