package com.example.avoorapp.support;

/* This class stored the information regarding the gallery menu obtained from firebase. */
public class GalleryInfo {
    /* member variables will be made private. Getter functions will be provided to read these
     * variables. This is done to ensure the elements of this class can be written only once in the
     * beginning by firebase and is not modified later by mistake. */
    private String strAboutTemple;
    private String strMediaLink;
    private String strTemplePathigam;
    private String strVasthramDetails;

    /* public setter methods that enable an one-time write to member variables. */
    public void setAboutTemple(String strAboutTemple)
    {
    	this.strAboutTemple = (this.strAboutTemple == null) ? strAboutTemple : this.strAboutTemple;
        this.strAboutTemple = this.strAboutTemple.replace("\\n", "\n");
    }

    public void setMediaLink(String strMediaLink)
    {
    	this.strMediaLink = (this.strMediaLink == null) ? strMediaLink : this.strMediaLink;
    }

    public void setTemplePathigam(String strTemplePathigam)
    {
    	this.strTemplePathigam = (this.strTemplePathigam == null) ? strTemplePathigam : this.strTemplePathigam;
        this.strTemplePathigam = this.strTemplePathigam.replace("\\n", "\n");
    }

    public void setVasthramDetails(String strVasthramDetails)
    {
        this.strVasthramDetails = (this.strVasthramDetails == null) ? strVasthramDetails : this.strVasthramDetails;
        this.strVasthramDetails = this.strVasthramDetails.replace("\\n", "\n");
    }

    /* public getter methods that allow outside access of data present in the member variables. */
    public String getAboutTemple()
    {
    	return strAboutTemple;
    }

    public String getMediaLink()
    {
    	return strMediaLink;
    }

    public String getTemplePathigam()
    {
    	return strTemplePathigam;
    }

    public String getVasthramDetails()
    {
        return strVasthramDetails;
    }

}
