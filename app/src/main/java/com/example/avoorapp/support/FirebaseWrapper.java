package com.example.avoorapp.support;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.example.avoorapp.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* This class implements wrapper functions to get data from firebase. All firebase related code is
 * expected to be within this class. The final info required out of the firestore database, like
 * sponsor info, pradosham info, etc. is returned by this class. */
public class FirebaseWrapper
{
    /* member variables. */
    private final FirebaseFirestore firestoreDatabase;
    private SponsorsInfo sponsorInfo;
    private GalleryInfo galleryInfo;
    private ArrayList<SponsorsInfo> sponsorsInfoList;
    private ArrayList<PradoshamInfo> pradoshamInfoList;
    private Context appContext;

    /* constructor. */
    public FirebaseWrapper(Context context)
    {
        /* Initialize member variables. */
        firestoreDatabase = FirebaseFirestore.getInstance();
        sponsorInfo = new SponsorsInfo();
        galleryInfo = new GalleryInfo();
        sponsorsInfoList = new ArrayList<SponsorsInfo>();
        pradoshamInfoList = new ArrayList<PradoshamInfo>();
        appContext = context;
    }

    /* This function initiates download of data of single sponsor. */
    public void downloadSingleSponsorInfo(String sponsorMobileNumber, FirebaseDownloadListener downloadListener)
    {
        /* Download all sponsor information asynchronously using the firestore class get() method. */
        sponsorInfo = new SponsorsInfo();
        firestoreDatabase.collection(appContext.getResources().getString(R.string.FirebaseSponsorsInfoCollectionName))
        .document(sponsorMobileNumber)
        .get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                sponsorInfo = task.getResult().toObject(SponsorsInfo.class);

                if(sponsorInfo != null)
                {
                    sponsorInfo.setNumber(sponsorMobileNumber);
                    downloadListener.onDownloadCompleteCallback();
                }
                else
                {
                    downloadListener.onDownloadFailureCallback();
                }
            }
            else
            {
                downloadListener.onDownloadFailureCallback();
            }
        });
    }

    /* This function initiates download of data of all sponsors. */
    public void downloadAllSponsorsInfo(FirebaseDownloadListener downloadListener)
    {
        /* Initiate download of the collection in which info of all sponsors is stored. Once this
         * is downloaded, assign the information of each individual sponsor to the Arraylist object
         * to enable the caller to use the corresponding getter method. */
        sponsorsInfoList = new ArrayList<SponsorsInfo>();
        firestoreDatabase.collection(appContext.getResources().getString(R.string.FirebaseSponsorsInfoCollectionName))
        .get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                List<DocumentSnapshot> documentSnapshotList = task.getResult().getDocuments();

                if(documentSnapshotList.size() > 0)
                {
                    for (int i=0; i<documentSnapshotList.size(); i++)
                    {
                        SponsorsInfo tempInfo = documentSnapshotList.get(i).toObject(SponsorsInfo.class);
                        tempInfo.setNumber(documentSnapshotList.get(i).getId());
                        sponsorsInfoList.add(tempInfo);
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        sponsorsInfoList.sort(new Comparator<SponsorsInfo>() {
                            @Override
                            public int compare(SponsorsInfo s, SponsorsInfo t1) {
                                return s.getName().compareTo(t1.getName());
                            }
                        }); 
                    }

                    downloadListener.onDownloadCompleteCallback();
                }
                else
                {
                    downloadListener.onDownloadFailureCallback();
                }
            }
            else
            {
                Log.w("firebase", "Error getting documents.", task.getException());
                downloadListener.onDownloadFailureCallback();
            }
        });
    }

    /* This function will download all the pradosham details from firebase. */
    public void downloadPradoshamDetails(FirebaseDownloadListener downloadListener)
    {
        /* Initiate download of the collection in which info of all pradoshams is stored. Once this
         * is downloaded, assign it to the ArrayList object for the same to enable the caller to use
         * the corresponding getter method. */
        pradoshamInfoList = new ArrayList<PradoshamInfo>();
        firestoreDatabase.collection(appContext.getResources().getString(R.string.FirebasePradoshamDetailsCollectionName))
        .get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                List<DocumentSnapshot> documentSnapshotList = task.getResult().getDocuments();

                if(documentSnapshotList.size() > 0)
                {
                    for (int i=0; i<documentSnapshotList.size(); i++)
                    {
                        PradoshamInfo tempInfo = documentSnapshotList.get(i).toObject(PradoshamInfo.class);
                        tempInfo.setYearName(documentSnapshotList.get(i).getId());
                        pradoshamInfoList.add(tempInfo);
                    }

                    downloadListener.onDownloadCompleteCallback();
                }
                else
                {
                    downloadListener.onDownloadFailureCallback();
                }
            }
            else
            {
                downloadListener.onDownloadFailureCallback();
            }
        });
    }

    /* This function will download all the gallery related information from firebase. */
    public void downloadGalleryInformation(FirebaseDownloadListener downloadListener)
    {
        /* Initiate download of the collection in which gallery related info is stored. Once this
         * is downloaded, assign it to the GalleryInfo object for the same to enable the caller to
         * use the corresponding getter method. */
        galleryInfo = new GalleryInfo();
        firestoreDatabase.collection(appContext.getResources().getString(R.string.FirebaseGalleryInformationCollectionName))
        .get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                List<DocumentSnapshot> documentSnapshotList = task.getResult().getDocuments();

                if(documentSnapshotList.size() > 0)
                {
                    galleryInfo = documentSnapshotList.get(0).toObject(GalleryInfo.class);
                    downloadListener.onDownloadCompleteCallback();
                }
                else
                {
                    downloadListener.onDownloadFailureCallback();
                }
            }
            else
            {
                downloadListener.onDownloadFailureCallback();
            }
        });
    }

    /* This function returns the sponsor information requested via downloadSingleSponsorInfo()
     * function. */
    public SponsorsInfo getSingleSponsorInfo()
    {
        return sponsorInfo;
    }

    /* This function returns all the sponsor information requested via downloadAllSponsorsInfo()
     * function. */
    public ArrayList<SponsorsInfo> getAllSponsorsInfo()
    {
        return sponsorsInfoList;
    }

    /* This function returns all the pradosham information requested via downloadPradoshamDetails()
     * function. */
    public ArrayList<PradoshamInfo> getPradoshamDetails()
    {
        return pradoshamInfoList;
    }

    /* This function returns all the Gallery information requested via downloadGalleryInformation()
     * function. */
    public GalleryInfo getGalleryInformation()
    {
        return galleryInfo;
    }
}
