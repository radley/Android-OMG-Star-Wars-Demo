package dev.radley.omgstarwars

import android.app.Application

import timber.log.Timber

class ApplicationController : Application() {

    /**
     * Add Timber support for logging
     */
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
