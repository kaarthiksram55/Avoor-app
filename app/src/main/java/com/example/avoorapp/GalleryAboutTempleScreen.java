package com.example.avoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/* This class represents the 'about the temple' sub-menu of the gallery screen. */
public class GalleryAboutTempleScreen extends AppCompatActivity {
    /* Member variables. */
    private int intTextViewColor;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        TextView tvGalleryAboutTempleScreenTxtViewAppName, tvGalleryAboutTempleScreenTxtViewTitleMessage, tvGalleryAboutTempleScreenContentBox;
        Toolbar tlbarGalleryAboutTempleScreenToolbar;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_about_temple_screen);
        if(this.getResources().getString(R.string.theme).equals("Night"))
        {
            intTextViewColor = this.getResources().getColor(R.color.white);
        }
        else
        {
            intTextViewColor = this.getResources().getColor(R.color.black);
        }

        tvGalleryAboutTempleScreenTxtViewAppName = findViewById(R.id.GalleryAboutTempleScreenTxtViewAppName);
        tvGalleryAboutTempleScreenTxtViewTitleMessage = findViewById(R.id.GalleryAboutTempleScreenTxtViewTitleMessage);
        tvGalleryAboutTempleScreenContentBox = findViewById(R.id.GalleryAboutTempleScreenContentBox);
        tlbarGalleryAboutTempleScreenToolbar = findViewById(R.id.GalleryAboutTempleScreenToolbar);

        setSupportActionBar(tlbarGalleryAboutTempleScreenToolbar);
        tvGalleryAboutTempleScreenTxtViewAppName.setText(
        getApplicationContext()
        .getResources()
        .getString(R.string.AvoorAppDisplayNameEnglish)
        );
        tvGalleryAboutTempleScreenTxtViewTitleMessage.setText(
        getApplicationContext()
        .getResources()
        .getString(R.string.GalleryAboutTempleScreenTitleEnglish)
        );

        Intent intent = getIntent();
        String strAboutTempleDescription = intent.getStringExtra(this.getResources().getString(R.string.GalleryScreenAboutTempleIntentKey));
        tvGalleryAboutTempleScreenContentBox.setTextColor(intTextViewColor);
        tvGalleryAboutTempleScreenContentBox.setText(strAboutTempleDescription);
    }
}
