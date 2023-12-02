package com.example.avoorapp.support;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.avoorapp.R;

import org.w3c.dom.Text;

/* This class will provide means to make textview objects that can be inserted as table cells. */
public class TableCell {
    /* memebr variables. */
    private Context appContext;
    private TableRow.LayoutParams cellLayoutParams;

    /* constructor. Will take the context of the app to acess resources. */
    public TableCell(Context context)
    {
        appContext = context;
        cellLayoutParams = new TableRow.LayoutParams();
        cellLayoutParams.setMargins(1, 1, 1, 1);
        cellLayoutParams.height = TableRow.LayoutParams.MATCH_PARENT;
    }

    /* This function will return a textView cell with the properties set as desired by the user. */
    public TextView generateCell(String strContentString, int intColor, float floatFontSizeSp, boolean boolCenterAlign)
    {
        TextView cellTextView = new TextView(appContext);

        cellTextView.setText(strContentString);
        cellTextView.setBackgroundColor(intColor);
        cellTextView.setPadding(12, 2, 12, 2);
        cellTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, floatFontSizeSp);
        cellTextView.setLayoutParams(cellLayoutParams);

        if (boolCenterAlign)
        {
            cellTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            cellTextView.setTypeface(null, Typeface.BOLD);
        }

        if (appContext.getResources().getString(R.string.theme).equals("Day"))
        {
            cellTextView.setTextColor(appContext.getResources().getColor(R.color.black));
        }
        else
        {
            cellTextView.setTextColor(appContext.getResources().getColor(R.color.white));
        }

        return cellTextView;
    }

    /* This function will return a textView header with the properties set as desired by the user. */
//    public TextView generateHeader(String strContentString, int intColor, float floatFontSizeSp)
//    {
//        TextView cellTextView = new TextView(appContext);
//
//        cellTextView.setText(strContentString);
//        cellTextView.setBackgroundColor(intColor);
//        cellTextView.setPadding(4, 4, 4, 4);
//        cellTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, floatFontSizeSp);
////        cellTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        cellTextView.setLayoutParams(cellLayoutParams);
//
//        return cellTextView;
//    }
}
