package com.sashi.sessionanalytics.data.dao

import androidx.room.*
import com.sashi.sessionanalytics.model.Event

/**
 * Created by Sashi Manandhar on 27/11/2024.
 * Senior Android Developer
 * sashimdr8@gmail.com
 **/

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    @Query("SELECT * FROM events WHERE sessionId = :sessionId")
    suspend fun getEventsBySessionId(sessionId: String): List<Event>

    @Query("DELETE FROM events")
    suspend fun clearAllEvents()

}
