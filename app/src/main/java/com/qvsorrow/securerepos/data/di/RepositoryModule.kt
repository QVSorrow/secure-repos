package com.qvsorrow.securerepos.data.di

import com.qvsorrow.securerepos.data.repository.GithubRepositoryImpl
import com.qvsorrow.securerepos.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun githubRepository(githubRepositoryImpl: GithubRepositoryImpl): GithubRepository

}