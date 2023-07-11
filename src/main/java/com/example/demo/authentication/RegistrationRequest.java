package com.example.demo.authentication;

import com.example.demo.entities.UserEntity;

public class RegistrationRequest {
    private String domain;
    private UserEntity user;

    public RegistrationRequest(String domain, String publicKey, UserEntity user) {
        this.domain = domain;
        this.user = user;
    }

    public String getDomainName() {
        return domain;
    }

    public void setDomainName(String domainName) {
        this.domain = domainName;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationRequest that)) return false;

        if (getDomainName() != null ? !getDomainName().equals(that.getDomainName()) : that.getDomainName() != null)
            return false;
        return getUser() != null ? getUser().equals(that.getUser()) : that.getUser() == null;
    }

    @Override
    public int hashCode() {
        int result = getDomainName() != null ? getDomainName().hashCode() : 0;
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "domainName='" + domain + '\'' +
                ", user=" + user +
                '}';
    }
}
