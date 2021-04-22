package com.example.harkka_login;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
//TODO move unnecessary stuff from method to object calling it
//TODO read and write as objects
public class FileIO {

    static FileIO fileIO = new FileIO();

    private String fileName;
    ArrayList<Double> weightArray = new ArrayList<>();
    private String row;

    private InputStream ins;
    private BufferedReader csvReader;
    private OutputStreamWriter csvWriter;

    private FileIO() {}

    public static FileIO getInstance() {
        return fileIO;
    }

    public ArrayList<Double> readFile(String inputName, Context context) throws IOException {
        fileName = inputName;
        {

            try {
                ins = context.openFileInput(fileName + ".csv");
                csvReader = new BufferedReader(new InputStreamReader(ins));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }   //open file
        csvReader.readLine();           //skip header line
        while ((row = csvReader.readLine()) != null){
            String[] infoPerRow = row.split(",");
            weightArray.add(Double.valueOf(infoPerRow[1]));
        }
        csvReader.close();
        System.out.println("Reading done");
        return weightArray;
    }

    public void writeFile(String inputName, ArrayList<Double> inputArray, Context context) throws IOException {
        fileName = inputName;
        String writeRow;
        //open file
        {
            try {
                csvWriter = new OutputStreamWriter(context.openFileOutput(fileName + ".csv", Context.MODE_PRIVATE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*actual writing of file*/
        csvWriter.write("Irrelevant,weightDouble,Irrelevant\n"); //header line
        for (double input : inputArray){
            writeRow = "0," + input + ",3\n";
            csvWriter.append(writeRow);
        }
        csvWriter.close();
        System.out.println("Writing done");

    }

    public void appendFile(String inputName, ArrayList<Double> inputArray){
        fileName = inputName;
        System.out.println("Writing done");
    }
}
