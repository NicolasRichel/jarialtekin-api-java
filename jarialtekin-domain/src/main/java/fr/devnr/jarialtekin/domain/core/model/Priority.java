package fr.devnr.jarialtekin.domain.core.model;

public enum Priority {

    LOW(-1, "Low"),
    NORMAL(0, "Normal"),
    HIGH(1, "High");

    private Integer index;
    private String name;

    Priority(Integer index, String name) {
        this.index = index;
        this.name = name;
    }

}
