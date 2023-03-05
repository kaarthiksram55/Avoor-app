package com.example.avoorapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/* This class represents the About screen which appears when the 'About' menu is selected. */
public class AboutScreen extends AppCompatActivity
{
    /* Member variables: */
    /* Below two arrays are initialized statically for now. Once firebase integration is done,
     * these arrays will be dynamically initialized. */
    String[] strRevisionsList =
    {
        "0.1"
    };

    String[] strRevisionsHistoryList =
    {
        "Proto under progress. "
    };

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        TextView tvAboutScreenTxtViewAppInfo;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_screen);

        tvAboutScreenTxtViewAppInfo = findViewById(R.id.AboutScreenTxtViewAppInfo);
        obtainRevisionHistory();

        /* Revision history will eventually be obtained from firebase. Hence checking if the list
         * is empty in case nothing is downloaded due to some fault. */
        if (strRevisionsList.length == 0)
        {
            /* If no revision history is found, the arrays will be empty.
             * Display a message to the user indicating the same. */
            Toast toastRevisionHistoryNotFound = Toast.makeText(getApplicationContext(), "Unable to find revision history", Toast.LENGTH_SHORT);
            toastRevisionHistoryNotFound.show();
        }
        else
        {
            /* Revision history is found as the array is not empty after initialization. Display
             * the revision history information. */
            StringBuilder strRevisionHistoryMessage = new StringBuilder()
                .append("Current version: v")
                .append(getResources().getString(R.string.app_version))
                .append(" \n\n\n")
                .append("Revision history: \n");

            for (int intRevIndex = 0; intRevIndex<strRevisionsList.length; intRevIndex++) {
                strRevisionHistoryMessage
                    .append("v")
                    .append(strRevisionsList[intRevIndex])
                    .append(" : ")
                    .append(strRevisionsHistoryList[intRevIndex])
                    .append(" \n");
            }

            tvAboutScreenTxtViewAppInfo.setText(strRevisionHistoryMessage.toString());
        }
    }

    private void obtainRevisionHistory()
    {
        /* Do nothing for now. To be implemented once firebase integration is done. */
    }
}
