package com.example.course_project.DataManagement;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

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
        try {
            ins = context.openFileInput(fileName + ".csv");
            csvReader = new BufferedReader(new InputStreamReader(ins));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //open file
        csvReader.readLine();   //skip header line
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

    /* Takes context and name of the file where data is stored as parameters. Reads the ArrayList
       inside the file and returns the ArrayList. */
    public ArrayList readObjects(Context context, String fileName){
        ArrayList<Object> objectList = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            objectList = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
    }
        System.out.println("Object reading successful");
        return objectList;
    }

    /* Takes context, name of the file where data will be stored and ArrayList containing the data
       as parameters. The method writes the ArrayList to the file. */
    public void writeObjects(Context context, String fileName, ArrayList objectList){
        try {
         FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
         ObjectOutputStream oos = new ObjectOutputStream(fos);
         oos.writeObject(objectList);

         oos.close();
         fos.close();
         System.out.println("Object writing successful");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
