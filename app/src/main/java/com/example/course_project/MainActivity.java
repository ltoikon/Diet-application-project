package com.example.course_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.StrictMode;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment login = Login.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.window, login);
        transaction.commit();
        /*Needed for internet*/
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    /*@Override
    public void onStart() {
        super.onStart();

        if ((getSupportFragmentManager().findFragmentById(R.id.window)) == login) {
            SignUp signUp = (SignUp) getIntent().getExtras().get("activity");
            getSupportFragmentManager().beginTransaction().replace(R.id.window, signUp).commit();
        }
    }*/

    @Override
    public void changeFragment(int id) {
        if (id == 0) {  // Login fragment
            System.out.println("Vaihtuu login-fragmenttiin");
            Fragment login = Login.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.window, login).commit();
        } else if (id == 1) {  // Sign Up fragment
            System.out.println("Vaihtuu signup-fragmenttiin");
            Fragment signUp = SignUp.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.window, signUp).commit();
        }
    }
}