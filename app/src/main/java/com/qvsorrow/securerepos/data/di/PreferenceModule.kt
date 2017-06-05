package com.qvsorrow.securerepos.data.di

import android.app.Application
import com.qvsorrow.securerepos.data.preferences.PreferenceManager
import com.qvsorrow.securerepos.data.preferences.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PreferenceModule {

    @Provides
    @Singleton
    fun provideShredPreferences(application: Application): PreferenceManager {
        return SharedPreferenceManager(
                android.preference.PreferenceManager.getDefaultSharedPreferences(application))
    }

}