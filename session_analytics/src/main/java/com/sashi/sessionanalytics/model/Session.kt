package com.sashi.sessionanalytics.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sashi.sessionanalytics.core.DbTables
import com.sashi.sessionanalytics.core.Fields.SESSION_ID

/**
 * Created by Sashi Manandhar on 25/11/2024.
 * Senior Android Developer
 * sashimdr8@gmail.com
 **/

@Entity(
    tableName = DbTables.SESSIONS,
    indices = [Index(value = [SESSION_ID], unique = true)]
)
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: String,
    val startTimestamp: Long = System.currentTimeMillis(),
    val endTimestamp: Long? = null //endTimestamp is null when session is active
)
