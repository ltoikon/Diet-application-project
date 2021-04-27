package com.example.course_project.Fragments;

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

import com.example.course_project.DataManagement.FileIO;
import com.example.course_project.DataManagement.Biometrics;
import com.example.course_project.DataManagement.User;
import com.example.course_project.R;

import java.util.ArrayList;

public class BiometricsEntry extends Fragment {
    static BiometricsEntry biometricsEntry = new BiometricsEntry();

    private Button bSubmit;

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

        editTextHeight = view.findViewById(R.id.inputHeight);
        editTextWeight = view.findViewById(R.id.inputWeight);
        editTextHeightError = view.findViewById(R.id.heightError);
        editTextWeightError = view.findViewById(R.id.weightError);

        bSubmit = view.findViewById(R.id.buttonSubmit);

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int errorCount = 0;
                if (editTextHeight.getText().toString().length() == 0) {
                    editTextHeightError.setText(errorMessage);
                    errorCount++;
                } else if ((editTextHeight.getText().toString().contains(".")) ||
                        (editTextHeight.getText().toString().contains(","))) {
                    editTextWeight.setText(heightError);
                    errorCount++;
                }

                if (editTextWeight.getText().toString().length() == 0) {
                    editTextWeightError.setText(errorMessage);
                    errorCount++;
                }

                if (errorCount == 0) {
                    iHeight = Integer.getInteger(entryHeight);
                    doubleWeight = Double.parseDouble(entryWeight);

                    Biometrics biometrics = new Biometrics(iHeight, doubleWeight);

                    String fileName = profile.getFirstName() + profile.getLastName() + "BiometricsList.ser";

                    biometricsList = (ArrayList<Biometrics>) fileIO.readObjects(context, fileName);
                    biometricsList.add(biometrics);
                    fileIO.writeObjects(context, fileName, biometricsList);

                    editTextHeight.setText(null);
                    editTextWeight.setText(null);
                    Toast.makeText(context, "Data saved.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
