package com.example.avoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/* This class represents the Landing Screen which appears when the app is just opened. */
public class LandingScreen extends AppCompatActivity
{

    /* memer variables */
    EditText etLandingScreenEditTxtUsername, etLandingScreenEditTxtPassword;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_screen);
        getSupportActionBar().hide();

        etLandingScreenEditTxtUsername = findViewById(R.id.LandingScreenEditTxtUsername);
        etLandingScreenEditTxtPassword = findViewById(R.id.LandingScreenEditTxtPassword);
        etLandingScreenEditTxtPassword.setVisibility(View.INVISIBLE);
    }

    /* This method is called when the button to jump to home screen is clicked. This is for debug
    * purposes. */
    public void openHomeScreen(View v)
    {
        String strUserNameFieldValue = String.valueOf(etLandingScreenEditTxtUsername.getText());

        /* check if the username field is not empty. Since its type is set as 'number' in the xml
         * layout, other checks need not be performed here. Commas, dashs, decimal points and
         * spaces seem to be not allowed by the keypad itself even though the key is provided. */
        if (!strUserNameFieldValue.isEmpty())
        {
            /* Make an intent object with the below flags set to prevent coming back to the landing
             * screen from home screen. Pass the mobile number information to the home screen. */
            Intent intent = new Intent(v.getContext(), HomeScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            String strIntentMobileNumberKey = String.valueOf(R.string.LandingScreenIntentUserNameKey);
            long longMobileNumberValue = Long.parseLong(strUserNameFieldValue);
            intent.putExtra(strIntentMobileNumberKey, longMobileNumberValue);
            startActivity(intent);
        }
    }
}
