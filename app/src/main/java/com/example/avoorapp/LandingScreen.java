package com.example.avoorapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.avoorapp.support.CustomAlertDialog;
import com.example.avoorapp.support.FirebaseDownloadListener;
import com.example.avoorapp.support.FirebaseWrapper;
import com.example.avoorapp.support.SponsorsInfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/* This class represents the landing screen which appears when the app is just opened. */
public class LandingScreen extends AppCompatActivity
{
    /* Member variables */
    private EditText etLandingScreenEditTxtUsername, etLandingScreenEditTxtPassword;
    private FirebaseWrapper tempWrapper;
    private CustomAlertDialog alertDialog;
    private Context appContext;
    private SharedPreferences sharedPref;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        appContext = getApplicationContext();
        sharedPref = appContext.getSharedPreferences(appContext.getResources().getString(R.string.SharedPreferencesLoginStatusFileKey), Context.MODE_PRIVATE);
        alertDialog = new CustomAlertDialog(appContext);
        tempWrapper = new FirebaseWrapper(getApplicationContext());

        if (sharedPref.getBoolean(appContext.getResources().getString(R.string.SharedPreferencesLoginStatusKey), false))
        {
            Log.d("debugLoginLand", ""+sharedPref.getBoolean(appContext.getResources().getString(R.string.SharedPreferencesLoginStatusKey), false));
            String strCurrentUserNumber = sharedPref.getString(appContext.getResources().getString(R.string.SharedPreferencesLoginStatusNumber), "");

            if (!strCurrentUserNumber.equals(""))
            {
                tempWrapper.downloadSingleSponsorInfo(strCurrentUserNumber, new FirebaseDownloadListener() {
                    @Override
                    public void onDownloadCompleteCallback() {
                        prvOpenHomeScreen(appContext, tempWrapper.getSingleSponsorInfo());
                    }

                    @Override
                    public void onDownloadFailureCallback() {
                        setContentView(R.layout.landing_screen);
                        etLandingScreenEditTxtUsername = findViewById(R.id.LandingScreenEditTxtUsername);
                        etLandingScreenEditTxtPassword = findViewById(R.id.LandingScreenEditTxtPassword);
                    }
                });
            }
            else
            {
                setContentView(R.layout.landing_screen);
                etLandingScreenEditTxtUsername = findViewById(R.id.LandingScreenEditTxtUsername);
                etLandingScreenEditTxtPassword = findViewById(R.id.LandingScreenEditTxtPassword);
            }
        }
        else
        {
            setContentView(R.layout.landing_screen);
            etLandingScreenEditTxtUsername = findViewById(R.id.LandingScreenEditTxtUsername);
            etLandingScreenEditTxtPassword = findViewById(R.id.LandingScreenEditTxtPassword);
        }
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
            tempWrapper.downloadSingleSponsorInfo(strUsernameFieldValue, new FirebaseDownloadListener() {
                @Override
                public void onDownloadCompleteCallback()
                {
                    final SponsorsInfo singleSponsorInfo = tempWrapper.getSingleSponsorInfo();

                    if (singleSponsorInfo.getPassword().equals(strPasswordFieldValue))
                    {
                        /* Save log in status of user so that they do not have to enter password on
                         * every login. This status will be reset only when they explicitly log out
                         * using the ellipsis menu. */
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean(appContext.getResources().getString(R.string.SharedPreferencesLoginStatusKey), true);
                        editor.putString(appContext.getResources().getString(R.string.SharedPreferencesLoginStatusNumber), singleSponsorInfo.getNumber());
                        editor.apply();

                        /* Make an intent object with the below flags set to prevent coming back to
                         * the landing screen from home screen by clicking the back button. Pass the
                         * logged in sponsor information to the home screen. */
//                        Intent intent = new Intent(v.getContext(), HomeScreen.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        String strIntentSponsorInfoKey = getApplicationContext().getResources().getString(R.string.LandingScreenIntentSponsorInfoKey);
//                        intent.putExtra(strIntentSponsorInfoKey, singleSponsorInfo);
//                        startActivity(intent);
                        prvOpenHomeScreen(v.getContext(), singleSponsorInfo);
                    }
                    else
                    {
                        /* display an alert to the user indicating that the credentials entered is invalid. */
                        alertDialog.displayAlertMessage(getApplicationContext().getResources().getString(R.string.LandingScreenLoginStatusCredentialsMismatch));
                    }
                }

                @Override
                public void onDownloadFailureCallback()
                {
                    /* display an alert to the user indicating that download of information failed. */
                    alertDialog.displayAlertMessage(getApplicationContext().getResources().getString(R.string.LandingScreenLoginStatusDownloadFailed));
                }
            });
        }
        else
        {
            /* display an alert to the user indicating that the username entered is invalid. */
            alertDialog.displayAlertMessage(getApplicationContext().getResources().getString(R.string.LandingScreenLoginStatusInvalidCredentials));
        }
    }

    /* This function will pass control to the home screen. */
    private void prvOpenHomeScreen(Context context, SponsorsInfo singleSponsorInfo)
    {
        /* Make an intent object with the below flags set to prevent coming back to
         * the landing screen from home screen by clicking the back button. Pass the
         * logged in sponsor information to the home screen. */
        Intent intent = new Intent(context, HomeScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        String strIntentSponsorInfoKey = getApplicationContext().getResources().getString(R.string.LandingScreenIntentSponsorInfoKey);
        intent.putExtra(strIntentSponsorInfoKey, singleSponsorInfo);
        startActivity(intent);
    }
}
