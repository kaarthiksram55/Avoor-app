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
    /* Create a class whose objects act like a struct to hold information about sponsors. */
//    public static class SponsorsInfo
//    {
//        /* Member variables. */
//        public String strSponsorName = "";
//        public String strSponsorNumber = "";
//        public String strSponsorLocation = "";
//    }

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
            sponsorsInfoArr[index].strSponsorName = strSponsorName[index];
            sponsorsInfoArr[index].strSponsorNumber = strSponsorNumber[index];
            sponsorsInfoArr[index].strSponsorLocation = strSponsorLocation[index];
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
            tvSponsorInfoCell.setText(sponsorsInfo.strSponsorName);
            tvSponsorInfoCell.setPadding(10, 10, 10, 10);
            tblrowSponsorInfoRow.addView(tvSponsorInfoCell);

            tvSponsorInfoCell = new TextView(this);
            tvSponsorInfoCell.setText(sponsorsInfo.strSponsorNumber);
            tvSponsorInfoCell.setPadding(10, 10, 10, 10);
            tblrowSponsorInfoRow.addView(tvSponsorInfoCell);

            tvSponsorInfoCell = new TextView(this);
            tvSponsorInfoCell.setText(sponsorsInfo.strSponsorLocation);
            tvSponsorInfoCell.setPadding(10, 10, 10, 10);
            tblrowSponsorInfoRow.addView(tvSponsorInfoCell);

            tblSponsorsScreenSponsorsInfoTable.addView(tblrowSponsorInfoRow);
        }
    }
}