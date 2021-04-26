package com.example.course_project.DataManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PersonInfo implements Serializable {

    FileIO fileio = FileIO.getInstance();

    private double height;
    private double weight;
    private double bmi;
    private Date date;
    //Constructor for entry
     public PersonInfo(double height, double weight){
         date = new Date();
         bmi = (weight/(height*height));
         this.height = height;
         this.weight = weight;
     }

    public Date getDate() {return date;}
    public double getWeight() {return weight;}
    public double getBmi() {return bmi;}

}
