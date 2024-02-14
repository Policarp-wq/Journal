package com.policarp.journal;

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
}
