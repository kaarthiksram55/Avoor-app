package com.example.avoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.avoorapp.support.CustomAlertDialog;
import com.example.avoorapp.support.FirebaseDownloadListener;
import com.example.avoorapp.support.FirebaseWrapper;
import com.example.avoorapp.support.PradoshamInfo;
import com.example.avoorapp.support.SponsorsInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/* This class represents the screen visualized by the sankalpam_screen layout xml file. */
public class SankalpamScreen extends AppCompatActivity {
    /* member variables */
    private TableLayout tblSankalpamScreenPradoshamInfoTable, tblSankalpamScreenFamilyInfoTable;
    private FirebaseWrapper tempWrapper;
    private ArrayList<PradoshamInfo> pradoshamInfoList;
    private CustomAlertDialog alertDialog;
    private SponsorsInfo currentSponsorInfo;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        Toolbar tlbarSankalpamScreenToolbar;
        TextView tvSankalpamScreenTxtViewAppName, tvSankalpamScreenTxtViewTitleMessage;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sankalpam_screen);

        tblSankalpamScreenPradoshamInfoTable = findViewById(R.id.SankalpamScreenPradoshamInfoTable);
        tblSankalpamScreenFamilyInfoTable = findViewById(R.id.SankalpamScreenFamilyInfoTable);
        tvSankalpamScreenTxtViewAppName = findViewById(R.id.SankalpamScreenTxtViewAppName);
        tvSankalpamScreenTxtViewTitleMessage = findViewById(R.id.SankalpamScreenTxtViewTitleMessage);
        tlbarSankalpamScreenToolbar = findViewById(R.id.SankalpamScreenToolbar);
        setSupportActionBar(tlbarSankalpamScreenToolbar);

        tvSankalpamScreenTxtViewAppName.setText(
        getResources()
        .getString(R.string.AvoorAppDisplayNameEnglish)
        );
        tvSankalpamScreenTxtViewTitleMessage.setText(
        getResources()
        .getString(R.string.SankalpamScreenTitleEnglish)
        );
        alertDialog = new CustomAlertDialog(this);
        tempWrapper = new FirebaseWrapper(getApplicationContext());

        Intent intent = getIntent();
        currentSponsorInfo = (SponsorsInfo)intent.getSerializableExtra(this.getResources().getString(R.string.HomeScreenSponsorInfoIntentKey));
        prvObtainPradoshamInfoFromFirebase();
    }

    /* This private function will retrieve pradosham info from firebase. In the Interface provided
     * to the firebase wrapper function call, further actions will be taken post download of all
     * information to be inserted into the table. */
    private void prvObtainPradoshamInfoFromFirebase()
    {
        tempWrapper.downloadPradoshamDetails(new FirebaseDownloadListener()
        {
            @Override
            public void onDownloadCompleteCallback()
            {
                pradoshamInfoList = tempWrapper.getPradoshamDetails();
                prvObtainSponsorPradoshamInfo();
                prvLoadSponsorFamilyInfoTable();
            }

            @Override
            public void onDownloadFailureCallback()
            {
                alertDialog.displayAlertMessage(getApplicationContext().getResources().getString(R.string.PradoshamScreenAllInfoStatusDownloadFailed));
            }
        });
    }

    /* This function will filter out those pradosham entries which are taken up by the current
     * sponsor. */
    private void prvObtainSponsorPradoshamInfo()
    {
        Map<String, String> tempSponsorPradoshamMap = currentSponsorInfo.getSponsoredPradoshams();

        for (int j=0; j<pradoshamInfoList.size(); j++)
        {
            PradoshamInfo tempPradoshamInfo = pradoshamInfoList.get(j);
            String strIndicesString = tempSponsorPradoshamMap.get(tempPradoshamInfo.getYearName());

            if (strIndicesString.length() != 0)
            {
                String[] strIndicesStringArr = strIndicesString.split(",");
                if (strIndicesStringArr.length > 0)
                {
                    ArrayList<Map<String, String>> sponsorPradoshamInfoTableEntries = new ArrayList<Map<String, String>>();

                    for (int k=0; k<strIndicesStringArr.length; k++)
                    {
                        sponsorPradoshamInfoTableEntries.add(tempPradoshamInfo.getPradoshamDetails().get(Integer.parseInt(strIndicesStringArr[k])));
                    }

                    prvLoadSponsorPradoshamTableWithInfo(tempPradoshamInfo.getYearName(), sponsorPradoshamInfoTableEntries);
                }
            }
        }
    }

    /* This function will load the pradosham table with those pradosham details to which the
     * current sponsor is associated. */
    private void prvLoadSponsorPradoshamTableWithInfo(String strYear, ArrayList<Map<String, String>> pradoshamInfoList)
    {
        for (int j=0; j<pradoshamInfoList.size(); j++)
        {
            Map<String, String> tempPradoshamInfoMap = pradoshamInfoList.get(j);
            TableRow tblRowPradoshamInfoRow = new TableRow(this);
            tblRowPradoshamInfoRow.setLayoutParams(new TableRow.LayoutParams());
            TextView tvPradoshamInfoCell;

            tvPradoshamInfoCell = new TextView(this);
            tvPradoshamInfoCell.setText(strYear);
            tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
            tvPradoshamInfoCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tblRowPradoshamInfoRow.addView(tvPradoshamInfoCell);

            tvPradoshamInfoCell = new TextView(this);
            tvPradoshamInfoCell.setText(tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameTamilDate)));
            tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
            tvPradoshamInfoCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tblRowPradoshamInfoRow.addView(tvPradoshamInfoCell);

            tvPradoshamInfoCell = new TextView(this);
            tvPradoshamInfoCell.setText(tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameEnglishDate)));
            tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
            tvPradoshamInfoCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tblRowPradoshamInfoRow.addView(tvPradoshamInfoCell);

            int intDayOfWeek = Integer.parseInt(tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameDay)));
            tvPradoshamInfoCell = new TextView(this);
            tvPradoshamInfoCell.setText(PradoshamInfo.getDayName(intDayOfWeek));
            tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
            tvPradoshamInfoCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tblRowPradoshamInfoRow.addView(tvPradoshamInfoCell);

            tblSankalpamScreenPradoshamInfoTable.addView(tblRowPradoshamInfoRow);
        }
    }

    /* This function will pass the sponsor upayatharar information to be filled in the sankalpam
     * screen upayatharar info table. */
    private void prvLoadSponsorFamilyInfoTable()
    {
        List<Map<String, String>> currentSponsorFamilyInfoList = currentSponsorInfo.getFamilyDetails();

        for (int i=0; i<currentSponsorFamilyInfoList.size(); i++)
        {
            Map<String, String> tempFamilyInfoMap = currentSponsorFamilyInfoList.get(i);
            TableRow tblRowPradoshamInfoRow = new TableRow(this);
            tblRowPradoshamInfoRow.setLayoutParams(new TableRow.LayoutParams());
            TextView tvPradoshamInfoCell;

            tvPradoshamInfoCell = new TextView(this);
            tvPradoshamInfoCell.setText(tempFamilyInfoMap.get(this.getResources().getString(R.string.SponsorFamilyInfoAttributeNameName)));
            tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
            tvPradoshamInfoCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tblRowPradoshamInfoRow.addView(tvPradoshamInfoCell);

            tvPradoshamInfoCell = new TextView(this);
            tvPradoshamInfoCell.setText(tempFamilyInfoMap.get(this.getResources().getString(R.string.SponsorFamilyInfoAttributeNameGothram)));
            tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
            tvPradoshamInfoCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tblRowPradoshamInfoRow.addView(tvPradoshamInfoCell);

            tvPradoshamInfoCell = new TextView(this);
            tvPradoshamInfoCell.setText(tempFamilyInfoMap.get(this.getResources().getString(R.string.SponsorFamilyInfoAttributeNameStar)));
            tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
            tvPradoshamInfoCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tblRowPradoshamInfoRow.addView(tvPradoshamInfoCell);

            tvPradoshamInfoCell = new TextView(this);
            tvPradoshamInfoCell.setText(tempFamilyInfoMap.get(this.getResources().getString(R.string.SponsorFamilyInfoAttributeNameRasi)));
            tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
            tvPradoshamInfoCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tblRowPradoshamInfoRow.addView(tvPradoshamInfoCell);

            tblSankalpamScreenFamilyInfoTable.addView(tblRowPradoshamInfoRow);
        }
    }
}
