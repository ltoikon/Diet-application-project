package com.example.consumption_monitor.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.consumption_monitor.DataManagement.Consumption;
import com.example.consumption_monitor.DataManagement.FileIO;
import com.example.consumption_monitor.DataManagement.User;
import com.example.course_project.R;

import java.util.ArrayList;

public class ConsumptionEntry extends Fragment {

    static ConsumptionEntry consumptionEntry = new ConsumptionEntry();

    private EditText editTextPork, editTextBeef, editTextFish, editTextDairy, editTextCheese,
            editTextRice, editTextEgg, editTextWinterSalad;

    private Button bSubmit;

    private String entryPork, entryBeef, entryFish, entryDairy, entryCheese,
            entryRice, entryEgg, entryWinterSalad;

    private int intPork, intBeef, intFish, intDairy, intCheese,
            intRice, intEgg, intWinterSalad;

    ArrayList<Consumption> consumptionList = new ArrayList<>();
    FileIO fileIO = FileIO.getInstance();

    private ConsumptionEntry() {
    }

    public static ConsumptionEntry getInstance() {
        return consumptionEntry;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumption_entry, container, false);
        Context context = getActivity().getApplicationContext();

        User profile = (User) getArguments().getSerializable("User");

        editTextPork = view.findViewById(R.id.inputPork);
        editTextBeef = view.findViewById(R.id.inputBeef);
        editTextFish = view.findViewById(R.id.inputFish);
        editTextDairy = view.findViewById(R.id.inputDairy);
        editTextCheese = view.findViewById(R.id.inputCheese);
        editTextRice = view.findViewById(R.id.inputRice);
        editTextEgg = view.findViewById(R.id.inputEggs);
        editTextWinterSalad = view.findViewById(R.id.inputWinterSalad);

        bSubmit = view.findViewById(R.id.buttonSubmit);

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                entryPork = editTextPork.getText().toString();
                entryBeef = editTextBeef.getText().toString();
                entryFish = editTextFish.getText().toString();
                entryDairy = editTextDairy.getText().toString();
                entryCheese = editTextCheese.getText().toString();
                entryRice = editTextRice.getText().toString();
                entryEgg = editTextEgg.getText().toString();
                entryWinterSalad = editTextWinterSalad.getText().toString();

                /*Prevents crashing if user leaves value empty*/
                if (entryPork.length() == 0) {
                    entryPork = "0";
                }
                if (entryBeef.length() == 0) {
                    entryBeef = "0";
                }
                if (entryFish.length() == 0) {
                    entryFish = "0";
                }
                if (entryDairy.length() == 0) {
                    entryDairy = "0";
                }
                if (entryCheese.length() == 0) {
                    entryCheese = "0";
                }
                if (entryRice.length() == 0) {
                    entryRice = "0";
                }
                if (entryEgg.length() == 0) {
                    entryEgg = "0";
                }
                if (entryWinterSalad.length() == 0) {
                    entryWinterSalad = "0";
                }

                intPork = Integer.parseInt(entryPork);
                intBeef = Integer.parseInt(entryBeef);
                intFish = Integer.parseInt(entryFish);
                intDairy = Integer.parseInt(entryDairy);
                intCheese = Integer.parseInt(entryCheese);
                intRice = Integer.parseInt(entryRice);
                intEgg = Integer.parseInt(entryEgg);
                intWinterSalad = Integer.parseInt(entryWinterSalad);

                Consumption consumption = new Consumption(intPork, intBeef, intFish, intDairy, intCheese, intRice, intEgg, intWinterSalad);

                String fileName = profile.getFirstName() + profile.getLastName() + "MealList.ser";

                consumptionList = (ArrayList<Consumption>) fileIO.readObjects(context, fileName);
                consumptionList.add(consumption);
                fileIO.writeObjects(context, fileName, consumptionList);

                editTextPork.setText(null);
                editTextBeef.setText(null);
                editTextFish.setText(null);
                editTextDairy.setText(null);
                editTextCheese.setText(null);
                editTextRice.setText(null);
                editTextEgg.setText(null);
                editTextWinterSalad.setText(null);

                Toast.makeText(context, "Data saved.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
