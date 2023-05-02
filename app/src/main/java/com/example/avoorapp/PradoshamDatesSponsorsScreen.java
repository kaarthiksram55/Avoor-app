package com.example.avoorapp;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
import com.example.avoorapp.support.TableCell;

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
    private TableCell tableCell;
    private int intTableColor;
    private int intTableCellColor;

    private final float floatTableHeaderSizeSp = 13f;
    private final float floatTableCellSizeSp = 13f;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        Toolbar tlbarPradoshamDatesSponsorsScreenToolbar;
        TextView tvPradoshamDatesSponsorsScreenTxtViewAppName, tvPradoshamDatesSponsorsScreenTxtViewTitleMessage;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pradosham_dates_sponsors_screen);
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

        tblPradoshamDatesSponsorsScreenPradoshamInfoTable.setBackgroundColor(intTableColor);
        tableCell = new TableCell(this);
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
        
        String strTableHeaderEnglishDate = this.getResources().getString(R.string.PradoshamDatesSponsorsScreenSponsorsTableHeaderEnglishDate);
        tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderEnglishDate, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderDay = this.getResources().getString(R.string.PradoshamDatesSponsorsScreenSponsorsTableHeaderDay);
        tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderDay, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderName = this.getResources().getString(R.string.PradoshamDatesSponsorsScreenSponsorsTableHeaderName);
        tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderName, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderLocation = this.getResources().getString(R.string.PradoshamDatesSponsorsScreenSponsorsTableHeaderLocation);
        tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderLocation, intTableCellColor, floatTableHeaderSizeSp, true));
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

            if ((tempSponsorInfo.getSponsoredPradoshams() != null) && (tempSponsorInfo.getSponsoredPradoshams().get(tempPradoshamInfo.getYearName()).length() != 0))
            {
                String[] strIndicesStringArr = tempSponsorInfo.getSponsoredPradoshams().get(tempPradoshamInfo.getYearName()).split(",");

                for (int j = 0; j < strIndicesStringArr.length; j++)
                {
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
            else
            {
                /* Do nothing here. */
            }
        }

        Log.d("debugPradoshamSponsor", pradoshamInfoList.get(intSelectedYearIndex).getYearName());
        List<Map<String, String>> tempPradoshamDetailsList = pradoshamInfoList.get(intSelectedYearIndex).getPradoshamDetails();

        for (int j=0; j<tempPradoshamDetailsList.size(); j++) {
            TableRow tblrowPradoshamInfoRow = new TableRow(this);
            tblrowPradoshamInfoRow.setLayoutParams(new TableRow.LayoutParams());

            String strPradoshamId = tempPradoshamDetailsList.get(j).get(this.getResources().getString(R.string.PradoshamInfoAttributeNameId));
            String[] tempPradoshamInfoArr = tempSponsorPradoshamMapping.get(strPradoshamId);

            if (tempPradoshamInfoArr == null)
            {
                String strTableCellEnglishDate = tempPradoshamDetailsList.get(j).get(this.getResources().getString(R.string.PradoshamInfoAttributeNameEnglishDate));
                tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellEnglishDate, intTableCellColor, floatTableCellSizeSp, false));
                int intDayOfWeek = Integer.parseInt(tempPradoshamDetailsList.get(j).get(this.getResources().getString(R.string.PradoshamInfoAttributeNameDay)));
                String strTableCellDay = PradoshamInfo.getDayName(intDayOfWeek);
                tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellDay, intTableCellColor, floatTableCellSizeSp, false));
                String strTableCellName = "";
                tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellName, intTableCellColor, floatTableCellSizeSp, false));
                String strTableCellLocation = "";
                tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellLocation, intTableCellColor, floatTableCellSizeSp, false));
            }
            else
            {
                String strTableCellEnglishDate = tempPradoshamInfoArr[intIndexEnglishDate];
                tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellEnglishDate, intTableCellColor, floatTableCellSizeSp, false));
                String strTableCellDay = tempPradoshamInfoArr[intIndexDay];
                tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellDay, intTableCellColor, floatTableCellSizeSp, false));
                String strTableCellName = tempPradoshamInfoArr[intIndexName];
                tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellName, intTableCellColor, floatTableCellSizeSp, false));
                String strTableCellLocation = tempPradoshamInfoArr[intIndexLocation];
                tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellLocation, intTableCellColor, floatTableCellSizeSp, false));
            }

            tblPradoshamDatesSponsorsScreenPradoshamInfoTable.addView(tblrowPradoshamInfoRow);
        }
    }
}
