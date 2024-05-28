package com.policarp.journal.database.response.entities;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MarkEntity {
    private Long markId;
    private String subjectName;
    private Integer val;
    private Long studentId;
    private Long teacherId;
    private Date markDate;

    public MarkEntity(String subjectName, Integer val, Long studentId, Long teacherId, Date markDate) {
        this.subjectName = subjectName;
        this.val = val;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.markDate = markDate;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "markId=" + markId +
                ", subjectName='" + subjectName + '\'' +
                ", val=" + val +
                ", studentId=" + studentId +
                ", teacherId=" + teacherId +
                ", markDate=" + markDate +
                '}';
    }
}
