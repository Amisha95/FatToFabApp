package com.amisha.fattofabapp;


import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

public class MyApplication extends Application {
    public Tracker mTracker;
    public void startTracking(){
        if(mTracker==null) {
            GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
            mTracker = googleAnalytics.newTracker(R.xml.track_app);
            googleAnalytics.enableAutoActivityReports(this);
            googleAnalytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        }
    }

    public Tracker getTracker() {
        startTracking();
        return mTracker;
    }
}
