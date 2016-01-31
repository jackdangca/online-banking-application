package com.onlinebank.user;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String mail;
    @NotNull
    private String password;
    @NotNull
    private String country;
    @NotNull
    private String state;
    @NotNull
    private Integer zip;
    @NotNull
    private String address;

    private String avatar;
    @NotNull
    private String cin;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }
}
