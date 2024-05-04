package com.policarp.journal.models;

import java.util.Objects;

public class UserInfo {
    public String login;
    public int hash;

    public UserInfo(String login, int hash) {
        this.login = login;
        this.hash = hash;
    }
    public UserInfo(String login, String password) {
        this.login = login;
        hash = password.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return hash == userInfo.hash && login.equals(userInfo.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, hash);
    }
}
