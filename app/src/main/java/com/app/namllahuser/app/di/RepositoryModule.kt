package com.app.namllahuser.app.di

import com.app.namllahuser.data.repository.AuthRepositoryImpl
import com.app.namllahuser.data.repository.MainRepositoryImpl
import com.app.namllahuser.domain.repository.AuthRepository
import com.app.namllahuser.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    //@Binds
    @Binds
    abstract fun authRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun mainRepository(mainRepositoryImpl: MainRepositoryImpl):MainRepository
}