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
    private EditText editTextEmail, editTextFirstName, editTextLastName, editTextBirthDate,
            editTextHomeTown, editTextPassword, editTextConfirmPassword;
    private TextView emailError, firstNameError, lastNameError, birthDateError, homeTownError,
            passwordError, confirmPasswordError, passwordInfo;
    private Button bSignUp, bShow, bShowConfirm;
    private boolean showing = false, showingConfirm = false;

    private OnFragmentInteractionListener mListener;

    private final String emailErrorMessage1 = "Incorrect E-mail.";
    private final String emailErrorMessage2 = "This E-mail address is already being used. Choose a different E-mail address.";
    private final String nameErrorMessage = "This must not be blank.";
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

        editTextEmail = view.findViewById(R.id.inputEmail);
        editTextFirstName = view.findViewById(R.id.inputFirstNameSignup);
        editTextLastName = view.findViewById(R.id.inputLastNameSignup);
        editTextBirthDate = view.findViewById(R.id.editTextBirthDate);
        editTextHomeTown = view.findViewById(R.id.inputHomeTownSignup);
        editTextPassword = view.findViewById(R.id.inputPasswordSignup);
        editTextConfirmPassword = view.findViewById(R.id.confirmPassword);

        bSignUp = view.findViewById(R.id.signUp);
        bShow = view.findViewById(R.id.showPasswordSignup);
        bShowConfirm = view.findViewById(R.id.showConfirmPasswordSignup);

        emailError = view.findViewById(R.id.emailError);
        firstNameError = view.findViewById(R.id.firstNameError);
        lastNameError = view.findViewById(R.id.lastNameError);
        birthDateError = view.findViewById(R.id.birthDateError);
        homeTownError = view.findViewById(R.id.homeTownError);
        passwordError = view.findViewById(R.id.passwordErrorSignup);
        confirmPasswordError = view.findViewById(R.id.confirmPasswordError);

        editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editTextConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        passwordInfo = view.findViewById(R.id.passwordInfo);
        passwordInfo.setText(info);

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo make all of the error messages to appear instead of only one (only if we don't run out of time)
                if (!validateEmail()) {
                    //todo !isEmailDuplicate()
                    emailError.setText(null);
                    firstNameError.setText(null);
                    lastNameError.setText(null);
                    birthDateError.setText(null);
                    homeTownError.setText(null);
                    passwordError.setText(null);
                    confirmPasswordError.setText(null);
                } else if (!validateFirstName()) {
                    emailError.setText(null);
                    firstNameError.setText(null);
                    lastNameError.setText(null);
                    birthDateError.setText(null);
                    homeTownError.setText(null);
                    passwordError.setText(null);
                    confirmPasswordError.setText(null);
                } else if (!validateLastName()) {
                    emailError.setText(null);
                    firstNameError.setText(null);
                    lastNameError.setText(null);
                    birthDateError.setText(null);
                    homeTownError.setText(null);
                    passwordError.setText(null);
                    confirmPasswordError.setText(null);
                } else if (!validateBirthDate()) {
                    emailError.setText(null);
                    firstNameError.setText(null);
                    lastNameError.setText(null);
                    birthDateError.setText(null);
                    homeTownError.setText(null);
                    passwordError.setText(null);
                    confirmPasswordError.setText(null);
                } else if (!validateHomeTown()) {
                    emailError.setText(null);
                    firstNameError.setText(null);
                    lastNameError.setText(null);
                    birthDateError.setText(null);
                    homeTownError.setText(null);
                    passwordError.setText(null);
                    confirmPasswordError.setText(null);
                } else if (!validatePassword()) {
                    emailError.setText(null);
                    firstNameError.setText(null);
                    lastNameError.setText(null);
                    birthDateError.setText(null);
                    homeTownError.setText(null);
                    passwordError.setText(null);
                    confirmPasswordError.setText(null);
                } else if (!confirmPassword()) {
                    emailError.setText(null);
                    firstNameError.setText(null);
                    lastNameError.setText(null);
                    birthDateError.setText(null);
                    homeTownError.setText(null);
                    passwordError.setText(null);
                    confirmPasswordError.setText(null);
                } else {
                    emailError.setText(null);
                    firstNameError.setText(null);
                    lastNameError.setText(null);
                    birthDateError.setText(null);
                    homeTownError.setText(null);
                    passwordError.setText(null);
                    confirmPasswordError.setText(null);

                    username = editTextFirstName.getText().toString();
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

    private boolean validateEmail() {
        String mail = editTextEmail.getText().toString();
        if (mail == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(mail).matches();
        }
    }

    //todo make this method complete
    private boolean isEmailDuplicate() {
        //todo readfrom file
        return false;
    }

    private boolean validateFirstName() {
        if (editTextFirstName.getText().toString().length() > 0) {
            return true;
        }
        return false;
    }

    private boolean validateLastName() {
        if (editTextLastName.getText().toString().length() > 0) {
            return true;
        }
        return false;
    }

    //todo Does not check if the month has as many days or if it's leap year (Do if there is enough time)
    private boolean validateBirthDate() {
        Pattern pattern;
        Matcher matcher;
        final String DATE_PATTERN = "(0?[1-9]|12][0-9]|3[01]) " + //Day of year
                                    "[/.-] " +
                                    "(0?[1-9]|1[012]) " + //Month
                                    "[/.-] " +
                                    "((19|20)\\d\\d)"; //Year

        String bDate = editTextBirthDate.getText().toString();
        pattern = Pattern.compile(DATE_PATTERN);
        matcher = pattern.matcher(bDate);
        return matcher.matches();
    }

    private boolean validateHomeTown() {
        if (editTextHomeTown.getText().toString().length() > 0) {
            return true;
        } else {
            return false;
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