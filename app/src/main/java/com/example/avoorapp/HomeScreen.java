package com.example.avoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/* This class represents the home screen which appears after logging in via the Landing screen. */
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
        lvHomeScreenListViewMenuItems.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strListViewMenuItemsNames));
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
        /* Here, menu has been created in xml and linked here. Menu items can also be directly
         * specified as objects here directly, for e.g., menu.add("menu_name"). */
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_screen_ellipsis_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        /* Check which menu item has been selected and accordingly direct to the desired page. */
        if (item.getTitle().equals(this.getResources().getString(R.string.ThreeDotsMenuLogoutName)))
        {
            /* If the 'log out' menu item is selected, open the landing screen again. */
            openLandingScreen();
        }
        else if (item.getTitle().equals(this.getResources().getString(R.string.ThreeDotsMenuAboutName)))
        {
            /* If the 'about' menu item is selected, open the landing screen again. */
            openAboutScreen();
        }

        return super.onOptionsItemSelected(item);
    }

    /* This method has been implemented for the Logout menu option. */
    public void openLandingScreen()
    {
        /* Make an intent object with the below flags set to prevent going back to the application
         * screens with the back button after opting to log out. */
        Intent intent = new Intent(this.getApplicationContext(), LandingScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /* This method has been implemented for the about menu option. */
    public void openAboutScreen()
    {
        /* Make an intent object to go to the about screen. User should be able to get back to the
         * home screen, so do not set any intent flags. */
        Intent intent = new Intent(this.getApplicationContext(), AboutScreen.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
