package com.policarp.journal.database.response.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LearningClassEntity {
    private Long classId;
    private String className;
    private Integer schoolId;
    @Override
    public String toString() {
        return "Class{" +
                "id=" + classId +
                ", className='" + className + '\'' +
                ", schoolId=" + schoolId +
                '}';
    }
}
