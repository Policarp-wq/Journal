package com.policarp.journal.database.response.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherEntity {
    private Long teacherId;
    private Long participantId;
    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", participantId=" + participantId +
                '}';
    }
}
