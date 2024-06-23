package com.ierusalem.androrat.core.data.connectivity

import kotlinx.coroutines.flow.Flow


interface ConnectivityObserver {

    fun observe(): Flow<NetworkStatus>

    fun observeWifiState(): Flow<NetworkStatus>

    fun getWifiServerIpAddress(): String

    enum class NetworkStatus{
        Available, Unavailable, Loosing, Lost
    }
}