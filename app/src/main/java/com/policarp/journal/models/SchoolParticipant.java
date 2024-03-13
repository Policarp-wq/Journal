package com.policarp.journal.models;

import java.util.Objects;

public class SchoolParticipant extends Person{
    public String ID;
    public School.Position Position;
    public UserInfo User;
    public SchoolParticipant(Person person, UserInfo u, String ID, School.Position position) {
        super(person);
        User = u;
        this.ID = ID;
        Position = position;
    }

    public SchoolParticipant(SchoolParticipant participant){
        this(participant, participant.User, participant.ID, participant.Position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolParticipant that = (SchoolParticipant) o;
        return ID.equals(that.ID) && User.equals(that.User);
    }
    public boolean equals(UserInfo u) {
        if (u == null) return false;
        return User.equals(u);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }
}
