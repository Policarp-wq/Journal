package com.policarp.journal.models;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Mark {
    private Long markId;
    private String subjectName;
    private Integer val;
    private Integer studentId;
    private Integer teacherId;
    private Date markDate;
}
