package com.qazlearn.a1.qazlearn;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by 1 on 03.02.2017.
 */

public class FirebaseOfflineCapabilities extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
