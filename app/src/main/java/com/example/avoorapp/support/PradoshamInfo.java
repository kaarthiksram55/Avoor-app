package com.example.avoorapp.support;

import java.util.List;
import java.util.Map;

/* Create a class whose objects act like a struct to hold information about pradoshams. */
public class PradoshamInfo {
    /* Member variables. */
    /* Make member variables private to enable only one time write. */
    private String strYearName;
    private List<Map<String, String>> mapPradoshamList;

    /* Make support member variables for Class operations. */
    public static final int DAY_OF_WEEK_MONDAY = 1;
    public static final int DAY_OF_WEEK_TUESDAY = 2;
    public static final int DAY_OF_WEEK_WEDNESDAY = 3;
    public static final int DAY_OF_WEEK_THURSDAY = 4;
    public static final int DAY_OF_WEEK_FRIDAY = 5;
    public static final int DAY_OF_WEEK_SATURDAY = 6;
    public static final int DAY_OF_WEEK_SUNDAY = 7;
    private static final String[] strDaysNameList = {"", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    /* Make public getter and setter methods for firebase to access and set the values in the
     * private member variables. Make the setter methods such that they allow only one time write
     * to the member variables. */
    public void setYearName(String strYearName)
    {
    	this.strYearName = (this.strYearName == null) ? strYearName : this.strYearName; 
    }

    public void setPradoshamDetails(List<Map<String, String>> mapPradoshamList)
    {
    	this.mapPradoshamList = (this.mapPradoshamList == null) ? mapPradoshamList : this.mapPradoshamList; 
    }    

    public String getYearName()
    {
    	return strYearName;
    }

    public List<Map<String, String>> getPradoshamDetails()
    {
    	return mapPradoshamList;
    }

    /* This function returns the Day name for the desired Day of the week. The day of the week can
     * be one of the constant values provided in thsi class. */
    public static String getDayName(int intDayOfWeek)
    {
        return strDaysNameList[intDayOfWeek];
    }

}
