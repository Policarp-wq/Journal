package com.policarp.journal.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchoolSubjectLine {
    private School.Subjects subject;
    private String homeWork;
    private Mark mark;

    public SchoolSubjectLine(School.Subjects subject, String homeWork) {
        this.subject = subject;
        this.homeWork = homeWork;
    }

    public SchoolSubjectLine(School.Subjects subject) {
        this(subject, "");
    }
}
