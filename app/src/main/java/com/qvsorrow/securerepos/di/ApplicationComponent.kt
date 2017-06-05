package com.qvsorrow.securerepos.di

import com.qvsorrow.securerepos.App
import com.qvsorrow.securerepos.data.di.*
import com.qvsorrow.securerepos.data.network.di.MockApiModule
import com.qvsorrow.securerepos.data.network.di.RetrofitApiModule
import com.qvsorrow.securerepos.presentation.di.BuilderModule
import com.qvsorrow.securerepos.presentation.di.ViewModelSubcomponent
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        RetrofitApiModule::class,
        RoomDatabaseModule::class,
        PreferenceModule::class,
        RepositoryModule::class,
        SecurityModule::class,
        BuilderModule::class
))
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>(){
        abstract fun apiModule(retrofitApiModule: RetrofitApiModule): Builder
    }

    fun viewModelSubcomponent(): ViewModelSubcomponent.Builder

}
