package com.example.avoorapp;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.avoorapp.support.CustomAlertDialog;
import com.example.avoorapp.support.FirebaseDownloadListener;
import com.example.avoorapp.support.FirebaseWrapper;
import com.example.avoorapp.support.PradoshamInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/* This class represents the Pradoshams screen which displays the details of all pradosham sponsors. */
public class PradoshamDatesScreen extends AppCompatActivity {
    /* member variables */
    private TableLayout tblPradoshamDatesScreenPradoshamInfoTable;
    private FirebaseWrapper tempWrapper;
    private ArrayList<PradoshamInfo> pradoshamInfoList;
    private CustomAlertDialog alertDialog;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState) {
        Toolbar tlbarPradoshamDatesScreenToolbar;
        TextView tvPradoshamDatesScreenTxtViewAppName, tvPradoshamDatesScreenTxtViewTitleMessage;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pradosham_dates_screen);

        tblPradoshamDatesScreenPradoshamInfoTable = findViewById(R.id.PradoshamDatesScreenPradoshamInfoTable);
        tvPradoshamDatesScreenTxtViewAppName = findViewById(R.id.PradoshamDatesScreenTxtViewAppName);
        tvPradoshamDatesScreenTxtViewTitleMessage = findViewById(R.id.PradoshamDatesScreenTxtViewTitleMessage);
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
                loadPradoshamInfoTableWithInfo(pradoshamInfoList);
            }

            @Override
            public void onDownloadFailureCallback()
            {
                alertDialog.displayAlertMessage(getApplicationContext().getResources().getString(R.string.PradoshamScreenAllInfoStatusDownloadFailed));
            }
        });
    }

    /* This private function will load the table in the screen with the pradosham info retrieved
     * from firebase. */
    private void loadPradoshamInfoTableWithInfo(ArrayList<PradoshamInfo> pradoshamInfoList)
    {
        int intSrNo = 1;

        for (int i=0; i<pradoshamInfoList.size(); i++)
        {
            List<Map<String, String>> tempPradoshamDetailsList = pradoshamInfoList.get(i).getPradoshamDetails();
            for (int j=0; j<tempPradoshamDetailsList.size(); j++)
            {
                Map<String, String> tempPradoshamInfoMap = tempPradoshamDetailsList.get(j);

                TableRow tblrowPradoshamInfoRow = new TableRow(this);
                tblrowPradoshamInfoRow.setLayoutParams(new TableRow.LayoutParams()); // TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
                TextView tvPradoshamInfoCell;

                tvPradoshamInfoCell = new TextView(this);
                tvPradoshamInfoCell.setText(Integer.toString(intSrNo++));
                tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
                tblrowPradoshamInfoRow.addView(tvPradoshamInfoCell);

                tvPradoshamInfoCell = new TextView(this);
                tvPradoshamInfoCell.setText(tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameId)));
                tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
                tblrowPradoshamInfoRow.addView(tvPradoshamInfoCell);

                tvPradoshamInfoCell = new TextView(this);
                tvPradoshamInfoCell.setText(tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameTamilDate)));
                tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
                tblrowPradoshamInfoRow.addView(tvPradoshamInfoCell);

                tvPradoshamInfoCell = new TextView(this);
                tvPradoshamInfoCell.setText(tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameEnglishDate)));
                tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
                tblrowPradoshamInfoRow.addView(tvPradoshamInfoCell);

                int intDayOfWeek = Integer.parseInt(tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameDay)));
                tvPradoshamInfoCell = new TextView(this);
                tvPradoshamInfoCell.setText(PradoshamInfo.getDayName(intDayOfWeek));
                tvPradoshamInfoCell.setPadding(10, 10, 10, 10);
                tblrowPradoshamInfoRow.addView(tvPradoshamInfoCell);

                tblPradoshamDatesScreenPradoshamInfoTable.addView(tblrowPradoshamInfoRow);
            }
        }
    }
}
