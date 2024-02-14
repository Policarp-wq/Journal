package com.policarp.journal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class School {
    public final String Name;

    public School(String name) {
        Name = name;
        SchoolParticipants = new ArrayList<>();
        Classes = new ArrayList<>();
        Teachers = new ArrayList<>();
        Students = new ArrayList<>();
        Users = new ArrayList<>();
    }

    public School(String name, ArrayList<SchoolParticipant> schoolParticipants, ArrayList<Class> classes, ArrayList<Teacher> teachers, ArrayList<Student> students, ArrayList<User> users) {
        Name = name;
        SchoolParticipants = schoolParticipants;
        Classes = classes;
        Teachers = teachers;
        Students = students;
        Users = users;
    }

    public enum Position {
        Principal,
        Teacher,
        Student,
        SecurityGuard,
        MaintenanceStaff,
        Guest
    }
    public enum Subjects{
        Maths,
        Russian,
        English,
        ICT,
        PE
    }
    private ArrayList<SchoolParticipant> SchoolParticipants;
    private ArrayList<Class> Classes;
    private ArrayList<Teacher> Teachers;
    private ArrayList<Student> Students;
    private ArrayList<User> Users;
    public void hireTeacher(Teacher teacher){
        teacher.setCardID(generateID(teacher.Position));
        Teachers.add(teacher);
    }
    public void addClass(Class c){
        Classes.add(c);
    }
    public void acceptStudent(Student student){
        student.setCardID(generateID(student.Position));
        Students.add(student);
    }
    public SchoolParticipant registerParticipant(String p, Position position){
        if(position == null)
            position = Position.Guest;
        SchoolParticipant participant = new SchoolParticipant(p, generateID(position), position);
        SchoolParticipants.add(participant);
        return participant;
    }
    public SchoolParticipant registerParticipant(String p){
        return registerParticipant(p, null);
    }

    private String generateID(Position position) {
        final int length = 7;
        String res = "";
        int arrSize = 0;
        if(position == Position.Principal){
            res = "0";
            arrSize = 0;
        }
        if(position == Position.Teacher){
            res = "1";
            arrSize = Teachers.size();
        }
        if(position == Position.Student){
            res = "2";
            arrSize = Students.size();
        }
        else {
            res = "3";
            arrSize = SchoolParticipants.size();
        }
        String additional = getAdditional(length, Integer.toString(arrSize).length() + 1);
        res += additional + arrSize;
        return res;
    }

    private String getAdditional(int mxLength, int size) {
        StringBuilder builder = new StringBuilder();
        for(int i = size; i < mxLength; ++i)
            builder.append("0");
        return builder.toString();
    }
    public User registerUser(String login, String password, SchoolParticipant participant){
        User user = new User(login,password,participant);
        Users.add(user);
        return user;
    }
    public SchoolParticipant getParticipant(String login, String password){
        User seeking = new User(login, password, null);
        for(User u : Users){
            if(seeking.equals(u))
                return u.participant;
        }
        return null;
    }
    public static boolean isMarkCorrect(Mark mark){
        return mark.Value >= 2 && mark.Value <= 5;
    }
    public String toJson(){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(this);
    }
    public static School fromJson(String json){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, School.class);
    }

}
