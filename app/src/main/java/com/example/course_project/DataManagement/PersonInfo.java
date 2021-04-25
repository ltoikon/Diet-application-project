package com.example.course_project.DataManagement;


import android.content.Context;

import com.example.course_project.DataManagement.FileIO;

import java.io.IOException;
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

     //TODO remove this
    public PersonInfo(Double inputHeight){
        height = inputHeight;
        double[] inputWeight = {74.3, 76.4, 75.4, 78.5, 79.4, 81.4, 85.3};
        for (double weight : inputWeight){
            //for (int i = 0; i <inputWeight.length; i++){
            //double weight = inputWeight[i];
            double bmi = (weight/(height*height));
            weightHistory.add(weight);
            bmiHistory.add(bmi);
        }
    }
    //TODO make personinfo entry for instead of this
    public PersonInfo(Double inputHeight, String fileName, Context context) throws IOException {
        height = inputHeight;

        weightHistory = fileio.readFile("infoSet", context);

        for (double weight : weightHistory){
            //for (int i = 0; i <inputWeight.length; i++){
            //double weight = inputWeight[i];
            double bmi = (weight/(height*height));
            bmiHistory.add(bmi);
        }
    }

    //TODO remove this
    public void writeWeightHistory(Context context) throws IOException {
        System.out.println("Tallennetaan");
        fileio.writeFile("infoSet", weightHistory, context);
    }
}
