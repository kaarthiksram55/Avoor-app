package com.example.avoorapp;

import android.content.Intent;
import android.os.Bundle;
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
    private TableCell tableCell;
    private int intTableColor;
    private int intTableCellColor;

    private final float floatTableHeaderSizeSp = 13f;
    private final float floatTableCellSizeSp = 13f;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        Toolbar tlbarSankalpamScreenToolbar;
        TextView tvSankalpamScreenTxtViewAppName, tvSankalpamScreenTxtViewTitleMessage, tvSankalpamScreenTxtViewUpayamName;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sankalpam_screen);
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

        tblSankalpamScreenPradoshamInfoTable = findViewById(R.id.SankalpamScreenPradoshamInfoTable);
        tblSankalpamScreenFamilyInfoTable = findViewById(R.id.SankalpamScreenFamilyInfoTable);
        tvSankalpamScreenTxtViewAppName = findViewById(R.id.SankalpamScreenTxtViewAppName);
        tvSankalpamScreenTxtViewTitleMessage = findViewById(R.id.SankalpamScreenTxtViewTitleMessage);
        tvSankalpamScreenTxtViewUpayamName = findViewById(R.id.SankalpamScreenTxtViewUpayamName);
        tlbarSankalpamScreenToolbar = findViewById(R.id.SankalpamScreenToolbar);
        setSupportActionBar(tlbarSankalpamScreenToolbar);

        Intent intent = getIntent();
        currentSponsorInfo = (SponsorsInfo)intent.getSerializableExtra(this.getResources().getString(R.string.HomeScreenSponsorInfoIntentKey));
        tvSankalpamScreenTxtViewAppName.setText(
        getResources()
        .getString(R.string.AvoorAppDisplayNameEnglish)
        );
        tvSankalpamScreenTxtViewTitleMessage.setText(
        getResources()
        .getString(R.string.SankalpamScreenTitleEnglish)
        );
        tvSankalpamScreenTxtViewUpayamName.setText(
        this.getResources().getString(R.string.SankalpamScreenUpayamLabel) + " " + currentSponsorInfo.getName()
        );

        tblSankalpamScreenPradoshamInfoTable.setBackgroundColor(intTableColor);
        tblSankalpamScreenFamilyInfoTable.setBackgroundColor(intTableColor);
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
                prvLoadSponsorPradoshamTableWithHeader();
                prvLoadSponsorFamilyInfoTableWithHeader();
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

    /* This function will load the pradosham table with the header row. */
    private void prvLoadSponsorPradoshamTableWithHeader()
    {
        TableRow tblRowPradoshamInfoRow = new TableRow(this);

        String strTableHeaderYear = this.getResources().getString(R.string.SankalpamScreenPradoshamInfoTableHeaderYear);
        tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderYear, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderTamilDate = this.getResources().getString(R.string.SankalpamScreenPradoshamInfoTableHeaderTamilDate);
        tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderTamilDate, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderEnglishDate = this.getResources().getString(R.string.SankalpamScreenPradoshamInfoTableHeaderEnglishDate);
        tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderEnglishDate, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderDay = this.getResources().getString(R.string.SankalpamScreenPradoshamInfoTableHeaderDay);
        tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderDay, intTableCellColor, floatTableHeaderSizeSp, true));
        tblSankalpamScreenPradoshamInfoTable.addView(tblRowPradoshamInfoRow);
    }

    /* This function will load the pradosham table with those pradosham details to which the
     * current sponsor is associated. */
    private void prvLoadSponsorPradoshamTableWithInfo(String strYear, ArrayList<Map<String, String>> pradoshamInfoList)
    {
        for (int j=0; j<pradoshamInfoList.size(); j++)
        {
            Map<String, String> tempPradoshamInfoMap = pradoshamInfoList.get(j);
            TableRow tblRowPradoshamInfoRow = new TableRow(this);

            String strTableCellYear = strYear;
            tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellYear, intTableCellColor, floatTableCellSizeSp, false));
            String strTableCellTamilDate = tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameTamilDate));
            tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellTamilDate, intTableCellColor, floatTableCellSizeSp, false));
            String strTableCellEnglishDate = tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameEnglishDate));
            tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellEnglishDate, intTableCellColor, floatTableCellSizeSp, false));
            int intDayOfWeek = Integer.parseInt(tempPradoshamInfoMap.get(getApplicationContext().getResources().getString(R.string.PradoshamInfoAttributeNameDay)));
            String strTableCellDay = PradoshamInfo.getDayName(intDayOfWeek);            
            tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellDay, intTableCellColor, floatTableCellSizeSp, false));
            tblSankalpamScreenPradoshamInfoTable.addView(tblRowPradoshamInfoRow);
        }
    }

    /* This function will load the Sponsor Family table with the heade row. */
    private void prvLoadSponsorFamilyInfoTableWithHeader()
    {
        TableRow tblRowPradoshamInfoRow = new TableRow(this);

        String strTableHeaderName = this.getResources().getString(R.string.SankalpamScreenFamilyInfoTableHeaderName);
        tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderName, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderGothram = this.getResources().getString(R.string.SankalpamScreenFamilyInfoTableHeaderGothram);
        tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderGothram, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderStar = this.getResources().getString(R.string.SankalpamScreenFamilyInfoTableHeaderStar);
        tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderStar, intTableCellColor, floatTableHeaderSizeSp, true));
        String strTableHeaderRasi = this.getResources().getString(R.string.SankalpamScreenFamilyInfoTableHeaderRasi);
        tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableHeaderRasi, intTableCellColor, floatTableHeaderSizeSp, true));
        tblSankalpamScreenFamilyInfoTable.addView(tblRowPradoshamInfoRow);
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

            String strTableCellName = tempFamilyInfoMap.get(this.getResources().getString(R.string.SponsorFamilyInfoAttributeNameName));
            tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellName, intTableCellColor, floatTableCellSizeSp, false));
            String strTableCellGothram = tempFamilyInfoMap.get(this.getResources().getString(R.string.SponsorFamilyInfoAttributeNameGothram));
            tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellGothram, intTableCellColor, floatTableCellSizeSp, false));
            String strTableCellStar = tempFamilyInfoMap.get(this.getResources().getString(R.string.SponsorFamilyInfoAttributeNameStar));
            tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellStar, intTableCellColor, floatTableCellSizeSp, false));
            String strTableCellRasi = tempFamilyInfoMap.get(this.getResources().getString(R.string.SponsorFamilyInfoAttributeNameRasi));
            tblRowPradoshamInfoRow.addView(tableCell.generateCell(strTableCellRasi, intTableCellColor, floatTableCellSizeSp, false));
            tblSankalpamScreenFamilyInfoTable.addView(tblRowPradoshamInfoRow);
        }
    }
}
