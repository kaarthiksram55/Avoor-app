package com.example.avoorapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.avoorapp.support.CustomAlertDialog;
import com.example.avoorapp.support.FirebaseDownloadListener;
import com.example.avoorapp.support.FirebaseWrapper;
import com.example.avoorapp.support.SponsorsInfo;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/* This class represents the home screen which appears after logging in via the Landing screen. */
public class HomeScreen extends AppCompatActivity
{
    /* Member variables. */
    private SponsorsInfo currentSponsorInfo;
    private FirebaseWrapper tempWrapper;
    private CustomAlertDialog alertDialog;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        TextView tvHomeScreenTxtViewAppName, tvHomeScreenTxtViewTitleMessage, tvHomeScreenTxtViewUserInfo, tvHomeScreenTxtViewPrdshPrtc;
        ListView lvHomeScreenListViewMenuItems;
        Toolbar tlbarHomeScreenToolbar;
        ArrayList<String> strListViewMenuItemsNamesList = new ArrayList<String>();
        Intent intent = getIntent();
        currentSponsorInfo = (SponsorsInfo)intent.getSerializableExtra(this.getResources().getString(R.string.LandingScreenIntentSponsorInfoKey));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        tvHomeScreenTxtViewAppName = findViewById(R.id.HomeScreenTxtViewAppName);
        tvHomeScreenTxtViewTitleMessage = findViewById(R.id.HomeScreenTxtViewTitleMessage);
        tvHomeScreenTxtViewUserInfo = findViewById(R.id.HomeScreenTxtViewUserInfo);
        tvHomeScreenTxtViewPrdshPrtc = findViewById(R.id.HomeScreenTxtViewPrdshPrtc);
        lvHomeScreenListViewMenuItems = findViewById(R.id.HomeScreenListViewMenuItems);
        tlbarHomeScreenToolbar = findViewById(R.id.HomeScreenToolbar);

        tvHomeScreenTxtViewUserInfo.setText("Welcome, " + currentSponsorInfo.getName());
        tvHomeScreenTxtViewAppName.setText(this.getResources().getString(R.string.AvoorAppDisplayNameEnglish));
        tvHomeScreenTxtViewTitleMessage.setText(this.getResources().getString(R.string.HomeScreenTitleEnglish));
        tvHomeScreenTxtViewPrdshPrtc.setText("");
        setSupportActionBar(tlbarHomeScreenToolbar);

        if(currentSponsorInfo.getAccessLevel() == SponsorsInfo.ACCESS_LEVEL_ADMIN)
        {
            strListViewMenuItemsNamesList.add(this.getResources().getString(R.string.MenuItemSponsorsList));
        }
        strListViewMenuItemsNamesList.add(this.getResources().getString(R.string.MenuItemPradoshamDates));
        strListViewMenuItemsNamesList.add(this.getResources().getString(R.string.MenuItemPradoshamSponsors));
        strListViewMenuItemsNamesList.add(this.getResources().getString(R.string.MenuItemSankalpamDetails));
        strListViewMenuItemsNamesList.add(this.getResources().getString(R.string.GalleryScreenSubMenuItemVasthramDetails));
        strListViewMenuItemsNamesList.add(this.getResources().getString(R.string.MenuItemPhotosAndVideos));

        lvHomeScreenListViewMenuItems.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strListViewMenuItemsNamesList));
        lvHomeScreenListViewMenuItems.setOnItemClickListener((adapterView, view, position, id) -> {
            String strListItemName = adapterView.getItemAtPosition(position).toString();
            prvSelectScreenFromListOption(strListItemName);
        });

        tempWrapper = new FirebaseWrapper(getApplicationContext());
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

    /* This method has been overridden and implemented to go to the desired screen on clicking a
     * menu item from the ellipsis menu. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
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

    /* This method has been implemented to go to the desired screen on clicking a menu item from
     * the list of menu items. */
    private void prvSelectScreenFromListOption(String strListItemName)
    {
        if (strListItemName.equals(getResources().getString(R.string.MenuItemSponsorsList)))
        {
            openSponsorsScreen();
        }
        else if (strListItemName.equals(getResources().getString(R.string.MenuItemPradoshamDates)))
        {
            openPradoshamDatesScreen();
        }
        else if (strListItemName.equals(getResources().getString(R.string.MenuItemPradoshamSponsors)))
        {
            openPradoshamDatesSponsorsScreen();
        }
        else if (strListItemName.equals(getResources().getString(R.string.MenuItemSankalpamDetails)))
        {
            openSankalpamScreen();
        }
        else if (strListItemName.equals(getResources().getString(R.string.MenuItemPhotosAndVideos)))
        {
            openGalleryScreen();
        }
        else if (strListItemName.equals(getResources().getString(R.string.GalleryScreenSubMenuItemVasthramDetails)))
        {
            prvObtainGalleryInfoFromFirebase();
        }
        else
        {
            Log.d("Error: ", "invalid menu item selected");
        }
    }

    /* This method has been implemented for the Sponsors list menu option from the list view. */
    private void openSponsorsScreen()
    {
        /* Make an intent object to go to the Sponsors list screen. User should be able to get back
         * to the home screen, so do not set any intent flags. */
        Intent intent = new Intent(this.getApplicationContext(), SponsorsScreen.class);
        startActivity(intent);
    }

    /* This method has been implemented for the pradoshams list menu option from the list view. */
    private void openPradoshamDatesScreen()
    {
        /* Make an intent object to go to the pradoshams list screen. User should be able to get
         * back to the home screen, so do not set any intent flags. */
         Intent intent = new Intent(this.getApplicationContext(), PradoshamDatesScreen.class);
         startActivity(intent);
    }

    /* This method has been implemented for the Sponsors list menu option from the list view. */
    private void openPradoshamDatesSponsorsScreen()
    {
        /* Make an intent object to go to the Sponsors dates and pradoshams list screen. User should
         * be able to get back to the home screen, so do not set any intent flags. */
        Intent intent = new Intent(this.getApplicationContext(), PradoshamDatesSponsorsScreen.class);
        startActivity(intent);
    }

    /* This method has been implemented for the Sponsor family info menu option from the list view. */
    private void openSankalpamScreen()
    {
        /* Make an intent object to go to the Sankalpam screen. User should be able to get back
         * to the home screen, so do not set any intent flags. */
        Intent intent;

        if (currentSponsorInfo.getAccessLevel() == SponsorsInfo.ACCESS_LEVEL_ADMIN)
        {
            intent = new Intent(this.getApplicationContext(), SankalpamAdminScreen.class);
        }
        else
        {
            intent = new Intent(this.getApplicationContext(), SankalpamScreen.class);
        }

        intent.putExtra(getResources().getString(R.string.HomeScreenSponsorInfoIntentKey), currentSponsorInfo);
        startActivity(intent);
    }

    /* This method has been implemented for the gallery menu option from the list view. */
    private void openGalleryScreen()
    {
        /* Make an intent object to go to the Sponsors list screen. User should be able to get back
         * to the home screen, so do not set any intent flags. */
        Intent intent = new Intent(this.getApplicationContext(), GalleryScreen.class);
        startActivity(intent);
    }

    /* This method has been implemented for the Logout menu option from the ellipsis menu. */
    private void openLandingScreen()
    {
        /* Make an intent object with the below flags set to prevent going back to the application
         * screens with the back button after opting to log out. */
        SharedPreferences sharedPref = this.getSharedPreferences(this.getResources().getString(R.string.SharedPreferencesLoginStatusFileKey), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(this.getResources().getString(R.string.SharedPreferencesLoginStatusKey), false);
        editor.putString(this.getResources().getString(R.string.SharedPreferencesLoginStatusNumber), "");
        editor.apply();

        Intent intent = new Intent(this.getApplicationContext(), LandingScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Log.d("debugLoginHome", ""+sharedPref.getBoolean(this.getResources().getString(R.string.SharedPreferencesLoginStatusKey), true));
        startActivity(intent);
    }

    /* This method has been implemented for the about menu option from the ellipsis menu. */
    private void openAboutScreen()
    {
        /* Make an intent object to go to the about screen. User should be able to get back to the
         * home screen, so do not set any intent flags. */
        Intent intent = new Intent(this.getApplicationContext(), AboutScreen.class);
        startActivity(intent);
    }

    private void openVasthramDetailsScreen()
    {
        /* Make an intent object to go to the Sponsors list screen. User should be able to get back
         * to the home screen, so do not set any intent flags. Pass the vasthram details string otained
         * fro Firebase. */
        Intent intent = new Intent(this.getApplicationContext(), GalleryVasthramDetailsScreen.class);
        intent.putExtra(this.getResources().getString(R.string.GalleryScreenVasthramDetailsIntentKey),
                        tempWrapper.getGalleryInformation().getVasthramDetails());
        startActivity(intent);
    }

    private void prvObtainGalleryInfoFromFirebase()
    {
        tempWrapper.downloadGalleryInformation(new FirebaseDownloadListener() {
            @Override
            public void onDownloadCompleteCallback() {
                openVasthramDetailsScreen();
            }

            @Override
            public void onDownloadFailureCallback() {
                alertDialog.displayAlertMessage(getApplicationContext().getResources().getString(R.string.GalleryScreenGalleryInfoStatusDownloadFailed));
            }
        });
    }
}
