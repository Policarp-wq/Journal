package com.policarp.journal;

import java.util.ArrayList;

public class Class {
    public String Number;
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
    public ArrayList<Parent> getParents(){
        ArrayList<Parent> parents = new ArrayList<>();
        for (Student student: getStudents()) {
            parents.add(student.FirstParent);
            parents.add(student.SecondParent);
        }
        return parents;
    }

    public Class(String number, Teacher headTeacher) {
        Number = number;
        HeadTeacher = headTeacher;
        Students = new ArrayList<>();
    }
}
