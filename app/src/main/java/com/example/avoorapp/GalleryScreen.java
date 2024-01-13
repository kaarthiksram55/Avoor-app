package com.example.avoorapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.avoorapp.support.CustomAlertDialog;
import com.example.avoorapp.support.FirebaseDownloadListener;
import com.example.avoorapp.support.FirebaseWrapper;
import com.example.avoorapp.support.GalleryInfo;

import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class GalleryScreen extends AppCompatActivity {
    /* member variables. */
    private ListView lvGalleryScreenListViewMenuItems;
    private GalleryInfo galleryInfo;
    private FirebaseWrapper tempWrapper;
    private CustomAlertDialog alertDialog;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        TextView tvGalleryScreenTxtViewAppName, tvGalleryScreenTxtViewTitleMessage;
        Toolbar tlbarGalleryScreenToolbar;
        ArrayList<String> strGalleryScreenListViewMenuItemsNamesList = new ArrayList<String>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_screen);

        tvGalleryScreenTxtViewAppName = findViewById(R.id.GalleryScreenTxtViewAppName);
        tvGalleryScreenTxtViewTitleMessage = findViewById(R.id.GalleryScreenTxtViewTitleMessage);
        tlbarGalleryScreenToolbar = findViewById(R.id.GalleryScreenToolbar);
        lvGalleryScreenListViewMenuItems = findViewById(R.id.GalleryScreenListViewMenuItems);

        tvGalleryScreenTxtViewAppName.setText(
            getApplicationContext()
            .getResources()
            .getString(R.string.AvoorAppDisplayNameEnglish)
        );
        tvGalleryScreenTxtViewTitleMessage.setText(
            getApplicationContext()
            .getResources()
            .getString(R.string.GalleryScreenTitleEnglish)
        );
        setSupportActionBar(tlbarGalleryScreenToolbar);

        strGalleryScreenListViewMenuItemsNamesList.add(this.getResources().getString(R.string.GalleryScreenSubMenuItemAboutTemple));
        strGalleryScreenListViewMenuItemsNamesList.add(this.getResources().getString(R.string.GalleryScreenSubMenuItemTemplePathigam));
        strGalleryScreenListViewMenuItemsNamesList.add(this.getResources().getString(R.string.GalleryScreenSubMenuItemMedia));
        lvGalleryScreenListViewMenuItems.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strGalleryScreenListViewMenuItemsNamesList));
        lvGalleryScreenListViewMenuItems.setOnItemClickListener((adapterView, view, position, id) -> {
            String strListItemName = adapterView.getItemAtPosition(position).toString();
            prvSelectGalleryScreenFromListOption(strListItemName);
        });

        alertDialog = new CustomAlertDialog(this);
        tempWrapper = new FirebaseWrapper(getApplicationContext());
        prvObtainGalleryInfoFromFirebase();
    }

    /* This function will open the sub menu selected by the user using the list view containing the
     * sub menu items. */
    private void prvSelectGalleryScreenFromListOption(String strSubMenuName)
    {
        if(galleryInfo == null)
        {
            alertDialog.displayAlertMessage(getApplicationContext().getResources().getString(R.string.GalleryScreenGalleryInfoStatusDownloadFailed));
        }
        else
        {
            if (strSubMenuName.equals(this.getResources().getString(R.string.GalleryScreenSubMenuItemAboutTemple)))
            {
                Intent intent = new Intent(this.getApplicationContext(), GalleryAboutTempleScreen.class);
                intent.putExtra(this.getResources().getString(R.string.GalleryScreenAboutTempleIntentKey), galleryInfo.getAboutTemple());
                startActivity(intent);
            }
            else if (strSubMenuName.equals(this.getResources().getString(R.string.GalleryScreenSubMenuItemTemplePathigam)))
            {
                Intent intent = new Intent(this.getApplicationContext(), GalleryTemplePathigamScreen.class);
                intent.putExtra(this.getResources().getString(R.string.GalleryScreenTemplePathigamIntentKey), galleryInfo.getTemplePathigam());
                startActivity(intent);
            }
            else if (strSubMenuName.equals(this.getResources().getString(R.string.GalleryScreenSubMenuItemMedia)))
            {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(galleryInfo.getMediaLink()));
                startActivity(intent);
            }
        }
    }

    /* This function will take care of downloading the gallery related information from firebase and
     * further actions to be taken on successful download. This function will also take care of the
     * case when download fails. */
    private void prvObtainGalleryInfoFromFirebase()
    {
        tempWrapper.downloadGalleryInformation(new FirebaseDownloadListener() {
            @Override
            public void onDownloadCompleteCallback() {
                galleryInfo = tempWrapper.getGalleryInformation();
            }

            @Override
            public void onDownloadFailureCallback() {
                alertDialog.displayAlertMessage(getApplicationContext().getResources().getString(R.string.GalleryScreenGalleryInfoStatusDownloadFailed));
            }
        });
    }
}
