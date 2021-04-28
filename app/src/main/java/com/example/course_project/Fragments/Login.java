package com.example.course_project.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.course_project.Activities.FragmentController;
import com.example.course_project.DataManagement.FileIO;
import com.example.course_project.DataManagement.ProtectPassword;
import com.example.course_project.DataManagement.Salt;
import com.example.course_project.Interfaces.OnFragmentInteractionListener;
import com.example.course_project.R;
import com.example.course_project.DataManagement.User;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Login extends Fragment {

    static Login login;

    static {
        try {
            login = new Login();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private EditText editTextEmail, editTextPassword;
    private TextView emailError, passwordError;
    private Button bLogin, bSignUp, bShow;
    private boolean showing = false;

    FileIO fileIO = FileIO.getInstance();

    private OnFragmentInteractionListener mListener;

    private final String emailErrorMessage = "The email is not registered in our system.";
    private final String passwordErrorMessage = "Incorrect password.";

    private String userFile = "userList.ser";
    private String saltFile = "salt.ser";

    ArrayList<User> userList = new ArrayList<>();
    ArrayList<Salt> saltList = new ArrayList<>();

    private int userID = 0;

    private Login() throws NoSuchAlgorithmException {
    }

    public static Login getInstance() {
        return login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Context context = getActivity().getApplicationContext();

        editTextEmail = view.findViewById(R.id.inputEmail);
        editTextPassword = view.findViewById(R.id.inputPassword);
        emailError = view.findViewById(R.id.emailError);
        passwordError = view.findViewById(R.id.passwordError);

        bLogin = view.findViewById(R.id.buttonLogIn);
        bSignUp = view.findViewById(R.id.buttonSignUp);
        bShow = view.findViewById(R.id.buttonShowPassword);

        editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList = (ArrayList<User>) fileIO.readObjects(context, userFile);
                saltList = (ArrayList<Salt>) fileIO.readObjects(context, saltFile);
                byte[] salt = null;

                userID = getUserID(userList, editTextEmail.getText().toString());
                if (userID != -1) {
                    salt = saltList.get(userID).getSalt();
                }
                System.out.println("########%%%########");

                System.out.println("#####" + userID + "#####");


                if (userID == -1) {
                    emailError.setText(emailErrorMessage);
                    passwordError.setText(null);
                    System.out.println("Väärä käyttäjänimi:");
                    System.out.println("Käyttäjänimi: " + editTextEmail.getText().toString());
                    System.out.println("Salasana: " + editTextPassword.getText().toString());
                    Toast.makeText(getActivity(), "Tuntematon käyttäjänimi.", Toast.LENGTH_SHORT).show();
                } else if (userList.get(userID).getPassword().equals(ProtectPassword.getSecurePassword(editTextPassword.getText().toString(), salt))) {
                    emailError.setText(null);
                    passwordError.setText(null);
                    System.out.println("Kirjautuminen onnistui");
                    System.out.println("Käyttäjänimi: " + editTextEmail.getText().toString());
                    System.out.println("Salasana: " + editTextPassword.getText().toString());
                    Toast.makeText(getActivity(), "Kirjautuminen onnistui!", Toast.LENGTH_SHORT).show();
                    editTextEmail.setText(null);
                    editTextPassword.setText(null);
                    changeActivity(userList.get(userID));
                } else {
                    passwordError.setText(passwordErrorMessage);
                    emailError.setText(null);
                    System.out.println("Väärä salasana:");
                    System.out.println("Käyttäjänimi: " + editTextEmail.getText().toString());
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

    private int getUserID(ArrayList<User> userList, String inputEmail) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getEmail().equals(inputEmail)) {
                return i;
            }
        }
        return -1;
    }

    public void changeActivity(User user) {
        Intent intent = new Intent(getActivity(), FragmentController.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }
}