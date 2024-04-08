package com.policarp.journal.models;

public class SchoolSubjectLine {
    public School.Subjects Subject;
    public String HomeWork;
    public Mark Mark;

    public SchoolSubjectLine(School.Subjects subject, String homeWork) {
        Subject = subject;
        HomeWork = homeWork;
    }

    public SchoolSubjectLine(School.Subjects subject) {
        this(subject, "");
    }
}
