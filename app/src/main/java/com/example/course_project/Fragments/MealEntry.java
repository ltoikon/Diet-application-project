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
import com.example.course_project.DataManagement.Meal;
import com.example.course_project.Interfaces.OnFragmentInteractionListener;
import com.example.course_project.R;

import java.util.ArrayList;

public class MealEntry extends Fragment {

    static MealEntry mealEntry = new MealEntry();

    private EditText editTextPork, editTextBeef, editTextFish, editTextDairy, editTextCheese,
            editTextRice, editTextEgg, editTextWinterSalad, editTextTiming;
    /*private TextView textPork, textBeef, textFish, textDairy, textCheese,
            textRice, textEgg, textWinterSalad;*/

    private Button bSubmit;

    private String entryPork, entryBeef, entryFish, entryDairy, entryCheese,
            entryRice, entryEgg, entryWinterSalad, entryTiming;

    private int intPork, intBeef, intFish, intDairy, intCheese,
            intRice, intEgg, intWinterSalad;


    ArrayList<Meal> mealList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    FileIO fileIO = FileIO.getInstance();
    private MealEntry() {}

    public static MealEntry getInstance() {
        return mealEntry;
    }

    //private String timing = "Timing";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_entry, container, false);
        Context context = getActivity().getApplicationContext();

        editTextTiming = view.findViewById(R.id.inputTiming);
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

               entryTiming = editTextTiming.getText().toString();
               entryPork = editTextPork.getText().toString();
               entryBeef = editTextBeef.getText().toString();
               entryFish = editTextFish.getText().toString();
               entryDairy = editTextDairy.getText().toString();
               entryCheese = editTextCheese.getText().toString();
               entryRice = editTextRice.getText().toString();
               entryEgg = editTextEgg.getText().toString();
               entryWinterSalad = editTextWinterSalad.getText().toString();

               /*Prevent crashing if user leaves value empty*/
               if (entryPork.length()==0){entryPork = "0";}
               if (entryBeef.length()==0){entryBeef = "0";}
               if (entryFish.length()==0){entryFish = "0";}
               if (entryDairy.length()==0){entryDairy = "0";}
               if (entryCheese.length()==0){entryCheese = "0";}
               if (entryRice.length()==0){entryRice = "0";}
               if (entryEgg.length()==0){entryEgg = "0";}
               if (entryWinterSalad.length()==0){entryWinterSalad = "0";}

               intPork =  Integer.parseInt(entryPork);
               intBeef = Integer.parseInt(entryBeef);
               intFish = Integer.parseInt(entryFish);
               intDairy = Integer.parseInt(entryDairy);
               intCheese = Integer.parseInt(entryCheese);
               intRice = Integer.parseInt(entryRice);
               intEgg = Integer.parseInt(entryEgg);
               intWinterSalad = Integer.parseInt(entryWinterSalad);

               Meal meal = new Meal(intPork, intBeef, intFish, intDairy, intCheese, intRice, intEgg, intWinterSalad, entryTiming);
               mealList = (ArrayList<Meal>) fileIO.readObjects(context, "mealList.ser");
               mealList.add(meal);
               fileIO.writeObjects(context, "mealList.ser", mealList);

               editTextPork.setText(null);
               editTextBeef.setText(null);
               editTextFish.setText(null);
               editTextDairy.setText(null);
               editTextCheese.setText(null);
               editTextRice.setText(null);
               editTextEgg.setText(null);
               editTextWinterSalad.setText(null);
               editTextTiming.setText(null);

               Toast.makeText(context, "Data saved.", Toast.LENGTH_SHORT).show();

           }
       });


        return view;
    }

    /* TODO poistoon oli syy
    // NOT NEEDED PERHAPS???***********************
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
     */
}