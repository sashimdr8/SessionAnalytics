package com.sashi.sessionanalytics.features

import com.sashi.sessionanalytics.data.SessionAnalyticsDatabase
import com.sashi.sessionanalytics.data.dao.SessionDao
import com.sashi.sessionanalytics.model.Session
import java.util.UUID

/**
 * Created by Sashi Manandhar on 27/11/2024.
 * Senior Android Developer
 * sashimdr8@gmail.com
 **/

/**
 * SessionManager manages the active session.
 * **/
object SessionManager {

    private lateinit var sessionDao: SessionDao

    fun initialize(database: SessionAnalyticsDatabase) {
        sessionDao = database.sessionDao()
    }

    suspend fun startSession(): String {
        val existingSession = sessionDao.getActiveSession()
        if (existingSession != null) {
            throw IllegalStateException("An active session already exists.")
        }
        val sessionId = generateSessionId()
        sessionDao.insertSession(Session(sessionId = sessionId))
        return sessionId
    }

    suspend fun endSession() {
        val activeSession = sessionDao.getActiveSession()
            ?: throw IllegalStateException("No active session to end.")
        sessionDao.endSession(activeSession.sessionId, System.currentTimeMillis())
    }

    suspend fun getActiveSession(): Session? = sessionDao.getActiveSession()

    private fun generateSessionId(): String = UUID.randomUUID().toString()
}
