package com.policarp.journal;

import android.nfc.FormatException;

public class Teacher extends SchoolParticipant{
    public Class ClassHead = null;
    public School.Subjects Qualification;
    public Teacher(SchoolParticipant participant, Class classHead, School.Subjects qualification) {
        super(participant);
        ClassHead = classHead;
        Qualification = qualification;
    }
    public void setMark(Student student, School.Subjects sbj, Mark mark) throws FormatException {
        JournalRequest.setMark(this, student, sbj, mark);
    }

}
