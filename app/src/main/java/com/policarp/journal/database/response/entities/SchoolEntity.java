package com.policarp.journal.database.response.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SchoolEntity {
    private Long schoolId;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
