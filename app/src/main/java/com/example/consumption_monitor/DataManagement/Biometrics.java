package com.example.consumption_monitor.DataManagement;

import java.io.Serializable;
import java.util.Date;

public class Biometrics implements Serializable {

    private int height;
    private double weight;
    private double bmi;
    private Date date;

    //Constructor for entry
    public Biometrics(int height, double weight) {
        date = new Date();
        bmi = (weight / ((Double.valueOf(height) / 100) * (Double.valueOf(height) / 100)));
        this.height = height;
        this.weight = weight;
        System.out.println(bmi);
    }

    public Date getDate() {
        return date;
    }

    public int getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getBmi() {
        return bmi;
    }

}
