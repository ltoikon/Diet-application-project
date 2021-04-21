package com.example.harkka_login;


import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

public class PersonInfo {
    private ArrayList<Double> weightHistory = new ArrayList<>();
    private ArrayList<Double> bmiHistory = new ArrayList<>();
    private double height;
    FileIO fetch = new FileIO();

    //TODO constructor for actual use

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

    public PersonInfo(Double inputHeight, String fileName, Context context) throws IOException {
        height = inputHeight;

        weightHistory = fetch.readFile("infoSet", context);

        for (double weight : weightHistory){
            //for (int i = 0; i <inputWeight.length; i++){
            //double weight = inputWeight[i];
            double bmi = (weight/(height*height));
            bmiHistory.add(bmi);
        }
    }

    public ArrayList<Double> getWeightList(){
        return weightHistory;
    }

    public ArrayList<Double> getBmiList(){
        return bmiHistory;
    }

    public void writeWeightHistory(Context context) throws IOException {
        System.out.println("Tallennetaan");
        fetch.writeFile("infoSet", weightHistory, context);
    }
}
