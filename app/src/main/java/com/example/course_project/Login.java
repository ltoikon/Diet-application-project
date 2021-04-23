package com.example.course_project;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends Fragment {

    static Login login = new Login();
    private EditText editTextUsername, editTextPassword;
    private TextView usernameError, passwordError;
    private Button bLogin, bSignUp, bShow;
    private Button printUserList;
    private boolean showing = false;

    FileIO fileIO = FileIO.getInstance();

    private OnFragmentInteractionListener mListener;

    private final String usernameErrorMessage = "The username is not registered in our system.";
    private final String passwordErrorMessage = "Incorrect password.";

    //todo Tunnuksen hakeminen tiedostosta tämän sijaan
    private String username = "Username", password = "Password";

    ArrayList<User> userList = new ArrayList<>();

    private Login() {}

    public static Login getInstance() {
        return login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextUsername = view.findViewById(R.id.inputEmail);
        editTextPassword = view.findViewById(R.id.inputPassword);
        usernameError = view.findViewById(R.id.emailError);
        passwordError = view.findViewById(R.id.passwordError);

        bLogin = view.findViewById(R.id.buttonLogIn);
        bSignUp = view.findViewById(R.id.buttonSignUp);
        bShow = view.findViewById(R.id.buttonShowPassword);

        printUserList = view.findViewById(R.id.printUserList);

        editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((editTextUsername.getText().toString().equals(username) &&
                        (editTextPassword.getText().toString().equals(password)))) {
                    usernameError.setText(null);
                    passwordError.setText(null);
                    System.out.println("Kirjautuminen onnistui");
                    System.out.println("Käyttäjänimi: " + editTextUsername.getText().toString());
                    System.out.println("Salasana: " + editTextPassword.getText().toString());
                    Toast.makeText(getActivity(), "Kirjautuminen onnistui!", Toast.LENGTH_SHORT).show();
                    //TODO remove this test part
                    //testing mealentry fragment
                    mListener.changeFragment(2); // 2 == MealEntry fragment
                    //todo switch to menu fragment

                } else if (!editTextUsername.getText().toString().equals(username)) {
                    usernameError.setText(usernameErrorMessage);
                    passwordError.setText(null);
                    System.out.println("Väärä käyttäjänimi:");
                    System.out.println("Käyttäjänimi: " + editTextUsername.getText().toString());
                    System.out.println("Salasana: " + editTextPassword.getText().toString());
                    Toast.makeText(getActivity(), "Tuntematon käyttäjänimi.", Toast.LENGTH_SHORT).show();
                } else if (!editTextPassword.getText().toString().equals(password)) {
                    passwordError.setText(passwordErrorMessage);
                    usernameError.setText(null);
                    System.out.println("Väärä salasana:");
                    System.out.println("Käyttäjänimi: " + editTextUsername.getText().toString());
                    System.out.println("Salasana: " + editTextPassword.getText().toString());
                    Toast.makeText(getActivity(), "Väärä salasana.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showing) {
                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editTextPassword.setSelection(editTextPassword.getText().length());
                    showing = false;
                } else {
                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    editTextPassword.setSelection(editTextPassword.getText().length());
                    showing = true;
                }
            }
        });

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.changeFragment(1); // 1 == SignUp fragment
            }
        });

        printUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList = fileIO.getUsers(getActivity().getApplicationContext());
                int i = 0;
                System.out.println("####### userList sisältö: #######");
                for (User user : userList) {
                    System.out.println("### Listan " + i+1 + ". jäsen\nEmail: " + user.getEmail() +
                            "\nNimi: " + user.getFirstName() + " " + user.getLastName() +
                            "\nSyntymäpäivä: " + user.getBirthDate() + "\nAsuinpaikka: " + user.getHomeTown() + "\n###");
                }
                System.out.println("#################################");
            }
        });

        return view;
    }

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
}