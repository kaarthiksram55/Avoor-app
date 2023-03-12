package com.example.avoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.avoorapp.support.FirebaseDownloadStatus;
import com.example.avoorapp.support.FirebaseWrapper;
import com.example.avoorapp.support.SponsorsInfo;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/* This class represents the landing screen which appears when the app is just opened. */
public class LandingScreen extends AppCompatActivity
{
    /* member variables */
    private EditText etLandingScreenEditTxtUsername, etLandingScreenEditTxtPassword;
    private SponsorsInfo singleSponsorInfo;
    private FirebaseWrapper tempWrapper;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_screen);

        etLandingScreenEditTxtUsername = findViewById(R.id.LandingScreenEditTxtUsername);
        etLandingScreenEditTxtPassword = findViewById(R.id.LandingScreenEditTxtPassword);

        tempWrapper = new FirebaseWrapper();
    }

    /* This method is called when the button to jump to home screen is clicked. This is for debug
    * purposes. */
    public void openHomeScreen(View v)
    {
        /* verify if the provided login credentials are valid and accordingly select a course of
         * action. If valid, allow log in. If invalid, do not allow login and display an alert to
         * the user. */
        String strUsernameFieldValue = String.valueOf(etLandingScreenEditTxtUsername.getText());
        String strPasswordFieldValue = String.valueOf(etLandingScreenEditTxtPassword.getText());

        if (strUsernameFieldValue.length() == 10 && strUsernameFieldValue.matches("[0-9]+") && !(strPasswordFieldValue.isEmpty())) {
            tempWrapper.downloadSingleSponsorInfo(strUsernameFieldValue, new FirebaseDownloadStatus() {
                @Override
                public void onDownloadCompleteCallback() {
                    singleSponsorInfo = tempWrapper.getSingleSponsorInfo();
                    boolean boolLoginValidityStatus = prvVerifyLoginCredentials(strPasswordFieldValue);

                    if (boolLoginValidityStatus) {
                        /* Make an intent object with the below flags set to prevent coming back to the landing
                         * screen from home screen by clicking the back button. Pass the mobile number
                         * information to the home screen. */
                        singleSponsorInfo.strSponsorNumber = strUsernameFieldValue;
                        Intent intent = new Intent(v.getContext(), HomeScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        String strIntentMobileNumberKey = getApplicationContext().getResources().getString(R.string.LandingScreenIntentUserNameKey);
                        long longMobileNumberValue = Long.parseLong(strUsernameFieldValue);
                        intent.putExtra(strIntentMobileNumberKey, longMobileNumberValue);
                        // checkout serializable to pass entire sponsor info to next activity.
                        startActivity(intent);
                    }
                    else
                    {
                        /* display an alert to the user indicating that the credentials entered is invalid. */
                        prvDisplayStatusUsingAlert(getApplicationContext().getResources().getString(R.string.LandingScreenLoginStatusCredentialsMismatch));
                    }
                }

                @Override
                public void onDownloadFailureCallback() {
                    /* display an alert to the user indicating that download of information failed. */
                    prvDisplayStatusUsingAlert(getApplicationContext().getResources().getString(R.string.LandingScreenLoginStatusDownloadFailed));
                }
            });
        }
        else
        {
            /* display an alert to the user indicating that the username entered is invalid. */
            prvDisplayStatusUsingAlert(getApplicationContext().getResources().getString(R.string.LandingScreenLoginStatusInvalidCredentials));
        }
    }

    /* This function verifies if the entered user name and password are a correct pair and returns
     * the verification status. 'true' implies valid and 'false' implies invalid. */
    private boolean prvVerifyLoginCredentials(String strPassword)
    {
        /* Verification of login credentials:
         *
         * Check if password entered matches with the value in firebase. If yes, then status should
         * be returned as true. Else, it should be returned as false. */
        return ((singleSponsorInfo.strSponsorPassword.equals(strPassword)));
    }

    /* This method displays an alert box with the desired status message. */
    private void prvDisplayStatusUsingAlert(String strStatusMessage)
    {
        /* Initialize an alert dialog and display the desired message. */
        Log.d("debug", strStatusMessage);
        AlertDialog adLoginStatusDialog = new AlertDialog.Builder(this).create();
        adLoginStatusDialog.setMessage(strStatusMessage);
        adLoginStatusDialog.show();
    }
}
