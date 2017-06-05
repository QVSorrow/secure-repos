package com.qvsorrow.securerepos.data.network.di


@dagger.Module
class RetrofitApiModule(private val baseUrl: String) {

    @dagger.Provides
    @javax.inject.Singleton
    fun provideGson() =
            com.google.gson.GsonBuilder()
                .setFieldNamingPolicy(com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

    @dagger.Provides
    @javax.inject.Singleton
    fun provideOkHttp() =
            okhttp3.OkHttpClient.Builder()
                    .build()

    @dagger.Provides
    @javax.inject.Singleton
    fun provideApiManager(gson: com.google.gson.Gson, httpClient: okhttp3.OkHttpClient): com.qvsorrow.securerepos.data.network.ApiManager {
        val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson))
                .addCallAdapterFactory(retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
        return retrofit.create(com.qvsorrow.securerepos.data.network.ApiManager::class.java)
    }
}

