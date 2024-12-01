package com.sashi.sessionanalytics

import android.app.Application
import com.sashi.sessionanalytics.features.SessionAnalytics

/**
 * Created by Sashi Manandhar on 27/11/2024.
 * Senior Android Developer
 * sashimdr8@gmail.com
 **/

class SessionApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionAnalytics.initialize(this)
    }
}
