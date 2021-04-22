package com.example.harkka_login;

import java.util.Date;



public class Meal {
    private Date date;
    private int pork, fish, beef, dairy, cheese, rice, egg, winterSalad; //Food in grams. Food compared to average then given to API
    private String timing;
    private double co2amount; //value fetched from API

    //public enum MealType {BREAKFAST, LUNCH, DINNER} // ei ehkä paras tapa


    /*constructor*/
    public Meal () {
        //TODO constructor for meal
    }

    /*co2 amount using food ingredient info*/
    public void fetchClimateDiet (){
        ClimateDietAPI climateDiet = new ClimateDietAPI(); //alternative would be giving here food values
        co2amount = climateDiet.calculate(pork, fish, beef, dairy, cheese, rice, egg, winterSalad);
        //TODO use of API, own class for it?
    }

    /* Do we use Meal class to do entries too or just for creating meal object*/
    //TODO Meal Entry NEED OWN CLASS
    public void entryInformation (){
    }
}
