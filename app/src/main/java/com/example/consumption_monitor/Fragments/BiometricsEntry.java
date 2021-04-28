package com.example.consumption_monitor.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.consumption_monitor.DataManagement.FileIO;
import com.example.consumption_monitor.DataManagement.Biometrics;
import com.example.consumption_monitor.DataManagement.User;
import com.example.course_project.R;

import java.util.ArrayList;

public class BiometricsEntry extends Fragment {
    static BiometricsEntry biometricsEntry = new BiometricsEntry();

    private Button bSubmit;

    private Biometrics biometrics;

    private EditText editTextHeight, editTextWeight;
    private TextView editTextHeightError, editTextWeightError;
    private String entryHeight, entryWeight;
    private double doubleWeight;
    private int iHeight;

    ArrayList<Biometrics> biometricsList = new ArrayList<>();
    FileIO fileIO = FileIO.getInstance();

    private String errorMessage = "This text field must not be blank.";
    private String heightError = "Height is not an integer.";

    private BiometricsEntry() {
    }

    public static BiometricsEntry getInstance() {
        return biometricsEntry;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_biometrics_entry, container, false);
        Context context = getActivity().getApplicationContext();

        User profile = (User) getArguments().getSerializable("User");

        String fileName = profile.getFirstName() + profile.getLastName() + "BiometricsList.ser";

        editTextHeight = view.findViewById(R.id.inputHeight);
        editTextWeight = view.findViewById(R.id.inputWeight);
        editTextHeightError = view.findViewById(R.id.heightError);
        editTextWeightError = view.findViewById(R.id.weightError);

        bSubmit = view.findViewById(R.id.buttonSubmit);


        // Setting the user's height to the corresponding text field
        biometricsList = (ArrayList<Biometrics>) fileIO.readObjects(context, fileName);
        if (biometricsList.size() != 0) {
            System.out.println("biometricsList is not empty");
            biometrics = biometricsList.get(biometricsList.size() - 1);
            System.out.println("Height: " + biometrics.getHeight() + "cm");
            editTextHeight.setText(String.valueOf(biometrics.getHeight()));
            biometrics = null;
        }

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Checks if the height text field and weight text field are empty and shows an
                error message if justifiable */
                int errorCount = 0;
                if (editTextHeight.getText().toString().length() == 0) {
                    editTextHeightError.setText(errorMessage);
                    errorCount++;
                } else if ((editTextHeight.getText().toString().contains(".")) ||
                        (editTextHeight.getText().toString().contains(","))) {
                    editTextWeight.setText(heightError);
                    errorCount++;
                }

                if (errorCount == 0) {
                    entryHeight = editTextHeight.getText().toString();
                    entryWeight = editTextWeight.getText().toString();
                    iHeight = Integer.parseInt(entryHeight);
                    doubleWeight = Double.parseDouble(entryWeight);

                    biometrics = new Biometrics(iHeight, doubleWeight);

                    biometricsList = (ArrayList<Biometrics>) fileIO.readObjects(context, fileName);
                    biometricsList.add(biometrics);
                    fileIO.writeObjects(context, fileName, biometricsList);

                    editTextWeight.setText(null);
                    Toast.makeText(context, "Data saved.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
