package com.raven.home.data.remote.service

import com.raven.home.data.remote.ServiceFactory
import retrofit2.Retrofit
import javax.inject.Inject

class ApiServiceFactory @Inject constructor(
    private val retrofit: Retrofit
) : ServiceFactory {
    override fun <T> makeConnectionApiService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}