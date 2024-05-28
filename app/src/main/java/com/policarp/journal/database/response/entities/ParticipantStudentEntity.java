package com.policarp.journal.database.response.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParticipantStudentEntity {
    private SchoolParticipantEntity participant;
    private StudentEntity student;
}
