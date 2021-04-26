package com.example.course_project.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.course_project.DataManagement.FileIO;
import com.example.course_project.DataManagement.Meal;
import com.example.course_project.DataManagement.PersonInfo;
import com.example.course_project.R;

import java.util.ArrayList;

public class PersonInfoLog extends Fragment {
    static PersonInfoLog personInfoLog = new PersonInfoLog();

    private PersonInfoLog(){}
    public static PersonInfoLog getInstance(){
        return personInfoLog;
    }

    private TextView textLog;
    ArrayList<PersonInfo> personInfoList = new ArrayList<>();
    FileIO fileIO = FileIO.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_info_log,container,false);
        Context context = getActivity().getApplicationContext();

        textLog = view.findViewById(R.id.textViewLog);
        personInfoList = (ArrayList<PersonInfo>) fileIO.readObjects(context,"personInfoList.ser");

        textLog.setText("Date ; Weight ; BMI\n");
        for (PersonInfo personInfo : personInfoList){
            textLog.append(personInfo.getDate() + " ; " + personInfo.getWeight()+ " ; " + personInfo.getBmi() + "\n");
        }

        return view;
    }
}
