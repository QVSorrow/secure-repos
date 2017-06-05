package com.qvsorrow.securerepos.data.di

import android.app.Application
import android.arch.persistence.room.Room
import com.qvsorrow.securerepos.data.database.DatabaseManager
import com.qvsorrow.securerepos.data.database.RoomDatabaseManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): DatabaseManager =
            Room.databaseBuilder(application.applicationContext,
                    RoomDatabaseManager::class.java, "room-database")
                    .build()

}

