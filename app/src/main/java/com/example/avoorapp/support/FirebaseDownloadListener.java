package com.example.avoorapp.support;

/* Implement an interface to store data downloaded from firebase in async task. */
public interface FirebaseDownloadListener {
    /* This function can be implemented as desired to retrieve data from async task of the firestore
     * class used in the FirebaseWrapper custom class. */
    void onDownloadCompleteCallback();

    /* This function can be implemented as desired to take actions in case asynchronous download of
     * data from firestore failed.  */
    void onDownloadFailureCallback();
}
