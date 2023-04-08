package com.example.avoorapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class GalleryScreen extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        TextView tvGalleryScreenTxtViewAppName, tvGalleryScreenTxtViewTitleMessage;
        Toolbar tlbarGalleryScreenToolbar;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_screen);

        tvGalleryScreenTxtViewAppName = findViewById(R.id.GalleryScreenTxtViewAppName);
        tvGalleryScreenTxtViewTitleMessage = findViewById(R.id.GalleryScreenTxtViewTitleMessage);
        tlbarGalleryScreenToolbar = findViewById(R.id.GalleryScreenToolbar);

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
    }
}
