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
import com.example.avoorapp.support.TableCell;

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
    private TableCell tableCell;
    private int intTableColor;
    private int intTableCellColor;

    private final float floatTableHeaderSizeSp = 13f;
    private final float floatTableCellSizeSp = 13f;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState) {
        Toolbar tlbarSponsorsScreenToolbar;
        TextView tvSponsorScreenTxtViewAppName, tvSponsorsScreenTxtViewTitleMessage;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sponsors_screen);
        if(this.getResources().getString(R.string.theme).equals("Night"))
        {
            intTableColor = this.getResources().getColor(R.color.white);
            intTableCellColor = this.getResources().getColor(R.color.black);
        }
        else
        {
            intTableColor = this.getResources().getColor(R.color.black);
            intTableCellColor = this.getResources().getColor(R.color.white);
        }

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
        tblSponsorsScreenSponsorsInfoTable.setBackgroundColor(intTableColor);
        tableCell = new TableCell(this);
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
                loadSponsorInfoTableWithHeader();
                loadSponsorsInfoTableWithInfo(sponsorsInfoList);
            }

            @Override
            public void onDownloadFailureCallback()
            {
                alertDialog.displayAlertMessage(getApplicationContext().getResources().getString(R.string.SponsorScreenAllInfoStatusDownloadFailed));
            }
        });
    }

    /* This method fills the sponsors information table with the header row. */
    private void loadSponsorInfoTableWithHeader()
    {
        TableRow tblrowSponsorInfoRow = new TableRow(this);
        tblrowSponsorInfoRow.setLayoutParams(new TableRow.LayoutParams());
    
        String strTableHeaderName = this.getResources().getString(R.string.SponsorScreenTableHeaderName);
        tblrowSponsorInfoRow.addView(tableCell.generateCell(strTableHeaderName, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderNumber = this.getResources().getString(R.string.SponsorScreenTableHeaderNumber);
        tblrowSponsorInfoRow.addView(tableCell.generateCell(strTableHeaderNumber, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderLocation = this.getResources().getString(R.string.SponsorScreenTableHeaderLocation);
        tblrowSponsorInfoRow.addView(tableCell.generateCell(strTableHeaderLocation, intTableCellColor, floatTableHeaderSizeSp, true));

        tblSponsorsScreenSponsorsInfoTable.addView(tblrowSponsorInfoRow);
    }

    /* This method fills the Sponsors Information table with the sponsors data provided as input. */
    private void loadSponsorsInfoTableWithInfo(ArrayList<SponsorsInfo> sponsorsInfoList)
    {
        for (int i=0; i<sponsorsInfoList.size(); i++)
        {
            if (sponsorsInfoList.get(i).getSponsoredPradoshams() != null)
            {
                TableRow tblrowSponsorInfoRow = new TableRow(this);
                tblrowSponsorInfoRow.setLayoutParams(new TableRow.LayoutParams());

                String strTableCellName = sponsorsInfoList.get(i).getName();
                tblrowSponsorInfoRow.addView(tableCell.generateCell(strTableCellName, intTableCellColor, floatTableCellSizeSp, false));
                String strTableCellNumber = sponsorsInfoList.get(i).getNumber();
                tblrowSponsorInfoRow.addView(tableCell.generateCell(strTableCellNumber, intTableCellColor, floatTableCellSizeSp, false));
                String strTableCellLocation = sponsorsInfoList.get(i).getLocation();
                tblrowSponsorInfoRow.addView(tableCell.generateCell(strTableCellLocation, intTableCellColor, floatTableCellSizeSp, false));

                tblSponsorsScreenSponsorsInfoTable.addView(tblrowSponsorInfoRow);
            }
        }
    }
}