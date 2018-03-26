package com.example.niraj.mapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by baltech on 09/11/17.
 */

public class SignUp_Fragment extends Fragment implements View.OnClickListener {
    private static View view;
    private static EditText fullName, emailId, mobileNumber, location,
            password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private Main2Activity mActivity;
    private DatabaseHelper databaseHelper;
    private User user;
    private static FragmentManager fragmentManager;

    public SignUp_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        initViews();
        setListeners();
        initObjects();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        location = (EditText) view.findViewById(R.id.location);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

        // Setting text selector over textviews
        //XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
           /* ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);*/
        } catch (Exception e) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivity = (Main2Activity) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(mActivity);
        user = new User();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
                checkValidation();
                break;

            case R.id.already_user:

                // Replace login fragment
                new Main2Activity().onBackPressed();
                break;
        }

    }

    public void onBackPressed() {
        int count = getChildFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            try {
                getChildFragmentManager().popBackStack();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            mActivity.finish();
        }
    }
    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");

        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");

        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");

        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");

        else
           /* Toast.makeText(getActivity(), "Do SignUp.", Toast.LENGTH_SHORT)
                    .show();*/

        if (!databaseHelper.checkUser(getEmailId)) {

            user.setName(getFullName);
            user.setEmail(getEmailId);
            user.setPassword(getPassword);

            databaseHelper.addUser(user);

            new CustomToast().Show_Toast(getActivity(), view,
                    "Registration Success");

            emptyInputEditText();
           // emptyInputEditText();


        } else {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Error Email Exists");        }

    }

    private void emptyInputEditText() {
        fullName.setText(null);
        emailId.setText(null);
        mobileNumber.setText(null);
        location.setText(null);
        password.setText(null);
        confirmPassword.setText(null);
    }
}
