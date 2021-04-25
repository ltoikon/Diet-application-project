package com.example.course_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

public class StartApp extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prelogin);

        changeFragment(0);

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
    }
}