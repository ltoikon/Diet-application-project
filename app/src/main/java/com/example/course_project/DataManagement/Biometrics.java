package com.example.course_project.DataManagement;

import java.io.Serializable;
import java.util.Date;

public class Biometrics implements Serializable {

    private double height;
    private double weight;
    private double bmi;
    private Date date;
    //Constructor for entry
     public Biometrics(double height, double weight){
         date = new Date();
         bmi = (weight/(height*height));
         this.height = height;
         this.weight = weight;
         System.out.println(bmi); //TODO exist for test purposes
     }

    public Date getDate() {return date;}
    public double getWeight() {return weight;}
    public double getBmi() {return bmi;}

}
