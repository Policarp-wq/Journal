package com.policarp.journal;

import android.nfc.FormatException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JournalRequest {
    public static ArrayList<Student> Students;
    public static void setMark(SchoolParticipant participant, Student student, School.Subjects subject, Mark mark) throws FormatException {
        if(!School.isMarkCorrect(mark))
            throw new FormatException("Invalid mark! : " + mark.Value);
        Runnable set = () -> {
            if(!(Students.contains(student) && student.Subjects.contains(subject)))
                return;
            try {
                synchronized (student){
                    student.wait();
                    student.Subjects.get(student.Subjects.indexOf(subject)).getChangeableMarks(participant).add(mark);
                    student.notify();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        new Thread(set).start();
    }
    public static ArrayList<Mark> getStudentMarksCopy(SchoolParticipant participant, Student student, School.Subjects subject){
        final ArrayList<ArrayList<Mark>> marks = new ArrayList<>();
        marks.add(new ArrayList<>());
        Runnable get = () -> {
            if(!(Students.contains(student) && student.Subjects.contains(subject)))
                return;
            int subjInd = student.Subjects.indexOf(subject);
            marks.set(0, student.Subjects.get(subjInd).getMarksCopy(participant));
        };
        Thread work = new Thread(get);
        work.start();
        try {
            work.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return marks.get(0);
    }
}
