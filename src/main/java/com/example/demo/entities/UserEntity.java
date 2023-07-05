package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;
import java.util.Objects;

@Entity
public class UserEntity {
    @Id
    public String email;
    public String hashedPassword;
    public String firstName;
    public String lastName;
    public String address;
    public String zipCode;
    public String gender;

    public UserEntity(String email, String hashedPassword, String firstName,
                      String lastName, String address, String zipCode,
                      String gender) {
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zipCode = zipCode;
        this.gender = gender;
    }

    public UserEntity() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return hashedPassword == userEntity.hashedPassword &&
                Objects.equals(email, userEntity.email) &&
                Objects.equals(firstName, userEntity.firstName) &&
                Objects.equals(lastName, userEntity.lastName) &&
                Objects.equals(address, userEntity.address) &&
                Objects.equals(zipCode, userEntity.zipCode) &&
                Objects.equals(gender, userEntity.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email,
                hashedPassword, firstName, lastName,
                address, zipCode, gender);
    }

    @Override
    public String toString() {
        return "User {" + "email='" + email + '\'' +
                ", hashedPassword=" + hashedPassword +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
