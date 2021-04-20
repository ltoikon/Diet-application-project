package com.example.harkka_login;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends Fragment {

    static SignUp signUp = new SignUp();
    private EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    private TextView usernameError, emailError, passwordError, confirmPasswordError, passwordInfo;
    private Button bSignUp, bShow, bShowConfirm;
    private boolean showing = false, showingConfirm = false;

    private OnFragmentInteractionListener mListener;

    private final String usernameErrorMessage1 = "The username is already being used. Choose a different username.";
    private final String usernameErrorMessage2 = "The username is too short. Please choose a username that is at least 5 characters.";
    private final String emailErrorMessage = "Incorrect E-mail.";
    private final String passwordErrorMessage = "Chosen password does not meet the password requirements.";
    private final String confirmPasswordErrorMessage = "The passwords do not match.";

    private final String info = "Password must contain at least 12 characters, including at least " +
            "1 upper and lower case letter, 1 number and 1 special character.";

    private String username, email, password;

    private SignUp() {}

    public static SignUp getInstance() {
        return signUp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        editTextUsername = view.findViewById(R.id.inputUsernameSignup);
        editTextEmail = view.findViewById(R.id.inputEmail);
        editTextPassword = view.findViewById(R.id.inputPasswordSignup);
        editTextConfirmPassword = view.findViewById(R.id.confirmPassword);
        bSignUp = view.findViewById(R.id.signUp);
        bShow = view.findViewById(R.id.showPasswordSignup);
        bShowConfirm = view.findViewById(R.id.showConfirmPasswordSignup);

        usernameError = view.findViewById(R.id.usernameErrorSignup);
        emailError = view.findViewById(R.id.emailError);
        passwordError = view.findViewById(R.id.passwordErrorSignup);
        confirmPasswordError = view.findViewById(R.id.confirmPasswordError);

        editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editTextConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        passwordInfo = view.findViewById(R.id.passwordInfo);
        passwordInfo.setText(info);

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername()) {
                    if (editTextUsername.getText().toString().length() > 4) {
                        usernameError.setText(usernameErrorMessage1);
                    } else {
                        usernameError.setText(usernameErrorMessage2);
                        emailError.setText(null);
                        passwordError.setText(null);
                        confirmPasswordError.setText(null);
                    }
                } else if (!validateEmail()) {
                    usernameError.setText(null);
                    emailError.setText(emailErrorMessage);
                    passwordError.setText(null);
                    confirmPasswordError.setText(null);
                } else if (!validatePassword()) {
                    usernameError.setText(null);
                    emailError.setText(null);
                    passwordError.setText(passwordErrorMessage);
                    confirmPasswordError.setText(null);
                } else if (!confirmPassword()) {
                    usernameError.setText(null);
                    emailError.setText(null);
                    passwordError.setText(null);
                    confirmPasswordError.setText(confirmPasswordErrorMessage);
                } else {
                    usernameError.setText(null);
                    emailError.setText(null);
                    passwordError.setText(null);
                    confirmPasswordError.setText(null);

                    username = editTextUsername.getText().toString();
                    email = editTextEmail.getText().toString();
                    password = editTextPassword.getText().toString();

                    Toast.makeText(getActivity(), "Rekisteröityminen onnistui!", Toast.LENGTH_SHORT).show();
                    mListener.changeFragment(0); // 0 == Login fragment

                    //todo Vahvistusruutu käyttäjän luomisesta + tallennus tiedostoon (oliona)
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

        bShowConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showingConfirm) {
                    editTextConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editTextConfirmPassword.setSelection(editTextConfirmPassword.getText().length());
                    showingConfirm = false;
                } else {
                    editTextConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    editTextConfirmPassword.setSelection(editTextConfirmPassword.getText().length());
                    showingConfirm = true;
                }
            }
        });
        return view;
    }

    private boolean validateUsername() {
        if (editTextUsername.getText().toString().length() > 4) {
            return true;
        }
        return false;
    }

    private boolean validateEmail() {
        String mail = editTextEmail.getText().toString();
        if (mail == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(mail).matches();
        }
    }

    private boolean validatePassword() {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^" +
                                        "(?=.*[0-9])" +  //Must contain at least one number
                                        "(?=.*[a-z])" +  //Must contain at least one lower case letter
                                        "(?=.*[A-Z])" +  //Must contain at least one upper case letter
                                        "(?=.*[!@#£¤$%&/=?+€*_<>])" + //At least one special character
                                        "(?=\\S+$)" + // Can't contain whitespaces
                                        ".{12,}" + // At least 8 characters
                                        "$";

        String pw = editTextPassword.getText().toString();
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(pw);

        return matcher.matches();
    }

    private boolean confirmPassword() {
        if (editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())) {
            return true;
        }
        return false;
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