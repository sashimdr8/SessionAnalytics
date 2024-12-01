package com.sashi.sessionanalytics.features

import com.google.gson.Gson
import com.sashi.sessionanalytics.data.dao.EventDao
import com.sashi.sessionanalytics.data.SessionAnalyticsDatabase
import com.sashi.sessionanalytics.model.Event

/**
 * Created by Sashi Manandhar on 27/11/2024.
 * Senior Android Developer
 * sashimdr8@gmail.com
 **/

/**
 * EventManager manages [Event]
 * */
object EventManager {

    private lateinit var eventDao: EventDao

    fun initialize(database: SessionAnalyticsDatabase) {
        eventDao = database.eventDao()
    }

    /**
     * Records/inserts event with [properties]
     * **/
    suspend fun recordEvent(name: String, properties: Map<String, Any> = emptyMap()) {
        val activeSession = SessionManager.getActiveSession()
            ?: throw IllegalStateException("No active session. Start a session first.")
        val event = Event(
            sessionId = activeSession.sessionId,
            name = name,
            properties = Gson().toJson(properties)
        )
        eventDao.insertEvent(event)
    }

    /**
     * Returns list of [Event]
     * by [sessionId]
     * */
    suspend fun getEventsForSession(sessionId: String): List<Event> {
        return eventDao.getEventsBySessionId(sessionId)
    }
}
