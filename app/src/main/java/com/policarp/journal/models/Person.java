package com.policarp.journal.models;

public class Person {
    public String FullName;
    public Person(String fullName) {
        FullName = fullName;
    }
    public Person(Person person) {this(person.FullName);}
}
