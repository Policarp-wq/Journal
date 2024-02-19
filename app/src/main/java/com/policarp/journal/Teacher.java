package com.policarp.journal;

import android.nfc.FormatException;

public class Teacher extends SchoolParticipant{
    public School.Subjects Qualification;
    public Teacher(SchoolParticipant participant, School.Subjects qualification) {
        super(participant);
        Qualification = qualification;
    }
    public void setMark(Student student, School.Subjects sbj, Mark mark) throws FormatException {
        JournalRequest.setMark(this, student, sbj, mark);
    }

}
