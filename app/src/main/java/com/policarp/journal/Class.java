package com.policarp.journal;

import java.util.ArrayList;

public class Class {
    public String ClassName;
    public Teacher HeadTeacher;
    private ArrayList<Student> Students;
    public int getCount(){
        return Students.size();
    }

    public ArrayList<Student> getStudents() {
        return Students;
    }
    public void addStudent(Student s){
        Students.add(s);
    }

    public Class(String className, Teacher headTeacher) {
        ClassName = className;
        HeadTeacher = headTeacher;
        Students = new ArrayList<>();
    }
    public Class(String className){
        this(className, null);
    }
}
