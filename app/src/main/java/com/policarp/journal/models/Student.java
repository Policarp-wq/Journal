package com.policarp.journal.models;

import java.util.ArrayList;

public class Student extends SchoolParticipant{
    public Student(){}
    public ArrayList<SubjectStatistic> Subjects;
    public String AttachedClass;
    public Student(SchoolParticipant participant) {
        super(participant);
        Subjects = School.getAvailableSubjects();
    }
}
