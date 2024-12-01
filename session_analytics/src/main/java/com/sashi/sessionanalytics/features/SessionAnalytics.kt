package com.sashi.sessionanalytics.features

import android.content.Context
import androidx.room.Room
import com.sashi.sessionanalytics.core.Contracts.DATABASE_NAME
import com.sashi.sessionanalytics.data.SessionAnalyticsDatabase
import com.sashi.sessionanalytics.model.Event

/**
 * Created by Sashi Manandhar on 27/11/2024.
 * Senior Android Developer
 * sashimdr8@gmail.com
 **/

/**
 * Session Analytics is the main entry point to the library.
 * initialize the library by calling SessionAnalytics.initialize(context)
 * start session by calling SessionAnalytics.startSession()
 * record events after starting session by calling SessionAnalytics.recordEvents()
 * stop session by calling SessionAnalytics.endSession()
 * **/
object SessionAnalytics {

    private lateinit var database: SessionAnalyticsDatabase

    /**
     * Initializes the library
     */
    fun initialize(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            SessionAnalyticsDatabase::class.java,
            DATABASE_NAME
        ).build()
        SessionManager.initialize(database)
        EventManager.initialize(database)
    }

    /**
     * Starts a new session
     */
    suspend fun startSession(): String {
        return SessionManager.startSession()
    }

    /**
     * Ends the session
     */
    suspend fun endSession() {
        SessionManager.endSession()
    }

    /**
     * Records event in the active session.
     * */
    suspend fun recordEvent(name: String, properties: Map<String, Any> = emptyMap()) {
        EventManager.recordEvent(name, properties)
    }

    /***
     * Returns list of [Event]
     * by [sessionId]
     * */
    suspend fun getEventsForSession(sessionId: String): List<Event> {
        return EventManager.getEventsForSession(sessionId)
    }
}
