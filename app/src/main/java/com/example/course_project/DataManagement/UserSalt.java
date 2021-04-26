package com.example.course_project.DataManagement;

import java.io.Serializable;

public class UserSalt implements Serializable {
    private byte[] salt;
    private String email;

    public UserSalt(String email, byte[] salt) {
        this.email = email;
        this.salt = salt;
    }

    public byte[] getSalt() {
        return salt;
    }

    public String getEmail() {
        return email;
    }
}
