package com.egeperk.hiltartbookpro.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.egeperk.hiltartbookpro.model.Art

@Database(entities = [Art::class], version = 1)
abstract class ArtDatabase : RoomDatabase() {
    abstract fun artDao() : ArtDao
}