package com.example.avoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/* This class represents the Home Screen which appears when the app is just opened. */
public class HomeScreen extends AppCompatActivity
{

    /* member variables */
    TextView tvHomeScreenTxtViewAppName, tvHomeScreenTxtViewTitleMessage, tvHomeScreenTxtViewUserInfo, tvHomeScreenTxtViewPrdshPrtc;
    ListView lvHomeScreenListViewMenuItems;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        getSupportActionBar().hide();

        tvHomeScreenTxtViewAppName = findViewById(R.id.HomeScreenTxtViewAppName);
        tvHomeScreenTxtViewTitleMessage = findViewById(R.id.HomeScreenTxtViewTitleMessage);
        tvHomeScreenTxtViewUserInfo = findViewById(R.id.HomeScreenTxtViewUserInfo);
        tvHomeScreenTxtViewPrdshPrtc = findViewById(R.id.HomeScreenTxtViewPrdshPrtc);
        lvHomeScreenListViewMenuItems = findViewById(R.id.HomeScreenListViewMenuItems);

        tvHomeScreenTxtViewAppName.setText(R.string.AvoorAppDisplayNameEnglish);
        tvHomeScreenTxtViewTitleMessage.setText(R.string.HomeScreenTitleEnglish);
        tvHomeScreenTxtViewPrdshPrtc.setText("Next: 27th Feb 2023");

        Intent intent = getIntent();
        long longMobileNumber = intent.getLongExtra(String.valueOf(R.string.LandingScreenIntentUserNameKey), 0);
        if (longMobileNumber != 0)
        {
            tvHomeScreenTxtViewUserInfo.setText("Welcome, " + longMobileNumber);
        }
    }
}
