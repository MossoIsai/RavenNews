package com.raven.home.data.source.remote

interface ServiceFactory {
    fun <T> makeConnectionApiService(serviceClass: Class<T>): T
}