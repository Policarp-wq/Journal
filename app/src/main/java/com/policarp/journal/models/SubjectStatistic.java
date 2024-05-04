package com.policarp.journal.models;

import java.util.ArrayList;

public class SubjectStatistic {
    public School.Subjects getName() {
        return Name;
    }
    public School.Subjects Name;
    private ArrayList<Mark> marks = new ArrayList<>();

    public SubjectStatistic(School.Subjects name) {
        Name = name;
    }
    public double getAverage(){
        int sm = 0;
        int cnt = 0;
        for (Mark mark: marks) {
            sm += mark.getVal();
            ++cnt;
        }
        if(cnt == 0)
            return 0;
        return sm * 1.0 / cnt;
    }
    public String getMarks(){
        StringBuilder sb = new StringBuilder();
        for(Mark m : marks){
            sb.append(m.getVal() + " ");
        }
        return sb.toString();
    }
}
