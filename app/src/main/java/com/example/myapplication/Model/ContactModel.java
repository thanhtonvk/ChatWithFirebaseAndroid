package com.example.myapplication.Model;

import java.io.Serializable;

public class ContactModel implements Serializable {
    public static String userAcc;
    private String user;
    private String userContact;
    private String emailContact;
    private String phoneNumber;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public ContactModel(String user, String userContact, String emailContact, String phoneNumber) {
        this.user = user;
        this.userContact = userContact;
        this.emailContact = emailContact;
        this.phoneNumber = phoneNumber;
    }

    public ContactModel() {

    }
}
