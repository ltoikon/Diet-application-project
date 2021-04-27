package com.example.course_project.Fragments;

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

import com.example.course_project.DataManagement.FileIO;
import com.example.course_project.DataManagement.Biometrics;
import com.example.course_project.DataManagement.User;
import com.example.course_project.R;

import java.util.ArrayList;

public class BiometricsEntry extends Fragment {
    static BiometricsEntry biometricsEntry = new BiometricsEntry();

    private Button bSubmit;

    private EditText editTextHeight, editTextWeight;
    private String entryHeight, entryWeight;
    private double doubleHeight, doubleWeight;

    ArrayList<Biometrics> biometricsList = new ArrayList<>();
    FileIO fileIO = FileIO.getInstance();

    private BiometricsEntry() {
    }

    public static BiometricsEntry getInstance() {
        return biometricsEntry;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_info_entry, container, false);
        Context context = getActivity().getApplicationContext();

        User profile = (User) getArguments().getSerializable("User");

        editTextHeight = view.findViewById(R.id.inputHeight);
        editTextWeight = view.findViewById(R.id.inputWeight);
        bSubmit = view.findViewById(R.id.buttonSubmit);

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                entryHeight = editTextHeight.getText().toString();
                entryWeight = editTextWeight.getText().toString();

                //TODO required entries, error message

                doubleHeight = Double.parseDouble(entryHeight);
                doubleWeight = Double.parseDouble(entryWeight);

                Biometrics biometrics = new Biometrics(doubleHeight, doubleWeight);

                String fileName = profile.getFirstName() + profile.getLastName() + "BiometricsList.ser";

                biometricsList = (ArrayList<Biometrics>) fileIO.readObjects(context, fileName);
                biometricsList.add(biometrics);
                fileIO.writeObjects(context, fileName, biometricsList);

                editTextHeight.setText(null);
                editTextWeight.setText(null);
                Toast.makeText(context, "Data saved.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
