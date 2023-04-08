package com.example.avoorapp.support;

import android.content.Context;
import android.util.Log;

import com.example.avoorapp.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirebaseWrapper
{
    /* member variables. */
    private final FirebaseFirestore firestoreDatabase;
    private SponsorsInfo sponsorInfo;
    private ArrayList<SponsorsInfo> sponsorsInfoList;
    private ArrayList<PradoshamInfo> pradoshamInfoList;
    private Context appContext;

    /* constructor. */
    public FirebaseWrapper(Context context)
    {
        /* Initialize member variables. */
        firestoreDatabase = FirebaseFirestore.getInstance();
        sponsorInfo = new SponsorsInfo();
        sponsorsInfoList = new ArrayList<SponsorsInfo>();
        pradoshamInfoList = new ArrayList<PradoshamInfo>();
        appContext = context;
        Log.d("debugaaa", "aaa");
    }

    /* This function initiates download of data of single sponsor. */
    public void downloadSingleSponsorInfo(String sponsorMobileNumber, FirebaseDownloadListener downloadListener)
    {
        /* Download all sponsor information asynchronously using the firestore class get() method. */
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
                    for (int i=0; i<sponsorInfo.getFamilyDetails().size(); i++)
                    {
                        Log.d("debugFirebase", sponsorInfo.getFamilyDetails().get(i).toString());
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

    /* This function initiates download of data of all sponsors. */
    public void downloadAllSponsorsInfo(FirebaseDownloadListener downloadListener)
    {
        /* Initiate download of the collection in which info of all sponsors is stored. Once this
         * is downloaded, assign the information of each individual sponsor to the Arraylist object
         * to enable the caller to use the corresponding getter method. */
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

    public ArrayList<PradoshamInfo> getPradoshamDetails()
    {
        return pradoshamInfoList;
    }
}
