package com.example.avoorapp.support;

import java.io.Serializable;

/* Create a class whose objects act like a struct to hold information about sponsors. Make it
 * implement serializable to enable passing to other activities via Intent class objects. */
public class SponsorsInfo implements Serializable {
    /* Member variables. */
    /* Make members private and implement getter/setter methods for firebase to be able to access
     * and update. Make members private and setters as one time usable so that sponsor info is not
     * modified elsewhere by anyone else. */
    private String strSponsorName;
    private String strSponsorNumber;
    private String strSponsorLocation;
    private String strSponsorPassword;
    private int intAccessLevel;

    public static final int ACCESS_LEVEL_NONE = 0;
    public static final int ACCESS_LEVEL_BASE = 1;
    public static final int ACCESS_LEVEL_ADMIN = 2;

    /* Make public methods for firebase to access the pirvate members and write their values. Write
     * them such that the object info can be updated only once and never again. */
    public void setStrSponsorName(String strSponsorName)
    {
        this.strSponsorName = (this.strSponsorName == null) ? strSponsorName : this.strSponsorName;
    }

    public void setStrSponsorNumber(String strSponsorNumber)
    {
        this.strSponsorNumber = (this.strSponsorNumber == null) ? strSponsorNumber : this.strSponsorNumber;
    }

    public void setStrSponsorLocation(String strSponsorLocation)
    {
        this.strSponsorLocation = (this.strSponsorLocation == null) ? strSponsorLocation : this.strSponsorLocation;
    }

    public void setStrSponsorPassword(String strSponsorPassword)
    {
        this.strSponsorPassword = (this.strSponsorPassword == null) ? strSponsorPassword : this.strSponsorPassword;
    }

    public void setIntAccessLevel(int intAccessLevel)
    {
        this.intAccessLevel = (this.intAccessLevel == this.ACCESS_LEVEL_NONE) ? intAccessLevel : this.intAccessLevel;
    }

    public String getStrSponsorName()
    {
        return strSponsorName;
    }

    public String getStrSponsorNumber()
    {
        return strSponsorNumber;
    }

    public String getStrSponsorLocation()
    {
        return strSponsorLocation;
    }

    public String getStrSponsorPassword()
    {
        return strSponsorPassword;
    }

    public int getIntAccessLevel()
    {
        return intAccessLevel;
    }
}