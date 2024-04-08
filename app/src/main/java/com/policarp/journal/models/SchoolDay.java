package com.policarp.journal.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class SchoolDay {
    public static final String SUNDAY = "Воскресенье";
    public static final String SATURDAY = "Суббота";
    public static final String FRIDAY = "Пятница";
    public static final String THURSDAY = "Четверг";
    public static final String WEDNESDAY = "Среда";
    public static final String TUESDAY = "Вторник";
    public static final String MONDAY = "Понедельник";
    public LocalDate Date;
    public ArrayList<SchoolSubjectLine> Lines;

    public DayOfWeek getDayOfWeek(){
        return Date.getDayOfWeek();
    }

    public String getDisplayName(){
        String res = "";
        switch (getDayOfWeek()){
            case MONDAY:
                res = MONDAY;
                break;
            case TUESDAY:
                res = TUESDAY;
                break;
            case WEDNESDAY:
                res = WEDNESDAY;
                break;
            case THURSDAY:
                res = THURSDAY;
                break;
            case FRIDAY:
                res = FRIDAY;
                break;
            case SATURDAY:
                res = SATURDAY;
                break;
            case SUNDAY:
                res = SUNDAY;
                break;
        }
        return res;
    }

    public SchoolDay(LocalDate date, ArrayList<SchoolSubjectLine> lines) {
        Date = date;
        Lines = lines;
    }
    public void setHomeWork(int pos, String hw){
        if(pos > Lines.size())
            throw new ArrayIndexOutOfBoundsException(pos + "is out of bounds for lines array in " + this);
        Lines.get(pos).HomeWork = hw;
    }
    public void setMark(int pos, Mark mark){
        Lines.get(pos).Mark = mark;
    }

    @Override
    public String toString() {
        return "SchoolDay{" +
                "Date=" + Date + " " +
                "Subjects num = " + Lines.size() +
                '}';
    }
}
