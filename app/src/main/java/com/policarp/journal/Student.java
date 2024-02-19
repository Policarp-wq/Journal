package com.policarp.journal;

import java.util.ArrayList;

public class Student extends SchoolParticipant{
    public Parent FirstParent;
    public Parent SecondParent;
    public ArrayList<SubjectStatistic> Subjects;
    public String ClassName = "";
    public Student(SchoolParticipant participant, Parent firstParent, Parent secondParent) {
        super(participant);
        FirstParent = firstParent;
        SecondParent = secondParent;
        Subjects = new ArrayList<>();
    }
}
