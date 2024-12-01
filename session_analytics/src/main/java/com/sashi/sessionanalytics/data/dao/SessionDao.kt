package com.sashi.sessionanalytics.data.dao


import androidx.room.*
import com.sashi.sessionanalytics.model.Session

/**
 * Created by Sashi Manandhar on 27/11/2024.
 * Senior Android Developer
 * sashimdr8@gmail.com
 **/

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: Session)

    @Query("UPDATE sessions SET endTimestamp = :endTimestamp WHERE sessionId = :sessionId")
    suspend fun endSession(sessionId: String, endTimestamp: Long)

    @Query("SELECT * FROM sessions WHERE endTimestamp IS NULL LIMIT 1")
    suspend fun getActiveSession(): Session?

    @Query("DELETE FROM sessions")
    suspend fun clearAllSessions()
}
