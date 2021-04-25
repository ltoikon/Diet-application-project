package com.example.course_project.DataManagement;

import java.io.Serializable;
import java.util.ArrayList;

public class PersonInfo implements Serializable {
    private ArrayList<Double> weightHistory = new ArrayList<>();
    private ArrayList<Double> bmiHistory = new ArrayList<>();

    FileIO fileio = FileIO.getInstance();

    private double height;
    private double weight;
    //Constructor for entry
     public PersonInfo(double height, double weight){
         double bmi = (weight/(height*height));
         this.height = height;
         this.weight = weight;
     }
}