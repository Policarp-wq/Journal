package com.policarp.journal;

import java.util.Objects;

public class SchoolParticipant extends Person{
    public void setCardID(String cardID) {
        CardID = cardID;
    }

    public String CardID;
    public School.Position Position;

    public SchoolParticipant(String fullName, String cardID, School.Position position) {
        super(fullName);
        CardID = cardID;
        Position = position;
    }

    public SchoolParticipant(Person person, String cardID, School.Position position) {
        super(person);
        CardID = cardID;
        Position = position;
    }
    public SchoolParticipant(Person person, School.Position position) {
        this(person, "", position);
    }
    public SchoolParticipant(SchoolParticipant participant){
        this(participant.FullName, participant.CardID, participant.Position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolParticipant that = (SchoolParticipant) o;
        return CardID.equals(that.CardID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CardID);
    }
}
