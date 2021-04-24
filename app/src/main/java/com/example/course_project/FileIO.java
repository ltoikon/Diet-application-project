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

    // Writes User to a file
    public void registerUser(ArrayList<User> userList, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("userList.ser", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userList);
            oos.close();
            fos.close();
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

        System.out.println("#################### getUsers ####################");
        try {
            FileInputStream fis = context.openFileInput("userList.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            userList = (ArrayList<User>) ois.readObject();

            for (int i = 1; i < userList.size() + 1; i++) {
                user = userList.get(i-1);

                System.out.println("###");
                System.out.println("Listan " + i + ". jäsen");
                System.out.println("Email: " + user.getEmail());
                System.out.println("Nimi: " + user.getFirstName() + " " + user.getLastName());
                System.out.println("Syntymäpäivä: " + user.getBirthDate());
                System.out.println("Asuinpaikka: " + user.getHomeTown());
                System.out.println("###");
            }
            //todo fix the method so CLassCastException is not mandatory (needed only when userList is empty)
        } catch (ClassCastException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("########################################");
        return userList;
    }
}
