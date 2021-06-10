package com.app.namllahuser.app.di

import android.content.Context
import com.app.namllahuser.data.repository.ConfigRepositoryImpl
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.data.sharedvariables.SharedVariables
import com.app.namllahuser.domain.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideSharedVariables(@ApplicationContext context: Context) = SharedVariables(context)

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient {
//        var HttpLoggingInterceptor = HttpLoggingInterceptor()
//        HttpLoggingInterceptor.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.MINUTES)
            .connectTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .addInterceptor(httpLoggingInterceptor).build()
    }


    @Singleton
    @Provides
    fun provideActivationRetrofitInstance(okHttpClient: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient.newBuilder().build())

    @Provides
    fun provideConfigRepository(
        @ApplicationContext context: Context
    ): ConfigRepository = ConfigRepositoryImpl(context)
}