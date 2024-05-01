package com.policarp.journal.database.response.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//Этот класс должен описывать хранимый объект в таблице.
// JSON запрос должен содержать поля описанные в этом классе,
// а не в таблице(они могут быть разными)
@Setter
@Getter
@NoArgsConstructor
public class UserEntity {
    private Long userId;
    private String login;
    private int hash;
    private Long participantId;

    public UserEntity(String login, String password) {
        this.login = login;
        this.hash = password.hashCode();
    }

    public UserEntity(String login, int hash, Long participantId) {
        this.login = login;
        this.hash = hash;
        this.participantId = participantId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", login='" + login + '\'' +
                ", hash=" + hash +
                ", participantId=" + participantId +
                '}';
    }
}
