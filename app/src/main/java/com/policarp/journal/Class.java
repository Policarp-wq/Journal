package com.policarp.journal;

import java.util.ArrayList;

public class Class {
    public String Number;
    public Teacher HeadTeacher;
    public ArrayList<Student> Students;

    public ArrayList<Student> getStudents() {
        return Students;
    }
    public ArrayList<Parent> getParents(){
        ArrayList<Parent> parents = new ArrayList<>();
        for (Student student: getStudents()) {
            parents.add(student.FirstParent);
            parents.add(student.SecondParent);
        }
        return parents;
    }

    public Class(String number, Teacher headTeacher, ArrayList<Student> students) {
        Number = number;
        HeadTeacher = headTeacher;
        Students = students;
        headTeacher.ClassHead = this;
    }
}
