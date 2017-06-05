package com.qvsorrow.securerepos

import com.qvsorrow.securerepos.data.network.di.RetrofitApiModule
import com.qvsorrow.securerepos.di.AppInjector
import com.qvsorrow.securerepos.di.ApplicationComponent
import com.qvsorrow.securerepos.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class App : DaggerApplication() {

    lateinit var component: ApplicationComponent
        get

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerApplicationComponent.builder()
                .apiModule(RetrofitApiModule(BuildConfig.API_ENDPOINT))
                .create(this)
        AppInjector.init(this)
        this.component = component as ApplicationComponent
        return component
    }
}