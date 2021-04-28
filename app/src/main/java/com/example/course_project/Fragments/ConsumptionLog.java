package com.example.course_project.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.course_project.DataManagement.FileIO;
import com.example.course_project.DataManagement.Consumption;
import com.example.course_project.DataManagement.User;
import com.example.course_project.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

public class ConsumptionLog extends Fragment {
    static ConsumptionLog consumptionLog = new ConsumptionLog();

    ArrayList<Consumption> consumptionList = new ArrayList<>();

    private ConsumptionLog() {
    }

    public static ConsumptionLog getInstance() {
        return consumptionLog;
    }

    FileIO fileIO = FileIO.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumption_log, container, false);
        Context context = getActivity().getApplicationContext();

        User profile = (User) getArguments().getSerializable("User");
        String fileName = profile.getFirstName() + profile.getLastName() + "MealList.ser";

        TextView textLog = view.findViewById(R.id.textViewLog);
        textLog.setMovementMethod(new ScrollingMovementMethod());
        LineChart chart = (LineChart) view.findViewById(R.id.chart);
        consumptionList = (ArrayList<Consumption>) fileIO.readObjects(context, fileName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String sDate = "Date", sCo2Amount = "CO2eq";
        textLog.setText(String.format("%-26s %-5s\n", sDate, sCo2Amount));
        for (Consumption consumption : consumptionList) {
            textLog.append(String.format("%-20s %-5.1f\n",
                                dateFormat.format(consumption.getDate()), consumption.getCo2amount()));
        }

        List<Entry> entries = new ArrayList<Entry>();
        int i = 0;
        for (Consumption data : consumptionList) {
            i++;
            entries.add(new Entry(i, (float) data.getCo2amount()));
            System.out.println(i);
        }

        LineDataSet dataSet = new LineDataSet(entries, "Weight");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();

        return view;
    }
}
