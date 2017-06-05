package com.qvsorrow.securerepos.data.di

import com.qvsorrow.securerepos.domain.security.SecurityManager
import com.qvsorrow.securerepos.domain.security.SecurityManagerImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class SecurityModule {

    @Binds
    @Singleton
    abstract fun providesSecurityManager(securityManagerImpl: SecurityManagerImpl): SecurityManager

}

