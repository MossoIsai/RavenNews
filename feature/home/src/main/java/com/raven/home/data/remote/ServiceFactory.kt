package com.raven.home.data.remote

interface ServiceFactory {
    fun <T> makeConnectionApiService(serviceClass: Class<T>): T
}