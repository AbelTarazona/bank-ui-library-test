package com.abeltarazona.password_ui

import android.app.Application
import com.abeltarazona.passwordpolicylibrary.BuildConfig
import timber.log.Timber

/**
 * Created by AbelTarazona on 3/10/2021
 */
class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}