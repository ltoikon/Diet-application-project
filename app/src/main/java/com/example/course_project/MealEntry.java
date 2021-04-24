package com.example.course_project;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MealEntry extends Fragment {

    static MealEntry mealEntry = new MealEntry();

    private EditText editTextPork, editTextBeef, editTextFish, editTextDairy, editTextCheese,
            editTextRice, editTextEgg, editTextWinterSalad, editTextTiming;
    private TextView textPork, textBeef, textFish, textDairy, textCheese,
            textRice, textEgg, textWinterSalad;;

    private Button bSubmit;

    FileIO fileIO = FileIO.getInstance();
    private MealEntry() {}

    public static MealEntry getInstance() {
        return mealEntry;
    }

    private String timing = "Timing";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_entry, container, false);

        editTextTiming = view.findViewById(R.id.inputTiming);

        textPork = view.findViewById(R.id.textPork);
        textBeef = view.findViewById(R.id.textBeef);
        textFish = view.findViewById(R.id.textFish);
        textDairy = view.findViewById(R.id.textDairy);
        textCheese = view.findViewById(R.id.textCheese);
        textRice = view.findViewById(R.id.textRice);
        textEgg = view.findViewById(R.id.textEggs);
        textWinterSalad = view.findViewById(R.id.textWinterSalad);
/*
        textPork.setText("Pork and poultry");
        textBeef.setText("Beef");
        textFish.setText("Fish");
        textDairy.setText("Dairy");
        textCheese.setText("Cheese");
        textRice.setText("Rice");
        textEgg.setText("Eggs");
        textWinterSalad.setText("Green house grown veggies");
*/


        bSubmit = view.findViewById(R.id.buttonSubmit);

        return view;
    }
}
