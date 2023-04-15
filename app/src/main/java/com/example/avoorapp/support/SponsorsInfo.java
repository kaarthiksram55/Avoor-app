package com.example.avoorapp.support;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/* Create a class whose objects act like a struct to hold information about sponsors. Make it
 * implement serializable to enable passing to other activities via Intent class objects. */
public class SponsorsInfo implements Serializable {
    /* Member variables. */
    /* Make members private and implement getter/setter methods for firebase to be able to access
     * and update. Make members private and setters as one time usable so that sponsor info is not
     * modified elsewhere by anyone else. */
    private String name;
    private String number;
    private String location;
    private String password;
    private int accessLevel;
    private List<Map<String, String>> familyMembersDetailsList;
    private Map<String, String> sponsoredPradoshamsList;

    public static final int ACCESS_LEVEL_NONE = 0;
    public static final int ACCESS_LEVEL_BASE = 1;
    public static final int ACCESS_LEVEL_ADMIN = 2;

    /* Make public methods for firebase to access the pirvate members and write their values. Write
     * them such that the object info can be updated only once and never again. */
    public void setName(String name)
    {
        this.name = (this.name == null) ? name : this.name;
    }
    
    public void setNumber(String number)
    {
        this.number = (this.number == null) ? number : this.number;
    }

    public void setLocation(String location)
    {
        this.location = (this.location == null) ? location : this.location;
    }

    public void setPassword(String password)
    {
        this.password = (this.password == null) ? password : this.password;
    }

    public void setAccessLevel(int accessLevel)
    {
        this.accessLevel = (this.accessLevel == this.ACCESS_LEVEL_NONE) ? accessLevel : this.accessLevel;
    }

    public void setFamilyDetails(List<Map<String, String>> familyMembersDetailsList)
    {
        this.familyMembersDetailsList = (this.familyMembersDetailsList == null) ? familyMembersDetailsList : this.familyMembersDetailsList;
    }

    public void setSponsoredPradoshams(Map<String, String> sponsoredPradoshamsList)
    {
        this.sponsoredPradoshamsList = (this.sponsoredPradoshamsList == null) ? sponsoredPradoshamsList : this.sponsoredPradoshamsList;
    }

    /* define public getter methods as all class variables are private. */
    public String getName()
    {
        return name;
    }

    public String getNumber()
    {
        return number;
    }

    public String getLocation()
    {
        return location;
    }

    public String getPassword()
    {
        return password;
    }

    public int getAccessLevel()
    {
        return accessLevel;
    }

    public List<Map<String, String>> getFamilyDetails()
    {
        return familyMembersDetailsList;
    }

    public Map<String, String> getSponsoredPradoshams()
    {
        return sponsoredPradoshamsList;
    }

}