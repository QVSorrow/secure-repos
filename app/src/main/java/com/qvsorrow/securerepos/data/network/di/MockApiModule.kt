package com.qvsorrow.securerepos.data.network.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.qvsorrow.securerepos.data.network.ApiManager
import com.qvsorrow.securerepos.data.network.MockApiManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockApiModule {

    @Singleton
    @Provides
    fun apiManager(): ApiManager = MockApiManager()

    @Provides
    @Singleton
    fun provideGson() =
            GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()
}