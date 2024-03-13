package com.policarp.journal.models;

public class Parent extends Person{
    public String PhoneNumber;
    public Parent(String fullName, String number) {
        super(fullName);
        PhoneNumber = number;
    }

}
