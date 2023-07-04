package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;
import java.util.Objects;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String username;
    public String email;
    public int hashedPassword;
    public String firstName;
    public String lastName;
    public Date birthDate;
    public String address;
    public String zipCode;
    public String gender;

    public UserEntity(String username, String email,
                      int hashedPassword, String firstName,
                      String lastName, Date birthDate,
                      String address, String zipCode, String gender) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
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
        return id == userEntity.id &&
                hashedPassword == userEntity.hashedPassword &&
                Objects.equals(username, userEntity.username) &&
                Objects.equals(email, userEntity.email) &&
                Objects.equals(firstName, userEntity.firstName) &&
                Objects.equals(lastName, userEntity.lastName) &&
                Objects.equals(birthDate, userEntity.birthDate) &&
                Objects.equals(address, userEntity.address) &&
                Objects.equals(zipCode, userEntity.zipCode) &&
                Objects.equals(gender, userEntity.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email,
                hashedPassword, firstName, lastName,
                birthDate, address, zipCode, gender);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", hashedPassword=" + hashedPassword +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
