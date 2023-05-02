package com.example.avoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/* This class represents the screen which displays the vasthram information in the corresponding
 * gallery sub-menu. */
public class GalleryVasthramDetailsScreen extends AppCompatActivity {
    /* Member variables. */
    private int intTextViewColor;

    /* This method is called in the background. Set the screen (xml layout) this class is supposed
     * to display and initialize class variables and screen items as desired. */
    public void onCreate(Bundle savedInstanceState)
    {
        TextView tvGalleryVasthramDetailsScreenTxtViewAppName, tvGalleryVasthramDetailsScreenTxtViewTitleMessage, tvGalleryVasthramDetailsScreenContentBox;
        Toolbar tlbarGalleryVasthramDetailsScreenToolbar;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_vasthram_details_screen);
        if(this.getResources().getString(R.string.theme).equals("Night"))
        {
            intTextViewColor = this.getResources().getColor(R.color.white);
        }
        else
        {
            intTextViewColor = this.getResources().getColor(R.color.black);
        }

        tvGalleryVasthramDetailsScreenTxtViewAppName = findViewById(R.id.GalleryVasthramDetailsScreenTxtViewAppName);
        tvGalleryVasthramDetailsScreenTxtViewTitleMessage = findViewById(R.id.GalleryVasthramDetailsScreenTxtViewTitleMessage);
        tvGalleryVasthramDetailsScreenContentBox = findViewById(R.id.GalleryVasthramDetailsScreenContentBox);
        tlbarGalleryVasthramDetailsScreenToolbar = findViewById(R.id.GalleryVasthramDetailsScreenToolbar);

        setSupportActionBar(tlbarGalleryVasthramDetailsScreenToolbar);
        tvGalleryVasthramDetailsScreenTxtViewAppName.setText(
        getApplicationContext()
        .getResources()
        .getString(R.string.AvoorAppDisplayNameEnglish)
        );
        tvGalleryVasthramDetailsScreenTxtViewTitleMessage.setText(
        getApplicationContext()
        .getResources()
        .getString(R.string.GalleryVasthramDetailsScreenTitleEnglish)
        );

        Intent intent = getIntent();
        String strVasthramDetailsDescription = intent.getStringExtra(this.getResources().getString(R.string.GalleryScreenVasthramDetailsIntentKey));
        tvGalleryVasthramDetailsScreenContentBox.setTextColor(intTextViewColor);
        tvGalleryVasthramDetailsScreenContentBox.setText(strVasthramDetailsDescription);
    }
}
