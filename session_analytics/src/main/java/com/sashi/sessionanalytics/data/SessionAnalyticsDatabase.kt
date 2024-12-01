package com.sashi.sessionanalytics.data


import androidx.room.Database
import androidx.room.RoomDatabase
import com.sashi.sessionanalytics.data.dao.EventDao
import com.sashi.sessionanalytics.data.dao.SessionDao
import com.sashi.sessionanalytics.model.Session
import com.sashi.sessionanalytics.model.Event

/**
 * Created by Sashi Manandhar on 27/11/2024.
 * Senior Android Developer
 * sashimdr8@gmail.com
 **/

@Database(entities = [Session::class, Event::class], version = 1, exportSchema = false)
abstract class SessionAnalyticsDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun eventDao(): EventDao
}