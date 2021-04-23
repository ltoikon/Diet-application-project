package com.example.course_project;

import android.content.Context;

import java.io.BufferedReader;
import java.io.EOFException;
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

    public void registerUser(User user, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("userList.ser", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.close();
            fos.close();
            System.out.println("User added to userList.ser");
        } catch (IOException e) {
            System.out.println("###################");
            e.printStackTrace();
            System.out.println("###################");
        }
    }

    // Reads User objects from a file and returns list of them as an ArrayList
    public ArrayList<User> getUsers(Context context) {
        User user = null;
        ArrayList<User> userList = new ArrayList<>();
        boolean cont = true;
        int i = 0;

        try {
            FileInputStream fis = context.openFileInput("userList.ser");

            while (cont) {
                try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                    user = (User) ois.readObject();
                    if (user != null) {
                        userList.add(user);
                    } else {
                        cont = false;
                    }
                    ois.close();
                    fis.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    cont = false;
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println("#### Kierroksia: " + i);
        }
        return userList;
    }
}
