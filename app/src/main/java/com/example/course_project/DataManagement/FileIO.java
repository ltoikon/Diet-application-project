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

    private FileIO() {}

    public static FileIO getInstance() {
        return fileIO;
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
