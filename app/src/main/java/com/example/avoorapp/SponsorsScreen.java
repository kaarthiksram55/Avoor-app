package com.example.avoorapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.avoorapp.support.CustomAlertDialog;
import com.example.avoorapp.support.FirebaseDownloadListener;
import com.example.avoorapp.support.FirebaseWrapper;
import com.example.avoorapp.support.SponsorsInfo;

import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

/* This class represents the Sponsors screen which displays the details of all pradosham sponsors. */
public class SponsorsScreen extends AppCompatActivity
{
    /* member variables */
    private TableLayout tblSponsorsScreenSponsorsInfoTable;
    private FirebaseWrapper tempWrapper;
    private ArrayList<SponsorsInfo> sponsorsInfoList;
    private CustomAlertDialog alertDialog;

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
        alertDialog = new CustomAlertDialog(this);
        tempWrapper = new FirebaseWrapper(getApplicationContext());
        prvObtainSponsorsInfoFromFirebase();
    }

    /* This method is used to obtain the Sponsors information from firebase into the object provided
     * as input. */
    private void prvObtainSponsorsInfoFromFirebase()
    {
        tempWrapper.downloadAllSponsorsInfo(new FirebaseDownloadListener()
        {
            @Override
            public void onDownloadCompleteCallback()
            {
                sponsorsInfoList = tempWrapper.getAllSponsorsInfo();
                Log.d("debug----------", sponsorsInfoList.get(0).getStrSponsorName() + ", " + sponsorsInfoList.get(1).getStrSponsorName());
                loadSponsorsInfoTableWithInfo(sponsorsInfoList);
            }

            @Override
            public void onDownloadFailureCallback()
            {
                alertDialog.displayAlertMessage(getApplicationContext().getResources().getString(R.string.SponsorScreenAllInfoStatusDownloadFailed));
            }
        });
    }

    /* This method fills the Sponsors Information table with the sponsors data provided as input. */
    private void loadSponsorsInfoTableWithInfo(ArrayList<SponsorsInfo> sponsorsInfoList)
    {
        for (int i=0; i<sponsorsInfoList.size(); i++)
        {
            TableRow tblrowSponsorInfoRow = new TableRow(this);
            tblrowSponsorInfoRow.setLayoutParams(new TableRow.LayoutParams()); // TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
            TextView tvSponsorInfoCell;

            tvSponsorInfoCell = new TextView(this);
            tvSponsorInfoCell.setText(sponsorsInfoList.get(i).getStrSponsorName());
            tvSponsorInfoCell.setPadding(10, 10, 10, 10);
            tblrowSponsorInfoRow.addView(tvSponsorInfoCell);

            tvSponsorInfoCell = new TextView(this);
            tvSponsorInfoCell.setText(sponsorsInfoList.get(i).getStrSponsorNumber());
            tvSponsorInfoCell.setPadding(10, 10, 10, 10);
            tblrowSponsorInfoRow.addView(tvSponsorInfoCell);

            tvSponsorInfoCell = new TextView(this);
            tvSponsorInfoCell.setText(sponsorsInfoList.get(i).getStrSponsorLocation());
            tvSponsorInfoCell.setPadding(10, 10, 10, 10);
            tblrowSponsorInfoRow.addView(tvSponsorInfoCell);

            tblSponsorsScreenSponsorsInfoTable.addView(tblrowSponsorInfoRow);
        }
    }
}