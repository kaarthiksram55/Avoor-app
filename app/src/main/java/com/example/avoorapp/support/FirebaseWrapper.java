package com.example.avoorapp.support;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class FirebaseWrapper {
    /* member variables. */
    SponsorsInfo[] sponsorsInfoArr;
    int intNrOfSponsors;
    int intSponsorIndex = 0;

    /* constructor. */
    public FirebaseWrapper()
    {
        intNrOfSponsors = 2;
        sponsorsInfoArr = new SponsorsInfo[intNrOfSponsors];

        FirebaseFirestore firestoreDatabase = FirebaseFirestore.getInstance();
        firestoreDatabase.collection("Sponsors")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
            {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task)
                {
                    if (task.isSuccessful())
                    {
                        for (QueryDocumentSnapshot document : task.getResult())
                        {
                            sponsorsInfoArr[intSponsorIndex] = document.toObject(SponsorsInfo.class);
                            Log.d("debug", sponsorsInfoArr[intSponsorIndex].strSponsorName);
                            intSponsorIndex++;
                        }
                    }
                    else
                    {
                        Log.w("firebase", "Error getting documents.", task.getException());
                    }
                }
            });
    }

    public void showSponsorsInfo()
    {
        Log.d("debug", "sponsors info: " + intSponsorIndex);
        for (int i=0; i<intSponsorIndex; i++)
        {
            Log.d("debug", "Name: " + sponsorsInfoArr[i].strSponsorName);
            Log.d("debug", "Number: " + sponsorsInfoArr[i].strSponsorNumber);
            Log.d("debug", "Password: " + sponsorsInfoArr[i].strSponsorLocation);
        }
    }
}
