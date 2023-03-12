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
    private boolean boolAdminRights;
    private boolean boolAdminRightsSetOnceStatus = false;

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

    public void setStrSponsorAdminRights(Boolean boolAdminRights)
    {
        this.boolAdminRights = (!this.boolAdminRightsSetOnceStatus) ? boolAdminRights : this.boolAdminRights;
        this.boolAdminRightsSetOnceStatus = true;
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

    public Boolean getStrSponsorAdminRights()
    {
        return boolAdminRights;
    }
}