package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.HashSet;
import java.util.Objects;

@Entity
public class JwtToken {
    @Id
    private String token;
    @ManyToOne
    private UserEntity user;
    private boolean expired;
    private boolean revoked;

    public JwtToken(String token, UserEntity user) {
        this.token = token;
        this.user = user;
        this.expired = false;
        this.revoked = false;
    }

    public JwtToken() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtToken jwtToken = (JwtToken) o;
        return expired == jwtToken.expired && revoked == jwtToken.revoked && Objects.equals(token, jwtToken.token) && Objects.equals(user, jwtToken.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, user, expired, revoked);
    }

    @Override
    public String toString() {
        return "JwtToken{" +
                "token='" + token + '\'' +
                ", user=" + user +
                ", expired=" + expired +
                ", revoked=" + revoked +
                '}';
    }
}
