package com.policarp.journal;

import java.util.ArrayList;

public class Student extends SchoolParticipant{
    public ArrayList<SubjectStatistic> Subjects;
    String AttachedClass;
    public Student(SchoolParticipant participant) {
        super(participant);
        Subjects = SubjectStatistic.AvailableSubjects;
    }
}
