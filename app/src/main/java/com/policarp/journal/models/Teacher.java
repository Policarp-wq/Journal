package com.policarp.journal.models;

import android.nfc.FormatException;

import java.util.ArrayList;

public class Teacher extends SchoolParticipant{
    public School.Subjects Qualification;
    public ArrayList<Class> Classes;
    public Teacher(SchoolParticipant participant, School.Subjects qualification) {
        super(participant);
        Classes = new ArrayList<>();
        Qualification = qualification;
    }
    public void setMark(Student student, School.Subjects sbj, Mark mark) throws FormatException {
        JournalRequest.setMark(this, student, sbj, mark);
    }

}
