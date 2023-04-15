package com.example.avoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.avoorapp.support.CustomAlertDialog;
import com.example.avoorapp.support.FirebaseDownloadListener;
import com.example.avoorapp.support.FirebaseWrapper;
import com.example.avoorapp.support.PradoshamInfo;
import com.example.avoorapp.support.SponsorsInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/* This class represents the Pradosham screen which displays the details of which pradosham is
 * sponsored by which sponsor. */
public class PradoshamDatesSponsorsScreen extends AppCompatActivity
{
    /* member variables */
    private TableLayout tblPradoshamDatesSponsorsScreenPradoshamInfoTable;
    private Spinner spnPradoshamDatesSponsorsScreenYearDropdown;
    private FirebaseWrapper tempWrapper;
    private ArrayList<PradoshamInfo> pradoshamInfoList;
    private ArrayList<SponsorsInfo> sponsorsInfoList;
    private CustomAlertDialog alertDialog;
//    private int intFirebaseDownloadModerator = 0;
//    private final int intNrOfAsyncFirebaseDownloads = 2;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        Toolbar tlbarPradoshamDatesSponsorsScreenToolbar;
        TextView tvPradoshamDatesSponsorsScreenTxtViewAppName, tvPradoshamDatesSponsorsScreenTxtViewTitleMessage;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pradosham_dates_sponsors_screen);

        tblPradoshamDatesSponsorsScreenPradoshamInfoTable = findViewById(R.id.PradoshamDatesSponsorsScreenPradoshamInfoTable);
        tvPradoshamDatesSponsorsScreenTxtViewAppName = findViewById(R.id.PradoshamDatesSponsorsScreenTxtViewAppName);
        tvPradoshamDatesSponsorsScreenTxtViewTitleMessage = findViewById(R.id.PradoshamDatesSponsorsScreenTxtViewTitleMessage);
        spnPradoshamDatesSponsorsScreenYearDropdown = findViewById(R.id.PradoshamDatesSponsorsScreenYearDropdown);
        tlbarPradoshamDatesSponsorsScreenToolbar = findViewById(R.id.PradoshamDatesSponsorsScreenToolbar);
        setSupportActionBar(tlbarPradoshamDatesSponsorsScreenToolbar);

        tvPradoshamDatesSponsorsScreenTxtViewAppName.setText(
        getResources()
        .getString(R.string.AvoorAppDisplayNameEnglish)
        );
        tvPradoshamDatesSponsorsScreenTxtViewTitleMessage.setText(
        getResources()
        .getString(R.string.PradoshamDatesSponsorsScreenTitleEnglish)
        );

        alertDialog = new CustomAlertDialog(this);
        tempWrapper = new FirebaseWrapper(getApplicationContext());
        prvObtainSponsorsInfoFromFirebase();
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
                prvLoadYearSelectDropdownWithInfo();
                prvLoadPradoshamInfoTableHeaders();
                prvLoadPradoshamInfoTableWithInfo(0);
            }

            @Override
            public void onDownloadFailureCallback()
            {
                alertDialog.displayAlertMessage(getApplicationContext().getResources().getString(R.string.PradoshamDatesSponsorsScreenAllSponsorInfoStatusDownloadFailed));
            }
        });
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
                prvObtainPradoshamInfoFromFirebase();
            }

            @Override
            public void onDownloadFailureCallback()
            {
                alertDialog.displayAlertMessage(getApplicationContext().getResources().getString(R.string.PradoshamDatesSponsorsScreenAllPradoshamInfoStatusDownloadFailed));
            }
        });
    }

    private void prvLoadYearSelectDropdownWithInfo()
    {
        ArrayList<String> strYearSelectDropdownList = new ArrayList<>();

        for (int i=0; i<pradoshamInfoList.size(); i++)
        {
            strYearSelectDropdownList.add(pradoshamInfoList.get(i).getYearName());
        }

        spnPradoshamDatesSponsorsScreenYearDropdown.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strYearSelectDropdownList));
        spnPradoshamDatesSponsorsScreenYearDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                /* i corresponds to the selected index, which is in the same order as in the array
                 * of options set to the dropdown. Pass this index so that the corresponding year's
                 * info can be displayed in the table. */
                tblPradoshamDatesSponsorsScreenPradoshamInfoTable.removeAllViews();
                prvLoadPradoshamInfoTableHeaders();
                prvLoadPradoshamInfoTableWithInfo(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                /* Do nothing here. */
            }
        });
    }

    /* This private function will load the table with the table headers. */
    private void prvLoadPradoshamInfoTableHeaders()
    {
        TableRow tblrowPradoshamInfoRow = new TableRow(this);
        tblrowPradoshamInfoRow.setLayoutParams(new TableRow.LayoutParams());
        TextView tvPradoshamInfoCell;

        tvPradoshamInfoCell = new TextView(this);
        tvPradoshamInfoCell.setText(getApplicationContext().getResources().getString(R.string.PradoshamDatesSponsorsScreenSponsorsTableHeaderEnglishDate));
        tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
        tblrowPradoshamInfoRow.addView(tvPradoshamInfoCell);

        tvPradoshamInfoCell = new TextView(this);
        tvPradoshamInfoCell.setText(getApplicationContext().getResources().getString(R.string.PradoshamDatesSponsorsScreenSponsorsTableHeaderDay));
        tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
        tblrowPradoshamInfoRow.addView(tvPradoshamInfoCell);

        tvPradoshamInfoCell = new TextView(this);
        tvPradoshamInfoCell.setText(getApplicationContext().getResources().getString(R.string.PradoshamDatesSponsorsScreenSponsorsTableHeaderName));
        tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
        tblrowPradoshamInfoRow.addView(tvPradoshamInfoCell);

        tvPradoshamInfoCell = new TextView(this);
        tvPradoshamInfoCell.setText(getApplicationContext().getResources().getString(R.string.PradoshamDatesSponsorsScreenSponsorsTableHeaderLocation));
        tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
        tblrowPradoshamInfoRow.addView(tvPradoshamInfoCell);

        tblPradoshamDatesSponsorsScreenPradoshamInfoTable.addView(tblrowPradoshamInfoRow);
    }

    /* This private function will load the table in the screen with the pradosham info retrieved
     * from firebase. */
    private void prvLoadPradoshamInfoTableWithInfo(int intSelectedYearIndex)
    {
        final int intIndexEnglishDate = 0, intIndexDay = 1, intIndexName = 2, intIndexLocation = 3;
        HashMap<String, String[]> tempSponsorPradoshamMapping = new HashMap<String, String[]>();
        PradoshamInfo tempPradoshamInfo = pradoshamInfoList.get(intSelectedYearIndex);

        for (int i=0; i<sponsorsInfoList.size(); i++)
        {
            SponsorsInfo tempSponsorInfo = sponsorsInfoList.get(i);

            if (tempSponsorInfo.getSponsoredPradoshams().get(tempPradoshamInfo.getYearName()).length() != 0)
            {
                String[] strIndicesStringArr = tempSponsorInfo.getSponsoredPradoshams().get(tempPradoshamInfo.getYearName()).split(",");

                for (int j = 0; j < strIndicesStringArr.length; j++) {
                    String[] strTempInfoArr = new String[4];
                    int intPradoshamIndex = Integer.parseInt(strIndicesStringArr[j]);
                    String strEnglishDateKey = this.getResources().getString(R.string.PradoshamInfoAttributeNameEnglishDate);
                    String strDayOfWeekKey = this.getResources().getString(R.string.PradoshamInfoAttributeNameDay);
                    String strPradoshamIdKey = this.getResources().getString(R.string.PradoshamInfoAttributeNameId);
                    String strPradoshamId = tempPradoshamInfo.getPradoshamDetails().get(intPradoshamIndex).get(strPradoshamIdKey);
                    int intDayOfWeek = Integer.parseInt(tempPradoshamInfo.getPradoshamDetails().get(intPradoshamIndex).get(strDayOfWeekKey));

                    strTempInfoArr[intIndexEnglishDate] = tempPradoshamInfo.getPradoshamDetails().get(intPradoshamIndex).get(strEnglishDateKey);
                    strTempInfoArr[intIndexDay] = PradoshamInfo.getDayName(intDayOfWeek);
                    strTempInfoArr[intIndexName] = tempSponsorInfo.getName();
                    strTempInfoArr[intIndexLocation] = tempSponsorInfo.getLocation();
                    tempSponsorPradoshamMapping.put(strPradoshamId, strTempInfoArr);
                }
            }
        }

        Log.d("debugPradoshamSponsor", pradoshamInfoList.get(intSelectedYearIndex).getYearName());
        List<Map<String, String>> tempPradoshamDetailsList = pradoshamInfoList.get(intSelectedYearIndex).getPradoshamDetails();

        for (int j=0; j<tempPradoshamDetailsList.size(); j++) {
            String strPradoshamId = tempPradoshamDetailsList.get(j).get(this.getResources().getString(R.string.PradoshamInfoAttributeNameId));
            String[] tempPradoshamInfoArr = tempSponsorPradoshamMapping.get(strPradoshamId);

            TableRow tblrowPradoshamInfoRow = new TableRow(this);
            tblrowPradoshamInfoRow.setLayoutParams(new TableRow.LayoutParams());
            TextView tvPradoshamInfoCell;

            tvPradoshamInfoCell = new TextView(this);
            tvPradoshamInfoCell.setText(tempPradoshamInfoArr[intIndexEnglishDate]);
            tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
            tblrowPradoshamInfoRow.addView(tvPradoshamInfoCell);

            tvPradoshamInfoCell = new TextView(this);
            tvPradoshamInfoCell.setText(tempPradoshamInfoArr[intIndexDay]);
            tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
            tblrowPradoshamInfoRow.addView(tvPradoshamInfoCell);

            tvPradoshamInfoCell = new TextView(this);
            tvPradoshamInfoCell.setText(tempPradoshamInfoArr[intIndexName]);
            tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
            tblrowPradoshamInfoRow.addView(tvPradoshamInfoCell);

            tvPradoshamInfoCell = new TextView(this);
            tvPradoshamInfoCell.setText(tempPradoshamInfoArr[intIndexLocation]);
            tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
            tblrowPradoshamInfoRow.addView(tvPradoshamInfoCell);

            tblPradoshamDatesSponsorsScreenPradoshamInfoTable.addView(tblrowPradoshamInfoRow);
        }
    }
}
