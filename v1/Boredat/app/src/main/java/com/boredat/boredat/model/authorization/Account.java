package com.boredat.boredat.model.authorization;

/**
 * Created by Liz on 2/7/2016.
 */
public class Account {
    private String userId;
    private String password;

    public Account(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o== null) || (o.getClass() != this.getClass())) return false;

        // object is an Account
        Account a = (Account) o;
        return userId.equals(a.getUserId());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (null == userId ? 0 : userId.hashCode());
        return hash;
    }
}
