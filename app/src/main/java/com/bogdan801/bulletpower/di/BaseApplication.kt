package com.bogdan801.bulletpower.di

import android.app.Application
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Firebase.database("https://bulletpower-27c60-default-rtdb.europe-west1.firebasedatabase.app/").setPersistenceEnabled(true)
    }
}