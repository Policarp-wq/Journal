package com.policarp.journal.models;

import java.util.Objects;

public class UserInfo {
    public String Login;
    public int Hash;

    public UserInfo(String login, int hash) {
        Login = login;
        Hash = hash;
    }
    public UserInfo(String login, String password) {
        Login = login;
        Hash = password.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Hash == userInfo.Hash && Login.equals(userInfo.Login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Login, Hash);
    }
}
