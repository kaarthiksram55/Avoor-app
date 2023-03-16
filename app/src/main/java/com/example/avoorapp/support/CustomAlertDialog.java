package com.example.avoorapp.support;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

/* This is a small wrapper class which displays an alert box with the specified message. */
public class CustomAlertDialog
{
    /* member variables. */
    private AlertDialog adLoginStatusDialog;

    /* Constructor. */
    public CustomAlertDialog(Context context)
    {
        adLoginStatusDialog = new AlertDialog.Builder(context).create();
    }

    /* This function is used to display the intended message in the alert dialog. */
    public void displayAlertMessage(String strMessage)
    {
        adLoginStatusDialog.setMessage(strMessage);
        adLoginStatusDialog.show();
    }
}
