package com.example.course_project.DataManagement;

import java.io.Serializable;

public class Salt implements Serializable {
    private byte[] salt;

    public Salt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getSalt() {
        return salt;
    }
}
