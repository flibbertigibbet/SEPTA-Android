/**
 * Created by Trey Robinson on 7/10/14.
 *
 * Copyright (c) 2014 SEPTA.  All rights reserved.
 */

package org.septa.android.app;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.firebase.client.Firebase;
import com.firebase.simplelogin.FirebaseSimpleLoginError;
import com.firebase.simplelogin.FirebaseSimpleLoginUser;
import com.firebase.simplelogin.SimpleLogin;
import com.firebase.simplelogin.SimpleLoginAuthenticatedHandler;

import org.septa.android.app.managers.AlertManager;
import org.septa.android.app.managers.SharedPreferencesManager;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainApplication extends Application{

    private static final String TAG = MainApplication.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        // Disable Crashlytics for debug builds (unsigned)
        if(!BuildConfig.DEBUG) {
        Crashlytics.start(this);
            Log.i(TAG, "Starting Crashlytics");
        } else {
            Log.i(TAG, "Crashlytics disabled for DEBUG builds");
        }
        /* Trigger Firebase log here */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        Firebase myRef = new Firebase("https://septauser.firebaseio.com");
        SimpleLogin authClient = new SimpleLogin(myRef, getApplicationContext());
        authClient.loginAnonymously(new SimpleLoginAuthenticatedHandler() {
            @Override
            public void authenticated(FirebaseSimpleLoginError error, FirebaseSimpleLoginUser user) {
                if (error != null) {
                    // Oh no! There was an error performing the check
                } else if (user == null) {
                    // No user is logged in
                } else {
                    // There is a logged in user
                    Log.i(TAG, "Starting Crashlytics");
                    String userid = user.getUserId();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
                    System.out.println("User "+userid);
                    Firebase locRef = new Firebase("https://septauser.firebaseio.com/"+userid+"/tracks/"+
                            sdf.format(new Date(System.currentTimeMillis())));
                    Calendar calendar = Calendar.getInstance();


                    java.util.Date now = calendar.getTime();
                    java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

                    Map<String, Object> toSet = new HashMap<String, Object>();
                    toSet.put("uid",userid );
                    toSet.put("setAt", currentTimestamp.toString());
                    Firebase newPushRef = locRef.push();
                    newPushRef.setValue(toSet);

                }
            }
        });
        SharedPreferencesManager.getInstance().init(this);

        // If we're using a developer build on a phone > 2.3 then enforce StrictMode
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(buildPolicy());
            Log.w(TAG, "Strict Mode Enforced.");
        }

        //@TODO putting timing reqs around this and load on demand
        AlertManager.getInstance().fetchAlerts();
    }

    private StrictMode.ThreadPolicy buildPolicy() {
        return new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
    }
}
