package com.example.course_project.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.course_project.DataManagement.FileIO;
import com.example.course_project.DataManagement.PersonInfo;
import com.example.course_project.R;

import java.util.ArrayList;

public class PersonInfoEntry extends Fragment {
    static PersonInfoEntry personInfoEntry = new PersonInfoEntry();

    private Button bSubmit;

    private EditText editTextHeight, editTextWeight;
    private String entryHeight, entryWeight;
    private double doubleHeight, doubleWeight;

    ArrayList<PersonInfo> personInfoList = new ArrayList<>();
    FileIO fileIO = FileIO.getInstance();
    private PersonInfoEntry() {}

    public static PersonInfoEntry getInstance(){return personInfoEntry;}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_info_entry, container, false);
        Context context = getActivity().getApplicationContext();

        editTextHeight = view.findViewById(R.id.inputHeight);
        editTextWeight = view.findViewById(R.id.inputWeight);
        bSubmit = view.findViewById(R.id.buttonSubmit);

        bSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                entryHeight = editTextHeight.getText().toString();
                entryWeight = editTextWeight.getText().toString();

                //TODO required entries, error message

                doubleHeight = Double.parseDouble(entryHeight);
                doubleWeight = Double.parseDouble(entryWeight);

                PersonInfo personInfo = new PersonInfo(doubleHeight, doubleWeight);
                if (personInfoList.size() == 0) {
                    personInfoList = (ArrayList<PersonInfo>) fileIO.readObjects(context, "personInfoList.ser");
                }
                personInfoList.add(personInfo);
                fileIO.writeObjects(context,"personInfoList.ser", personInfoList);

            }
        });

        return view;
    }
}
