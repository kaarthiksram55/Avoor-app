package com.example.avoorapp;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
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
import com.example.avoorapp.support.TableCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/* This class represents the Pradoshams screen which displays the details of all pradoshams. */
public class PradoshamDatesScreen extends AppCompatActivity
{
    /* member variables */
    private TableLayout tblPradoshamDatesScreenPradoshamInfoTable;
    private Spinner spnPradoshamDatesScreenYearDropdown;
    private FirebaseWrapper tempWrapper;
    private ArrayList<PradoshamInfo> pradoshamInfoList;
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
        Toolbar tlbarPradoshamDatesScreenToolbar;
        TextView tvPradoshamDatesScreenTxtViewAppName, tvPradoshamDatesScreenTxtViewTitleMessage;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pradosham_dates_screen);
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

        tblPradoshamDatesScreenPradoshamInfoTable = findViewById(R.id.PradoshamDatesScreenPradoshamInfoTable);
        tvPradoshamDatesScreenTxtViewAppName = findViewById(R.id.PradoshamDatesScreenTxtViewAppName);
        tvPradoshamDatesScreenTxtViewTitleMessage = findViewById(R.id.PradoshamDatesScreenTxtViewTitleMessage);
        spnPradoshamDatesScreenYearDropdown = findViewById(R.id.PradoshamDatesScreenYearDropdown);
        tlbarPradoshamDatesScreenToolbar = findViewById(R.id.PradoshamDatesScreenToolbar);
        setSupportActionBar(tlbarPradoshamDatesScreenToolbar);

        tvPradoshamDatesScreenTxtViewAppName.setText(
        getResources()
        .getString(R.string.AvoorAppDisplayNameEnglish)
        );
        tvPradoshamDatesScreenTxtViewTitleMessage.setText(
        getResources()
        .getString(R.string.PradoshamDatesScreenTitleEnglish)
        );

        tblPradoshamDatesScreenPradoshamInfoTable.setBackgroundColor(intTableColor);
        tableCell = new TableCell(this);
        alertDialog = new CustomAlertDialog(this);
        tempWrapper = new FirebaseWrapper(getApplicationContext());
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
                prvloadYearSelectDropdownWithInfo();
            }

            @Override
            public void onDownloadFailureCallback()
            {
                alertDialog.displayAlertMessage(getApplicationContext().getResources().getString(R.string.PradoshamScreenAllInfoStatusDownloadFailed));
            }
        });
    }

    /* This private function will load the year select dropdown with the downloaded pradosham
     * financial year dates. */
    private void prvloadYearSelectDropdownWithInfo()
    {
        ArrayList<String> strYearSelectDropdownList = new ArrayList<>();

        for (int i=0; i<pradoshamInfoList.size(); i++)
        {
            strYearSelectDropdownList.add(pradoshamInfoList.get(i).getYearName());
        }

        spnPradoshamDatesScreenYearDropdown.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strYearSelectDropdownList));
        spnPradoshamDatesScreenYearDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /* i corresponds to the selected index, which is in the same order as in the array
                 * of options set to the dropdown. Pass this index so that the corresponding year's
                 * info can be displayed in the table. */
                tblPradoshamDatesScreenPradoshamInfoTable.removeAllViews();
                prvLoadPradoshamTableHeaders();
                prvLoadPradoshamInfoTableWithInfo(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                /* Do nothing here. */
            }
        });
    }

    /* This private function will load the table with the table headers. */
    private void prvLoadPradoshamTableHeaders()
    {
        TableRow tblrowPradoshamInfoRow = new TableRow(this);
        tblrowPradoshamInfoRow.setLayoutParams(new TableRow.LayoutParams());

        String strTableHeaderSNo = this.getResources().getString(R.string.PradoshamDatesScreenTableHeaderSNo);
        tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderSNo, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderId = this.getResources().getString(R.string.PradoshamDatesScreenTableHeaderId);
        tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderId, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderTamilDate = this.getResources().getString(R.string.PradoshamDatesScreenTableHeaderTamilDate);
        tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderTamilDate, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderEnglishDate = this.getResources().getString(R.string.PradoshamDatesScreenTableHeaderEnglishDate);
        tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderEnglishDate, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderDay = this.getResources().getString(R.string.PradoshamDatesScreenTableHeaderDay);
        tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderDay, intTableCellColor, floatTableHeaderSizeSp, true));

        tblPradoshamDatesScreenPradoshamInfoTable.addView(tblrowPradoshamInfoRow);
    }

    /* This private function will load the table in the screen with the pradosham info retrieved
     * from firebase. */
    private void prvLoadPradoshamInfoTableWithInfo(int intSelectedYearIndex)
    {
        int intSrNo = 1;
        List<Map<String, String>> tempPradoshamDetailsList = pradoshamInfoList.get(intSelectedYearIndex).getPradoshamDetails();

        for (int j=0; j<tempPradoshamDetailsList.size(); j++)
        {
            Map<String, String> tempPradoshamInfoMap = tempPradoshamDetailsList.get(j);

            TableRow tblrowPradoshamInfoRow = new TableRow(this);
            tblrowPradoshamInfoRow.setLayoutParams(new TableRow.LayoutParams());

            String strTableCellSNo = Integer.toString(intSrNo++);   
            tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellSNo, intTableCellColor, floatTableCellSizeSp, false));
            String strTableCellId = tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameId));
            tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellId, intTableCellColor, floatTableCellSizeSp, false));
            String strTableCellTamilDate = tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameTamilDate));
            tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellTamilDate, intTableCellColor, floatTableCellSizeSp, false));
            String strTableCellEnglishDate = tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameEnglishDate));
            tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellEnglishDate, intTableCellColor, floatTableCellSizeSp, false));
            int intDayOfWeek = Integer.parseInt(tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameDay)));
            String strTableCellDay = PradoshamInfo.getDayName(intDayOfWeek);
            tblrowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellDay, intTableCellColor, floatTableCellSizeSp, false));

            tblPradoshamDatesScreenPradoshamInfoTable.addView(tblrowPradoshamInfoRow);
        }
    }
}
