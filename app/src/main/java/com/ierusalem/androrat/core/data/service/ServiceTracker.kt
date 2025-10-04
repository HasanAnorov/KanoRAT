package com.ierusalem.androrat.core.data.service

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

enum class ServiceState {
    STARTED,
    STOPPED,
}

private const val name = "SPY_SERVICE_KEY"
private const val key = "SPY_SERVICE_STATE"

fun setServiceState(context: Context, state: ServiceState) {
    val sharedPrefs = getPreferences(context)
    sharedPrefs.edit {
        putString(key, state.name)
    }
}

fun getServiceState(context: Context): ServiceState {
    val sharedPrefs = getPreferences(context)
    val value = sharedPrefs.getString(key, ServiceState.STOPPED.name) ?: ServiceState.STOPPED.name
    return ServiceState.valueOf(value)
}

private fun getPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(name, 0)
}
