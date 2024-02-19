package com.policarp.journal;

import java.util.Objects;

public class User {
    public final String Login;
    public final int PasswordHash;
    public SchoolParticipant participant;

    public User(String login, String password, SchoolParticipant participant) {
        Login = login;
        PasswordHash = getHashcode(password);
        this.participant = participant;
    }

    private int getHashcode(String password) {
        final int mod = (int) 1e7;
        int p = 207;
        int cur = 1;
        int res = 0;
        for(int i = 0; i < password.length(); ++i){
            res = (res + (password.charAt(i) * cur) % mod) % mod;
            cur = (cur * p)% mod;
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return PasswordHash == user.PasswordHash && Login.equals(user.Login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Login, PasswordHash);
    }
}
