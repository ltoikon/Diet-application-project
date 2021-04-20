package com.example.harkka_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment login = Login.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.window, login);
        transaction.commit();
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
        if (id == 0) {
            System.out.println("Vaihtuu login-fragmenttiin");
            Fragment login = Login.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.window, login).commit();
        } else if (id == 1) {
            System.out.println("Vaihtuu signup-fragmenttiin");
            Fragment signUp = SignUp.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.window, signUp).commit();
        }
    }
}