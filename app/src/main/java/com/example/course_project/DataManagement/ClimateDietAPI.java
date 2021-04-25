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
    private final int avePork = 1000;
    private final int aveBeef = 400;
    private final int aveFish = 600;
    private final int aveCheese = 300;
    private final int aveDairy = 3800;
    private final int aveRice = 90;
    private final int aveWinterSalad = 1400; // finn average grams per week,
    public ClimateDietAPI(){

    }

    public double calculate(int pork, int fish, int beef, int dairy, int cheese, int rice, int egg, int winterSalad){
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
        co2 = co2/52; // conversion to co2eq per input
        return co2;
    }

    public String formingUrl(int pork, int fish, int beef, int dairy, int cheese, int rice, int egg, int winterSalad){
        pork = (pork/ avePork)*100;
        fish = (fish/aveFish)*100;
        beef = (beef/aveBeef)*100;
        dairy = (dairy/aveDairy)*100;
        cheese = (cheese/aveCheese)*100;
        rice = (rice/aveRice)*100;
        winterSalad = (winterSalad/aveWinterSalad)*100;

        String url = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/"
                + "calculatorapi/v1/FoodCalculator?query.diet=omnivore&query.beefLevel=" + beef +
                "&query.fishLevel="+ fish +
                "&query.porkPoultryLevel=" + pork +
                "&query.dairyLevel=" + dairy +
                "&query.cheeseLevel="+ cheese +
                "&query.riceLevel="+ rice +
                "&query.eggLevel="+ egg +
                "&query.winterSaladLevel="+ winterSalad;
        return url;
    }


    /*method for pulling JSON*/
    public String getJSON(String urlString){
        String response = null;
        try {
            URL url = new URL(urlString);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            response = sb.toString();
            in.close(); // input stream closed
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
