package com.qvsorrow.securerepos.presentation.di

import com.qvsorrow.securerepos.presentation.view.main.MainViewModel
import com.qvsorrow.securerepos.presentation.view.repos_list.RepoListViewModel
import dagger.Subcomponent

@Subcomponent
interface ViewModelSubcomponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubcomponent
    }
    fun inject(repoListViewModel: RepoListViewModel)
    fun inject(repoListViewModel: MainViewModel)
}