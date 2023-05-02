package com.example.avoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/* This class represents the 'temple pathigam' sub-menu of the gallery screen. */
public class GalleryTemplePathigamScreen extends AppCompatActivity {
    /* Member variables. */
    private int intTextViewColor;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        TextView tvGalleryTemplePathigamScreenTxtViewAppName, tvGalleryTemplePathigamScreenTxtViewTitleMessage, tvGalleryTemplePathigamScreenContentBox;
        Toolbar tlbarGalleryTemplePathigamScreenToolbar;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_temple_pathigam_screen);
        if(this.getResources().getString(R.string.theme).equals("Night"))
        {
            intTextViewColor = this.getResources().getColor(R.color.white);
        }
        else
        {
            intTextViewColor = this.getResources().getColor(R.color.black);
        }

        tvGalleryTemplePathigamScreenTxtViewAppName = findViewById(R.id.GalleryTemplePathigamScreenTxtViewAppName);
        tvGalleryTemplePathigamScreenTxtViewTitleMessage = findViewById(R.id.GalleryTemplePathigamScreenTxtViewTitleMessage);
        tvGalleryTemplePathigamScreenContentBox = findViewById(R.id.GalleryTemplePathigamScreenContentBox);
        tlbarGalleryTemplePathigamScreenToolbar = findViewById(R.id.GalleryTemplePathigamScreenToolbar);

        setSupportActionBar(tlbarGalleryTemplePathigamScreenToolbar);
        tvGalleryTemplePathigamScreenTxtViewAppName.setText(
        getApplicationContext()
        .getResources()
        .getString(R.string.AvoorAppDisplayNameEnglish)
        );
        tvGalleryTemplePathigamScreenTxtViewTitleMessage.setText(
        getApplicationContext()
        .getResources()
        .getString(R.string.GalleryTemplePathigamScreenTitleEnglish)
        );

        Intent intent = getIntent();
        String strTemplePathigam = intent.getStringExtra(this.getResources().getString(R.string.GalleryScreenTemplePathigamIntentKey));
        tvGalleryTemplePathigamScreenContentBox.setTextColor(intTextViewColor);
        tvGalleryTemplePathigamScreenContentBox.setText(strTemplePathigam);
    }
}
