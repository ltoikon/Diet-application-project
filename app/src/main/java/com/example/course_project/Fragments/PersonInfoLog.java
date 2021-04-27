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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PersonInfoLog extends Fragment {
    static PersonInfoLog personInfoLog = new PersonInfoLog();

    private PersonInfoLog() {
    }

    public static PersonInfoLog getInstance() {
        return personInfoLog;
    }

    private TextView textLog;
    private LineChart chart;
    ArrayList<PersonInfo> personInfoList = new ArrayList<>();
    FileIO fileIO = FileIO.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_info_log, container, false);
        Context context = getActivity().getApplicationContext();

        textLog = view.findViewById(R.id.textViewLog);
        chart = (LineChart) view.findViewById(R.id.chart);
        personInfoList = (ArrayList<PersonInfo>) fileIO.readObjects(context, "personInfoList.ser");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        textLog.setText("Date ; Weight ; BMI\n");
        for (PersonInfo personInfo : personInfoList) {
            textLog.append(dateFormat.format(personInfo.getDate()) + " ; " + personInfo.getWeight() + " ; " + personInfo.getBmi() + "\n");
        }

        List<Entry> entries = new ArrayList<Entry>();
        int i = 0;
        for (PersonInfo data : personInfoList) {
            i++;
            entries.add(new Entry(i, (float) data.getWeight()));
            System.out.println(i);
        }

        LineDataSet dataSet = new LineDataSet(entries, "Weight");
        LineData lineData = new LineData(dataSet);
        //dataSet.setColor(...);
        //dataSet.setValueTextColor(...);
        chart.setData(lineData);
        chart.invalidate();

        return view;
    }
}
