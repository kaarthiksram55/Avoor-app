package com.example.avoorapp.support;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    /* constructor. */
    public FirebaseWrapper()
    {
        /* Initialize member variables. */
        firestoreDatabase = FirebaseFirestore.getInstance();
        sponsorInfo = new SponsorsInfo(); // ArrayList<>();
        Log.d("debugaaa", "aaa");
    }

    /* This function initiates download of sponsors data from firebase. */
    public void downloadSingleSponsorInfo(String SponsorMobileNumber, FirebaseDownloadStatus downloadCompletion)
    {
        /* Download all sponsor information asynchronously using the firestore class get() method. */
        firestoreDatabase.collection(SponsorMobileNumber)
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    List<SponsorsInfo> sponsorInfoList = task.getResult().toObjects(SponsorsInfo.class);
                    if(sponsorInfoList.size() > 0)
                    {
                        sponsorInfo = sponsorInfoList.get(0);
                        downloadCompletion.onDownloadCompleteCallback();
                    }
                    else
                    {
                        downloadCompletion.onDownloadFailureCallback();
                    }

                }
                else
                {
                    Log.w("firebase", "Error getting documents.", task.getException());
                    downloadCompletion.onDownloadFailureCallback();
                }
            }
        });
    }

    public SponsorsInfo getSingleSponsorInfo()
    {
        return sponsorInfo;
    }
}
