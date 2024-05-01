package com.policarp.journal.models;

import java.util.Objects;

public class SchoolParticipant extends Person{
    public Long ID;
    public School.Position Position;

    public SchoolParticipant() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolParticipant that = (SchoolParticipant) o;
        return ID.equals(that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }
}
