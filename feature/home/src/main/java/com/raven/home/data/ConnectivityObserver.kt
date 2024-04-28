package com.raven.home.data

import kotlinx.coroutines.flow.Flow


interface ConnectivityObserver {
    fun observerNetwork(): Flow<NetworkStatus>

    enum class NetworkStatus {
        Available,
        Unavailable,
        Losing,
        Lost
    }
}