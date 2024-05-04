package com.policarp.journal.database.response.entities;

import com.policarp.journal.models.School;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SchoolParticipantEntity {
    private Long participantId;
    private String name;
    private String position;
    private Long schoolId;
    @Override
    public String toString() {
        return "SchoolParticipant{" +
                "participantId=" + participantId +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", schoolId=" + schoolId +
                '}';
    }
    public School.Position getSchoolPosition(){
        for(School.Position pos : School.Position.values()){
            if(pos.name().equals(position))
                return pos;
        }
        return null;
    }
}
