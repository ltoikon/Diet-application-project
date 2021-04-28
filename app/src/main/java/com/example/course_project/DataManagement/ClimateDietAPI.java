package com.example.course_project.DataManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/*using JSON on API request */
public class ClimateDietAPI {
    private double co2;
    private final double avePork = 1000;
    private final double aveBeef = 400;
    private final double aveFish = 600;
    private final double aveCheese = 300;
    private final double aveDairy = 3800;
    private final double aveRice = 90;
    private final double aveWinterSalad = 1400; // finn average grams per week,

    public ClimateDietAPI() {
    }


    /* Takes input values and changes them into percents. Return co2 emissions */
    public double calculate(int pork, int fish, int beef, int dairy, int cheese, int rice, int egg, int winterSalad) {
        String urlAPI = formingUrl(pork, fish, beef, dairy, cheese, rice, egg, winterSalad);
        String json = getJSON(urlAPI);

        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                co2 = jsonObject.getDouble("Total");
                System.out.println(co2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        co2 = co2 / 52; // conversion to co2eq per input
        return co2;
    }

    public String formingUrl(int pork, int fish, int beef, int dairy, int cheese, int rice, int egg, int winterSalad) {
        double doublePork = ((double) pork / avePork) * 100;
        double doubleFish = ((double) fish / aveFish) * 100;
        double doubleBeef = ((double) beef / aveBeef) * 100;
        double doubleDairy = ((double) dairy / aveDairy) * 100;
        double doubleCheese = ((double) cheese / aveCheese) * 100;
        double doubleRice = ((double) rice / aveRice) * 100;
        double doubleWinterSalad = ((double) winterSalad / aveWinterSalad) * 100;

        pork = (int) Math.round(doublePork);
        fish = (int) Math.round(doubleFish);
        beef = (int) Math.round(doubleBeef);
        dairy = (int) Math.round(doubleDairy);
        cheese = (int) Math.round(doubleCheese);
        rice = (int) Math.round(doubleRice);
        winterSalad = (int) Math.round(doubleWinterSalad);

        String url = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/"
                + "calculatorapi/v1/FoodCalculator?query.diet=omnivore&query.beefLevel=" + beef +
                "&query.fishLevel=" + fish +
                "&query.porkPoultryLevel=" + pork +
                "&query.dairyLevel=" + dairy +
                "&query.cheeseLevel=" + cheese +
                "&query.riceLevel=" + rice +
                "&query.eggLevel=" + egg +
                "&query.winterSaladLevel=" + winterSalad;
        System.out.println(url);
        return url;
    }

    /* Takes url (from the method above) as parameter and return the co2 emissions */
    public String getJSON(String urlString) {
        String response = null;
        try {
            URL url = new URL(urlString);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            response = sb.toString();
            in.close(); // input stream closed
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
