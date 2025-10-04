package com.ierusalem.androrat.core.data.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.ierusalem.androrat.core.utils.log

class StartReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED && getServiceState(context) == ServiceState.STARTED) {
            Log.d("ENDLESS-SERVICE", "onReceive: starting service ")
            Intent(context, EndlessService::class.java).also {
                it.action = Actions.START.name
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    log("Starting the service in >=26 Mode from a BroadcastReceiver")
                    context.startForegroundService(it)
                    return
                }
                log("Starting the service in < 26 Mode from a BroadcastReceiver")
                context.startService(it)
            }
        }else{
            Log.d("ENDLESS-SERVICE", "onReceive: starting service else ")
        }
    }
}
