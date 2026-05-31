package com.pantrybox

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PantryBoxApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize anything you need here
    }
}