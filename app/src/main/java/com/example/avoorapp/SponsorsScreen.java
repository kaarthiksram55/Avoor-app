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
        TextView tvSponsorScreenTxtViewAppName, tvSponsorsScreenTxtViewTitleMessage;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sponsors_screen);

        tblSponsorsScreenSponsorsInfoTable = findViewById(R.id.SponsorsScreenSponsorsInfoTable);
        tvSponsorScreenTxtViewAppName = findViewById(R.id.SponsorsScreenTxtViewAppName);
        tvSponsorsScreenTxtViewTitleMessage = findViewById(R.id.SponsorsScreenTxtViewTitleMessage);
        tlbarSponsorsScreenToolbar = findViewById(R.id.SponsorsScreenToolbar);
        setSupportActionBar(tlbarSponsorsScreenToolbar);

        tvSponsorScreenTxtViewAppName.setText(
            getResources()
            .getString(R.string.AvoorAppDisplayNameEnglish)
        );
        tvSponsorsScreenTxtViewTitleMessage.setText(
            getResources()
            .getString(R.string.SponsorsScreenTitleEnglish)
        );
        alertDialog = new CustomAlertDialog(this);
        tempWrapper = new FirebaseWrapper(getApplicationContext());
        prvObtainSponsorsInfoFromFirebase();
    }

    /* This method is used to obtain the Sponsors information from firebase. Further actions to be
     * taken are decided inside the interface provided to the firebase wrapper method.  */
    private void prvObtainSponsorsInfoFromFirebase()
    {
        tempWrapper.downloadAllSponsorsInfo(new FirebaseDownloadListener()
        {
            @Override
            public void onDownloadCompleteCallback()
            {
                sponsorsInfoList = tempWrapper.getAllSponsorsInfo();
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
            tblrowSponsorInfoRow.setLayoutParams(new TableRow.LayoutParams());
            TextView tvSponsorInfoCell;

            tvSponsorInfoCell = new TextView(this);
            tvSponsorInfoCell.setText(sponsorsInfoList.get(i).getName());
            tvSponsorInfoCell.setPadding(4, 4, 4, 4);
            tblrowSponsorInfoRow.addView(tvSponsorInfoCell);

            tvSponsorInfoCell = new TextView(this);
            tvSponsorInfoCell.setText(sponsorsInfoList.get(i).getNumber());
            tvSponsorInfoCell.setPadding(4, 4, 4, 4);
            tblrowSponsorInfoRow.addView(tvSponsorInfoCell);

            tvSponsorInfoCell = new TextView(this);
            tvSponsorInfoCell.setText(sponsorsInfoList.get(i).getLocation());
            tvSponsorInfoCell.setPadding(4, 4, 4, 4);
            tblrowSponsorInfoRow.addView(tvSponsorInfoCell);

            tblSponsorsScreenSponsorsInfoTable.addView(tblrowSponsorInfoRow);
        }
    }
}