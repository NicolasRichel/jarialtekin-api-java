package fr.devnr.jarialtekinapi.model;

import java.util.Objects;


public class IdentifiableModel implements Model {

    protected Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdentifiableModel model = (IdentifiableModel) o;
        return Objects.equals(id, model.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
