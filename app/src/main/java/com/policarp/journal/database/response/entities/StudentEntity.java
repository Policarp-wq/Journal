package com.policarp.journal.database.response.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentEntity {
    private Integer studentId;
    private Integer participantId;
    private Integer attachedClassId;
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", participantId=" + participantId +
                ", attachedClassId=" + attachedClassId +
                '}';
    }
}
