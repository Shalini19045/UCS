package com.iiitd.ucsf.application;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
 import com.iiitd.ucsf.pojo.User;

public class ucsf extends Application {
    private static ucsf instance;
    private static FirebaseFirestore db;
     private FirebaseAnalytics mFirebaseAnalytics;
    private static User user;

    private static String userID;

    synchronized public static ucsf  getInstance() {
        if (instance == null) {
            instance = new ucsf();
        }
        return instance;
    }

    synchronized public FirebaseFirestore getFirebaseDatabaseInstance() {
        if (db == null) {
            db = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)

                    .build();

            db.setFirestoreSettings(settings);
        }
        return db;
    }

    synchronized public User getAppUser(User p) {
        if (p != null) {
            user = p;
            db.collection("users").document(p.getId()).set(p);
        } else if (user == null)
            user = new User();
        return user;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        getFirebaseDatabaseInstance();
        instance = this;
        FirebaseApp.initializeApp(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        FirebaseMessaging.getInstance().subscribeToTopic("events");
    }

    synchronized public FirebaseAnalytics getFirebaseAnalyticsObj(){
        if(mFirebaseAnalytics!=null) {
            return mFirebaseAnalytics;
        }else{
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            return mFirebaseAnalytics;
        }
    }
    public void setUserID(String id) {
        userID = id;
    }

    public void clearUserId(){
        userID = null;
    }

    public String getUserID() {
        return userID;
    }

}
