package com.example.course_project;

import java.io.Serializable;
import java.util.Date;



public class Meal implements Serializable {
    private Date date;
    private int pork, fish, beef, dairy, cheese, rice, egg, winterSalad; //Food in grams. Food compared to average then given to API
    private String timing;
    private double co2amount; //value fetched from API



    /*constructor*/
    public Meal (int pork, int beef, int fish, int dairy, int cheese, int rice, int egg, int winterSalad, String timing) {
        date = new Date();
        this.pork = pork;
        this.fish = fish;
        this.beef = beef;
        this.dairy = dairy;
        this.cheese = cheese;
        this.rice = rice;
        this.egg = egg;
        this.winterSalad = winterSalad;
        this.timing = timing;
        co2amount = fetchClimateDiet();
        System.out.println("****************" + co2amount);

    }

    /*co2 amount using food ingredient info*/
    public double fetchClimateDiet (){
        double result;
        ClimateDietAPI climateDiet = new ClimateDietAPI(); //alternative would be giving here food values
        result = climateDiet.calculate(pork, fish, beef, dairy, cheese, rice, egg, winterSalad);
        return result;
    }

}
