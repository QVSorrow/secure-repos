package com.qvsorrow.securerepos.presentation.view.main.di

import com.qvsorrow.securerepos.presentation.view.main.MainActivity
import com.qvsorrow.securerepos.presentation.view.repos_list.di.RepoListFragmentModule


@dagger.Subcomponent(modules = arrayOf(RepoListFragmentModule::class))
interface MainActivitySubcomponent : dagger.android.AndroidInjector<MainActivity> {

    @dagger.Subcomponent.Builder
    abstract class Builder : dagger.android.AndroidInjector.Builder<MainActivity>()


}