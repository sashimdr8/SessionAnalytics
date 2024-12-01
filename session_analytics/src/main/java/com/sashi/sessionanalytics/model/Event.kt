package com.sashi.sessionanalytics.model


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.sashi.sessionanalytics.core.DbTables
import com.sashi.sessionanalytics.core.Fields.SESSION_ID

/**
 * Created by Sashi Manandhar on 25/11/2024.
 * Senior Android Developer
 * sashimdr8@gmail.com
 **/

@Entity(
    tableName = DbTables.EVENTS,
    foreignKeys = [
        ForeignKey(
            entity = Session::class,
            parentColumns = [SESSION_ID],
            childColumns = [SESSION_ID],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: String,
    val name: String,
    val properties: String,
    val timestamp: Long = System.currentTimeMillis()
)


