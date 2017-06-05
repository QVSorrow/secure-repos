package com.qvsorrow.securerepos.presentation.view.repos_list.di

import android.support.v4.app.Fragment
import com.qvsorrow.securerepos.presentation.view.repos_list.RepoListFragment
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Subcomponent
interface RepoListFragmentSubcomponent : AndroidInjector<RepoListFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<RepoListFragment>()
}

@Module(subcomponents = arrayOf(RepoListFragmentSubcomponent::class))
abstract class RepoListFragmentModule {


    @Binds
    @IntoMap
    @FragmentKey(RepoListFragment::class)
    abstract fun bindRepoListFragmentInjectorFactory(
            builder: RepoListFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>
}