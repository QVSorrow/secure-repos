package com.qvsorrow.securerepos.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.qvsorrow.securerepos.data.database.dao.RepoDao
import com.qvsorrow.securerepos.data.database.entity.EncrRepo

@Database(entities = arrayOf(EncrRepo::class), version = 1)
abstract class RoomDatabaseManager : RoomDatabase(), DatabaseManager {
    override abstract fun repoDao(): RepoDao
}