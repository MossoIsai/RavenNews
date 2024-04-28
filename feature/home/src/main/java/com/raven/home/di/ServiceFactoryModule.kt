package com.raven.home.di

import android.content.Context
import com.raven.home.data.ConnectivityObserver
import com.raven.home.data.ConnectivityObserverImp
import com.raven.home.data.source.remote.ServiceFactory
import com.raven.home.data.source.remote.service.ApiServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver {
        return ConnectivityObserverImp(context)
    }
}