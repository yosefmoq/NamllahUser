package com.app.namllahuser.app.di

import com.app.namllahuser.data.auth.AuthApi
import com.app.namllahuser.data.auth.AuthApiImpl
import com.app.namllahuser.domain.repository.ConfigRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    //@Provides
    @Provides
    fun provideAuthApi(retrofit: Retrofit.Builder): AuthApi =
        retrofit.build().create(AuthApi::class.java)

    @Provides
    fun provideAuthApiImpl(authApi: AuthApi, configRepository: ConfigRepository) =
        AuthApiImpl(authApi, configRepository)
}