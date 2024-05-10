package com.example.curavibe_desktop;

import java.time.LocalDate;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;


    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phone;
    }

  

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
