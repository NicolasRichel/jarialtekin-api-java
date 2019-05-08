package fr.devnr.jarialtekinapi.model;


public enum Status {

	NONE(-1),
	TODO(0),
	DOING(1),
	DONE(2);

	private Integer value;


	Status(Integer value) {
		this.value = value;
	}


	public Integer getValue() { return this.value; }

	public static Status valueOf(int n) {
		switch (n) {
			case -1: return Status.NONE;
			case 0: return Status.TODO;
			case 1: return Status.DOING;
			case 2: return Status.DONE;
			default: return Status.NONE;
		}
	}

}
