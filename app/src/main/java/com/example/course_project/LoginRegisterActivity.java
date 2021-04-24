package com.example.course_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

public class LoginRegisterActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prelogin);
        Fragment login = Login.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.window, login);
        transaction.commit();
        /*Needed for internet*/
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void changeFragment(int id) {
        if (id == 0) {  // Login fragment
            System.out.println("Vaihtuu login-fragmenttiin");
            Fragment login = Login.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.window, login).commit();
        } else if (id == 1) {  // SignUp fragment
            System.out.println("Vaihtuu signup-fragmenttiin");
            Fragment signUp = SignUp.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.window, signUp).commit();
        }
        // This is moved to Placeholder.java class
        /*else if (id == 2) {  // mealEntry fragment
            System.out.println("Vaihtuu mealEntry-fragmenttiin");
            Fragment mealEntry = MealEntry.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.window, mealEntry).commit();
        }*/
    }

    public void changeActivity() {
        Intent intent = new Intent(LoginRegisterActivity.this, Placeholder.class);
        startActivity(intent);
    }
}