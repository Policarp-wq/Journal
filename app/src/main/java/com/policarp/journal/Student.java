package com.policarp.journal;

import java.util.ArrayList;

public class Student extends SchoolParticipant{
    public Parent FirstParent;
    public Parent SecondParent;
    public ArrayList<SubjectStatistic> Subjects;
    public Student(Person person, School.Position position, Parent firstParent, Parent secondParent) {
        super(person, position);
        FirstParent = firstParent;
        SecondParent = secondParent;
        Subjects = new ArrayList<>();
    }
}
