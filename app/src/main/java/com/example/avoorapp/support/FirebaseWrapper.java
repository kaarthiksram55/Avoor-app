package com.example.avoorapp.support;

import android.content.Context;
import android.util.Log;

import com.example.avoorapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class FirebaseWrapper
{
    /* member variables. */
    private final FirebaseFirestore firestoreDatabase;
    private SponsorsInfo sponsorInfo;
    private ArrayList<SponsorsInfo> sponsorsInfoList;
    private int intNrOfSponsorsInfoRequested;
    private int intNrOfSponsorsInfoDownloaded = 0;
    private Context appContext;

    /* constructor. */
    public FirebaseWrapper(Context context)
    {
        /* Initialize member variables. */
        firestoreDatabase = FirebaseFirestore.getInstance();
        sponsorInfo = new SponsorsInfo();
        sponsorsInfoList = new ArrayList<SponsorsInfo>();
        appContext = context;
        Log.d("debugaaa", "aaa");
    }

    /* This function initiates download of data of single sponsor. */
    public void downloadSingleSponsorInfo(String SponsorMobileNumber, FirebaseDownloadListener downloadCompletion)
    {
        /* Set no. of requested downloads to 1 as this function is for a single sponsor. Then
         * initiate download. */
        prvResetSponsorsIndexVariables();
        intNrOfSponsorsInfoRequested = 1;
        prvDownloadSingleSponsorInfo(SponsorMobileNumber, downloadCompletion);
    }

    /* This function initiates download of data of all sponsors. */
    public void downloadAllSponsorsInfo(FirebaseDownloadListener downloadListener)
    {
        /* Initiate download of the collection in which number of all sponsors is stored. Once this
         * is downloaded, initiate download of information of each individual sponsor further using
         * firebase again. */
        prvResetSponsorsIndexVariables();
        firestoreDatabase.collection(appContext.getResources().getString(R.string.SponsorScreenInfoCollectionName))
        .get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                List<DocumentSnapshot> documentSnapshotList = task.getResult().getDocuments();
                if(documentSnapshotList.size() > 0)
                {
                    intNrOfSponsorsInfoRequested = documentSnapshotList.size();

                    for (int i=0; i<documentSnapshotList.size(); i++)
                    {
                        prvDownloadSingleSponsorInfo(documentSnapshotList.get(i).getId(), new FirebaseDownloadListener()
                        {
                            @Override
                            public void onDownloadCompleteCallback()
                            {
                                sponsorsInfoList.add(sponsorInfo);
                                intNrOfSponsorsInfoDownloaded++;

                                if(intNrOfSponsorsInfoDownloaded >= intNrOfSponsorsInfoRequested)
                                {
                                    downloadListener.onDownloadCompleteCallback();
                                }
                            }

                            @Override
                            public void onDownloadFailureCallback()
                            {
                                downloadListener.onDownloadFailureCallback();
                            }
                        });
                    }
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

    /* This function downloads information of a single sponsor info using firebase APIs. */
    private void prvDownloadSingleSponsorInfo(String SponsorMobileNumber, FirebaseDownloadListener downloadListener)
    {
        /* Download all sponsor information asynchronously using the firestore class get() method. */
        firestoreDatabase.collection(SponsorMobileNumber)
        .get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                List<SponsorsInfo> sponsorInfoList = task.getResult().toObjects(SponsorsInfo.class);
                if(sponsorInfoList.size() > 0)
                {
                    sponsorInfo = sponsorInfoList.get(0);
                    sponsorInfo.setStrSponsorNumber(SponsorMobileNumber);
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

    private void prvResetSponsorsIndexVariables()
    {
        intNrOfSponsorsInfoRequested = 0;
        intNrOfSponsorsInfoDownloaded = 0;
    }
}
