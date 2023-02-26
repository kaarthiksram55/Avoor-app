package com.example.avoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/* This class represents the home screen which appears when the app is just opened. */
public class HomeScreen extends AppCompatActivity
{
    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        TextView tvHomeScreenTxtViewAppName, tvHomeScreenTxtViewTitleMessage, tvHomeScreenTxtViewUserInfo, tvHomeScreenTxtViewPrdshPrtc;
        ListView lvHomeScreenListViewMenuItems;
        Toolbar tbHomeScreenToolbar;
        String[] strListViewMenuItemsNames =
        {
            this.getResources().getString(R.string.MenuItemUsersList),
            this.getResources().getString(R.string.MenuItemPradoshamDates),
            this.getResources().getString(R.string.MenuItemSankalpamDetails),
            this.getResources().getString(R.string.MenuItemPhotosAndVideos)
        };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        tvHomeScreenTxtViewAppName = findViewById(R.id.HomeScreenTxtViewAppName);
        tvHomeScreenTxtViewTitleMessage = findViewById(R.id.HomeScreenTxtViewTitleMessage);
        tvHomeScreenTxtViewUserInfo = findViewById(R.id.HomeScreenTxtViewUserInfo);
        tvHomeScreenTxtViewPrdshPrtc = findViewById(R.id.HomeScreenTxtViewPrdshPrtc);
        lvHomeScreenListViewMenuItems = findViewById(R.id.HomeScreenListViewMenuItems);
        tbHomeScreenToolbar = findViewById(R.id.HomeScreenToolbar);

        tvHomeScreenTxtViewAppName.setText(this.getResources().getString(R.string.AvoorAppDisplayNameEnglish));
        tvHomeScreenTxtViewTitleMessage.setText(this.getResources().getString(R.string.HomeScreenTitleEnglish));
        tvHomeScreenTxtViewPrdshPrtc.setText("Next on: 27th Feb 2023");
        lvHomeScreenListViewMenuItems.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strListViewMenuItemsNames));
        setSupportActionBar(tbHomeScreenToolbar);

        /* The mobile number received from the previous screen will be valid as checks have been
         * performed there. Hence no need to check here once again. */
        Intent intent = getIntent();
        long longMobileNumber = intent.getLongExtra(this.getResources().getString(R.string.LandingScreenIntentUserNameKey), 0);
        tvHomeScreenTxtViewUserInfo.setText("Welcome, " + longMobileNumber);
    }

    /* This method has been implemented to generate the ellipsis (three dots) menu. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_screen_ellipsis_menu, menu);
        return true;
    }
}
