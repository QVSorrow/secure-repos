package com.qvsorrow.securerepos.presentation.di

import dagger.Module
import android.app.Activity
import com.qvsorrow.securerepos.presentation.view.main.MainActivity
import com.qvsorrow.securerepos.presentation.view.main.di.MainActivitySubcomponent
import dagger.android.AndroidInjector
import dagger.android.ActivityKey
import dagger.multibindings.IntoMap
import dagger.Binds


@Module
abstract class BuilderModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(builder: MainActivitySubcomponent.Builder): AndroidInjector.Factory<out Activity>

}