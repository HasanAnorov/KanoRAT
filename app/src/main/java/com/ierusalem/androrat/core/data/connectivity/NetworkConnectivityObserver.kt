package com.ierusalem.androrat.core.data.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.net.InetAddress
import java.nio.ByteBuffer
import java.nio.ByteOrder

class NetworkConnectivityObserver(
    private val context: Context
): ConnectivityObserver {

    private val networkConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

    override fun getWifiServerIpAddress(): String {
        val ip = InetAddress.getByAddress(
            ByteBuffer
                .allocate(Integer.BYTES)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(wifiManager.dhcpInfo.serverAddress)
                .array()
        ).hostAddress
        return ip
    }
    override fun observeWifiState(): Flow<ConnectivityObserver.NetworkStatus> {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        return callbackFlow {
            val wifiNetworkCallback  = object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(ConnectivityObserver.NetworkStatus.Available) }
                }
                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(ConnectivityObserver.NetworkStatus.Loosing) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivityObserver.NetworkStatus.Lost) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivityObserver.NetworkStatus.Unavailable) }
                }
            }
            networkConnectivityManager.requestNetwork(networkRequest, wifiNetworkCallback)
            awaitClose {
                networkConnectivityManager.unregisterNetworkCallback(wifiNetworkCallback)
            }
        }.distinctUntilChanged()
    }

    override fun observe(): Flow<ConnectivityObserver.NetworkStatus> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(ConnectivityObserver.NetworkStatus.Available) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(ConnectivityObserver.NetworkStatus.Loosing) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivityObserver.NetworkStatus.Lost) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivityObserver.NetworkStatus.Unavailable) }
                }
            }
            networkConnectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                networkConnectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }

}