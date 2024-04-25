package com.raven.home.di

import com.raven.home.data.remote.ApiServiceFactory
import com.raven.home.data.remote.ServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceFactoryModule {

    @Provides
    @Singleton
    fun provideApiServiceFactory(retrofit: Retrofit): ServiceFactory =
        ApiServiceFactory(retrofit)

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class IoDispatcher
}