package com.policarp.journal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubjectStatistic {
    public School.Subjects Name;
    private ArrayList<Mark> marks = new ArrayList<>();

    public SubjectStatistic(School.Subjects name) {
        Name = name;
    }

    public ArrayList<Mark> getMarksCopy(SchoolParticipant caller) {
        if(AccessLevel.getLevel(caller.Position).ordinal() >= AccessLevel.Levels.Low.ordinal())
            return new ArrayList<>(marks);
        return null;
    }
    public ArrayList<Mark> getChangeableMarks(SchoolParticipant caller){
        if(AccessLevel.getLevel(caller.Position).ordinal() >= AccessLevel.Levels.Medium.ordinal())
            return marks;
        return null;
    }
    public double getAverage(){
        int sm = 0;
        int cnt = 0;
        for (Mark mark: marks) {
            sm += mark.Value;
            ++cnt;
        }
        if(cnt == 0)
            return 0;
        return sm * 1.0 / cnt;
    }

}
