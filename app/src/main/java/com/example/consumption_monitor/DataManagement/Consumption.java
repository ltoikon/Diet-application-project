package com.example.consumption_monitor.DataManagement;

import java.io.Serializable;
import java.util.Date;

public class Consumption implements Serializable {
    private Date date;
    private int pork, fish, beef, dairy, cheese, rice, egg, winterSalad; //Food in grams. Food compared to average then given to API
    private double co2amount; //value fetched from API

    /* Takes input data as parameters */
    public Consumption(int pork, int beef, int fish, int dairy, int cheese, int rice, int egg, int winterSalad) {
        date = new Date();
        this.pork = pork;
        this.fish = fish;
        this.beef = beef;
        this.dairy = dairy;
        this.cheese = cheese;
        this.rice = rice;
        this.egg = egg;
        this.winterSalad = winterSalad;
        co2amount = fetchClimateDiet();
        System.out.println("****************" + co2amount);
    }

    /* Calucates and returns the co2 emissions of the given inputs */
    public double fetchClimateDiet() {
        double result;
        ClimateDietAPI climateDiet = new ClimateDietAPI(); //alternative would be giving here food values
        result = climateDiet.calculate(pork, fish, beef, dairy, cheese, rice, egg, winterSalad);
        return result;
    }

    public Date getDate() {
        return date;
    }

    public double getCo2amount() {
        return co2amount;
    }

}
