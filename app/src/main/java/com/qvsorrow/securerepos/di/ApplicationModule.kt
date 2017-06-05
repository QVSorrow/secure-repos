package com.qvsorrow.securerepos.di

import android.app.Application
import com.qvsorrow.securerepos.App
import com.qvsorrow.securerepos.presentation.di.ViewModelSubcomponent
import com.qvsorrow.securerepos.presentation.view.main.di.MainActivitySubcomponent
import dagger.Binds
import dagger.Module

@Module(subcomponents = arrayOf(
        MainActivitySubcomponent::class,
        ViewModelSubcomponent::class))
abstract class ApplicationModule {

    @Binds
    abstract fun application(app: App): Application

}

