package com.policarp.journal.models;

public class OldSchool {
//    public final String name;
//  //  public final SchoolParticipant SchoolPrincipal;
//    public static final int CLASSCAPACITY = 1;
//
//    public School(String name) {
//        this.name = name;
//        SchoolParticipants = new ArrayList<>();
//        learningClasses = new ArrayList<>();
//        Teachers = new ArrayList<>();
//        Students = new ArrayList<>();
//    }
//
//    public School(String name, ArrayList<SchoolParticipant> schoolParticipants,
//                  ArrayList<LearningClass> learningClasses, ArrayList<Teacher> teachers, ArrayList<Student> students, SchoolParticipant principal) {
//        this.name = name;
//        SchoolParticipants = schoolParticipants;
//        this.learningClasses = learningClasses;
//        Teachers = teachers;
//        Students = students;
//  //      SchoolPrincipal = principal;
//    }
//
//    public enum Position {
//        Principal,
//        Teacher,
//        Student,
//        SecurityGuard,
//        MaintenanceStaff,
//        Guest
//    }
//    public enum Subjects{
//        Maths,
//        Russian,
//        English,
//        ICT,
//        PE
//    }
//    public static ArrayList<SubjectStatistic> getAvailableSubjects(){
//        ArrayList<SubjectStatistic> sbj = new ArrayList<>();
//        for (School.Subjects s : School.Subjects.values()){
//            sbj.add(new SubjectStatistic(s));
//        }
//        return sbj;
//    }
//    private ArrayList<SchoolParticipant> SchoolParticipants;
//    private ArrayList<LearningClass> learningClasses;
//    private ArrayList<Teacher> Teachers;
//    private ArrayList<Student> Students;
//    private Teacher hireTeacher(SchoolParticipant participant){
//        if(participant.Position != Position.Teacher)
//            return null;
//        Teacher teacher = new Teacher(participant, Subjects.Maths);
//        Teachers.add(teacher);
//        return attachTeacher(teacher);
//    }
//    private Student applyStudent(SchoolParticipant participant){
//        if(participant.Position != Position.Student)
//            return null;
//        Student s = new Student(participant);
//        if(learningClasses.size() == 0)
//            addClass("1B");
//        for (LearningClass c: learningClasses) {
//            if(c.getCount() < CLASSCAPACITY){
//                c.addStudent(s);
//                s.AttachedClass = c.ClassName;
//                break;
//            }
//        }
//        Students.add(s);
//        return s;
//    }
//    public Teacher attachTeacher(Teacher teacher){
//        for(LearningClass c : learningClasses){
//            if(c.HeadTeacher == null){
//                c.HeadTeacher = teacher;
//                teacher.learningClasses.add(c);
//                return teacher;
//            }
//        }
//        Random r = new Random();
//        addClass(Integer.toString(r.nextInt() % 11 + 1), teacher);
//        return teacher;
//    }
//    public void addClass(String name){
//        learningClasses.add(new LearningClass(name));
//    }
//    public void addClass(String name, Teacher teacher){
//        learningClasses.add(new LearningClass(name, teacher));
//    }
//    public SchoolParticipant registerParticipant(Person p, UserInfo u, Position position){
//        if(position == null)
//            position = Position.Guest;
//        SchoolParticipant participant = new SchoolParticipant(p, u, generateID(position), position);
//        SchoolParticipants.add(participant);
//        switch(participant.Position){
//            case Student:
//                return applyStudent(participant);
//            case Teacher:
//                return hireTeacher(participant);
//        }
//        return participant;
//    }
//
//    private String generateID(Position position) {
//        String res = "";
//        int arrSize = 0;
//        if(position == Position.Principal){
//            res = "0";
//            arrSize = 0;
//        }
//        if(position == Position.Teacher){
//            res = "1";
//            arrSize = Teachers.size();
//        }
//        if(position == Position.Student){
//            res = "2";
//            arrSize = Students.size();
//        }
//        else {
//            res = "3";
//            arrSize = SchoolParticipants.size();
//        }
//        String additional = getAdditional(IDlength, Integer.toString(arrSize).length() + 1);
//        res += additional + arrSize;
//        return res;
//    }
//
//    private static String getAdditional(int mxLength, int size) {
//        StringBuilder builder = new StringBuilder();
//        for(int i = size; i < mxLength; ++i)
//            builder.append("0");
//        return builder.toString();
//    }
//    public boolean containsUser(UserInfo info){
//        for(SchoolParticipant participant : SchoolParticipants){
//            if(info.equals(participant.User))
//                return true;
//        }
//        return false;
//    }
//    public SchoolParticipant getParticipant(String login){
//        //Пытаемся вернуть конкретный класс для позиции
//        for(SchoolParticipant participant : Teachers){
//            if(login.equals(participant.User.login))
//                return participant;
//        }
//        for(SchoolParticipant participant : Students){
//            if(login.equals(participant.User.login))
//                return participant;
//        }
//        //Если не нашли конкретный, то пытаемся хоть какой-то
//        for(SchoolParticipant participant : SchoolParticipants){
//            if(login.equals(participant.User.login))
//                return participant;
//        }
//        return null;
//    }
//    public static boolean isMarkCorrect(Mark mark){
//        return mark.Value >= 2 && mark.Value <= 5;
//    }
//    public String toJson(){
//        return JSONable.toJSON(this);
//    }
//    public static School fromJson(String json){
//        return (School)JSONable.fromJSON(json, School.class);
//    }
//    private SchoolParticipant updateParticipant(SchoolParticipant participant){
//        SchoolParticipant seeking = findParticipant(participant.participantId, SchoolParticipants);
//        if(seeking == null)
//            return null;
//        SchoolParticipants.remove(seeking);
//        SchoolParticipants.add(participant);
//        switch(participant.Position){
//            case Student:
//                seeking = findParticipant(participant.participantId, Students);
//                if(seeking == null)
//                    participant = applyStudent(participant);
//                else{
//                    Students.remove(seeking);
//                    Students.add((Student)participant);
//                }
//                break;
//            case Teacher:
//                seeking = findParticipant(participant.participantId, Teachers);
//                if(seeking == null)
//                    participant = hireTeacher(participant);
//                else{
//                    Teachers.remove(seeking);
//                    Teachers.add((Teacher)participant);
//                }
//                break;
//        }
//        return participant;
//    }
//    private <T extends SchoolParticipant> T findParticipant(String ID, ArrayList<T> list){
//        for(T p : list){
//            if(ID.equals(p.participantId)){
//                return p;
//            }
//        }
//        return null;
//    }
//    public boolean containsParticipant(SchoolParticipant participant){
//        for(SchoolParticipant p: SchoolParticipants){
//            if(participant.equals(p))
//                return true;
//        }
//        return false;
//    }
//    public boolean containsLogin(String login){
//        for(SchoolParticipant p: SchoolParticipants){
//            if(p.User != null && p.User.login.equals(login))
//                return true;
//        }
//        return false;
//    }
//    public static Position getPositionFromString(String pos){
//        School.Position position = null;
//        for(School.Position p : School.Position.values()){
//            if(p.toString().equals(pos))
//                position = p;
//        }
//        return position;
//    }
//

}
