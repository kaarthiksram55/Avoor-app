package com.example.avoorapp;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.avoorapp.support.SponsorsInfo;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

/* This class represents the Sponsors screen which displays the details of all pradosham sponsors. */
public class SponsorsScreen extends AppCompatActivity
{
    /* member variables */
    private TableLayout tblSponsorsScreenSponsorsInfoTable;

    private String[] strSponsorName =
    {
        "Subbu",
        "Suri",
        "Sriram"
    };
    private String[] strSponsorNumber =
    {
        "9282109090",
        "9234018574",
        "8598284537"
    };
    private String[] strSponsorLocation =
    {
        "Chennai",
        "Puducherry",
        "Bangalore"
    };

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState) {
        Toolbar tlbarSponsorsScreenToolbar;
        TextView tvSponsorScreenTxtViewAppName;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sponsors_screen);

        tblSponsorsScreenSponsorsInfoTable = findViewById(R.id.SponsorsScreenSponsorsInfoTable);
        tvSponsorScreenTxtViewAppName = findViewById(R.id.SponsorsScreenTxtViewAppName);
        tlbarSponsorsScreenToolbar = findViewById(R.id.SponsorsScreenToolbar);
        setSupportActionBar(tlbarSponsorsScreenToolbar);

        tvSponsorScreenTxtViewAppName.setText(getResources().getString(R.string.AvoorAppDisplayNameEnglish));
        int intNumberOfSponsors = strSponsorName.length;
        SponsorsInfo[] sponsorsInfoArr = new SponsorsInfo[intNumberOfSponsors];
        obtainSponsorsInfoFromFirebase(sponsorsInfoArr);
        loadSponsorsInfoTableWithInfo(sponsorsInfoArr);
    }

    /* This method is used to obtain the Sponsors information from firebase into the object provided
     * as input. */
    private void obtainSponsorsInfoFromFirebase(SponsorsInfo[] sponsorsInfoArr)
    {
        /* Do something temporary for now. To be implemented once firebase integration is done. */
        for (int index=0; index<sponsorsInfoArr.length; index++)
        {
            sponsorsInfoArr[index] = new SponsorsInfo();
            sponsorsInfoArr[index].setStrSponsorName(strSponsorName[index]);
            sponsorsInfoArr[index].setStrSponsorNumber(strSponsorNumber[index]);
            sponsorsInfoArr[index].setStrSponsorLocation(strSponsorLocation[index]);
        }
    }

    /* This method fills the Sponsors Information table with the sponsors data provided as input. */
    private void loadSponsorsInfoTableWithInfo(SponsorsInfo[] sponsorsInfoArr)
    {
        for (SponsorsInfo sponsorsInfo : sponsorsInfoArr) {
            TableRow tblrowSponsorInfoRow = new TableRow(this);
            tblrowSponsorInfoRow.setLayoutParams(new TableRow.LayoutParams()); // TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
            TextView tvSponsorInfoCell;

            tvSponsorInfoCell = new TextView(this);
            tvSponsorInfoCell.setText(sponsorsInfo.getStrSponsorName());
            tvSponsorInfoCell.setPadding(10, 10, 10, 10);
            tblrowSponsorInfoRow.addView(tvSponsorInfoCell);
//
            tvSponsorInfoCell = new TextView(this);
            tvSponsorInfoCell.setText(sponsorsInfo.getStrSponsorNumber());
            tvSponsorInfoCell.setPadding(10, 10, 10, 10);
            tblrowSponsorInfoRow.addView(tvSponsorInfoCell);
//
            tvSponsorInfoCell = new TextView(this);
            tvSponsorInfoCell.setText(sponsorsInfo.getStrSponsorLocation());
            tvSponsorInfoCell.setPadding(10, 10, 10, 10);
            tblrowSponsorInfoRow.addView(tvSponsorInfoCell);

            tblSponsorsScreenSponsorsInfoTable.addView(tblrowSponsorInfoRow);
        }
    }
}