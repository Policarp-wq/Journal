package com.policarp.journal.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class School {
    private Long schoolId;
    private String name;
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
}
