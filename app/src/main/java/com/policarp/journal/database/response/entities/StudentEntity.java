package com.policarp.journal.database.response.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentEntity {
    private Long studentId;
    private Long participantId;
    private Long attachedClassId;
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", participantId=" + participantId +
                ", attachedClassId=" + attachedClassId +
                '}';
    }
}
