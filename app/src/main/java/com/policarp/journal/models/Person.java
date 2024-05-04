package com.policarp.journal.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Person {
    public String name;

    public Person(String name) {
        this.name = name;
    }
}
