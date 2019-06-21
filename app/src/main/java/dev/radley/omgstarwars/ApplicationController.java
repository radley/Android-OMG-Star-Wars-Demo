package dev.radley.omgstarwars;

import android.app.Application;

import timber.log.Timber;

public class ApplicationController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }
}
