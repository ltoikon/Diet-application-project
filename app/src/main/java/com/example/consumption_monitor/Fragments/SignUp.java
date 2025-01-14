package com.example.consumption_monitor.Fragments;

import android.app.Activity;
import android.content.Context;
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

import com.example.consumption_monitor.DataManagement.FileIO;
import com.example.consumption_monitor.DataManagement.ProtectPassword;
import com.example.consumption_monitor.DataManagement.Salt;
import com.example.consumption_monitor.DataManagement.User;
import com.example.consumption_monitor.Interfaces.OnFragmentInteractionListener;
import com.example.course_project.R;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends Fragment {

    static SignUp signUp = new SignUp();

    private EditText editTextEmail, editTextFirstName, editTextLastName, editTextBirthDate,
            editTextHomeTown, editTextPassword, editTextConfirmPassword;
    private TextView emailError, firstNameError, lastNameError, birthDateError, homeTownError,
            passwordError, confirmPasswordError, passwordInfo;
    private Button bSignUp, bCancel, bShow, bShowConfirm;
    private boolean showing = false, showingConfirm = false;

    private OnFragmentInteractionListener mListener;

    private final String emailErrorMessage1 = "Incorrect email format.";
    private final String emailErrorMessage2 = "This email address is already being used. Choose a different email address.";
    private final String nameErrorMessage = "This must not be blank.";
    private final String birthDateErrorMessage = "Enter valid birth date";
    private final String passwordErrorMessage = "Chosen password does not meet the password requirements.";
    private final String confirmPasswordErrorMessage = "The passwords do not match.";

    private final String info = "Password must contain at least 12 characters, including at least " +
            "1 upper and lower case letter, 1 number and 1 special character.";

    private String email, firstName, lastName, birthDate, homeTown, password, hashedPassword;
    byte[] salt;

    private String userFile = "userList.ser";
    private String saltFile = "salt.ser";

    ArrayList<User> userList = new ArrayList<>();
    ArrayList<Salt> saltList = new ArrayList<>();

    FileIO fileIO = FileIO.getInstance();

    private SignUp() {
    }

    public static SignUp getInstance() {
        return signUp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        Context context = getActivity().getApplicationContext();

        editTextEmail = view.findViewById(R.id.inputEmailSignUp);
        editTextFirstName = view.findViewById(R.id.inputFirstNameSignup);
        editTextLastName = view.findViewById(R.id.inputLastNameSignup);
        editTextBirthDate = view.findViewById(R.id.editTextBirthDate);
        editTextHomeTown = view.findViewById(R.id.inputHomeTownSignup);
        editTextPassword = view.findViewById(R.id.inputPasswordSignup);
        editTextConfirmPassword = view.findViewById(R.id.confirmPassword);

        bSignUp = view.findViewById(R.id.signUp);
        bCancel = view.findViewById(R.id.cancel);
        bShow = view.findViewById(R.id.showPasswordSignup);
        bShowConfirm = view.findViewById(R.id.showConfirmPasswordSignup);

        emailError = view.findViewById(R.id.emailErrorSignUp);
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

                /* Checks if there are any errors on the text fields and if there are, the code
                 *  announces them */
                int errorCount = 0;
                if (!validateEmail()) {
                    emailError.setText(emailErrorMessage1);
                    errorCount++;
                } else if (isEmailDuplicate(editTextEmail.getText().toString())) {
                    emailError.setText(emailErrorMessage2);
                    errorCount++;
                } else {
                    emailError.setText(null);
                }
                if (!validateFirstName()) {
                    firstNameError.setText(nameErrorMessage);
                    errorCount++;
                } else {
                    firstNameError.setText(null);
                }
                if (!validateLastName()) {
                    lastNameError.setText(nameErrorMessage);
                    errorCount++;
                } else {
                    lastNameError.setText(null);
                }
                if (!validateBirthDate()) {
                    birthDateError.setText(birthDateErrorMessage);
                    errorCount++;
                } else {
                    birthDateError.setText(null);
                }
                if (!validateHomeTown()) {
                    homeTownError.setText(nameErrorMessage);
                    errorCount++;
                } else {
                    homeTownError.setText(null);
                }
                if (!validatePassword()) {
                    passwordError.setText(passwordErrorMessage);
                    errorCount++;
                } else {
                    passwordError.setText(null);
                }
                if (!confirmPassword()) {
                    confirmPasswordError.setText(confirmPasswordErrorMessage);
                    errorCount++;
                } else {
                    confirmPasswordError.setText(null);
                }
                if (errorCount == 0) {
                    emailError.setText(null);
                    firstNameError.setText(null);
                    lastNameError.setText(null);
                    birthDateError.setText(null);
                    homeTownError.setText(null);
                    passwordError.setText(null);
                    confirmPasswordError.setText(null);

                    email = editTextEmail.getText().toString();
                    firstName = editTextFirstName.getText().toString();
                    lastName = editTextLastName.getText().toString();
                    birthDate = editTextBirthDate.getText().toString();
                    homeTown = editTextHomeTown.getText().toString();
                    password = editTextPassword.getText().toString();

                    try {
                        salt = ProtectPassword.getSalt();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    hashedPassword = ProtectPassword.getSecurePassword(password, salt);

                    editTextEmail.setText(null);
                    editTextFirstName.setText(null);
                    editTextLastName.setText(null);
                    editTextBirthDate.setText(null);
                    editTextHomeTown.setText(null);
                    editTextBirthDate.setText(null);
                    editTextPassword.setText(null);
                    editTextConfirmPassword.setText(null);

                    userList.clear();

                    userList = (ArrayList<User>) fileIO.readObjects(context, userFile);

                    User user = new User(email, hashedPassword, firstName, lastName, birthDate, homeTown);
                    userList.add(user);
                    FileIO fileIO = FileIO.getInstance();
                    saltList = fileIO.readObjects(context, saltFile);
                    Salt saltUser = new Salt(salt);
                    saltList.add(saltUser);


                    fileIO.writeObjects(context, userFile, userList);
                    fileIO.writeObjects(context, saltFile, saltList);
                    System.out.println("Rekisteröityminen onnistui!");

                    mListener.changeFragment(0); // 0 == Login fragment
                }
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.changeFragment(0); // 0 == Login fragment
            }
        });

        bShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Switches between showed password and hidden password text and puts the cursor
                 *  at the end of the text field */
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

                /* Switches between showed password and hidden password text and puts the cursor
                 *  at the end of the text field */
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
        String email = editTextEmail.getText().toString();
        if (email == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    // Checks if there already is account created for chosen email
    private boolean isEmailDuplicate(String email) {
        userList = (ArrayList<User>) fileIO.readObjects(getActivity().getApplicationContext(), userFile);
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
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

    // Validates the chosen date as a real date including leap years etc.
    private boolean validateBirthDate() {
        Pattern pattern;
        Matcher matcher;

        // Pattern taken from https://regexr.com/2vg48 and made by Macs Dickinson
        final String DATE_PATTERN = "^(((0[1-9]|[12][0-9]|3[01])" +
                "[- /.]" +
                "(0[13578]|1[02])|(0[1-9]|[12][0-9]|30)" +
                "[- /.]" +
                "(0[469]|11)|(0[1-9]|1\\d|2[0-8])" +
                "[- /.]" +
                "02)" +
                "[- /.]" +
                "\\d{4}|29" +
                "[- /.]" +
                "02" +
                "[- /.]" +
                "(\\d{2}(0[48]|[2468][048]|[13579][26])|([02468][048]|[1359][26])00))$";


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