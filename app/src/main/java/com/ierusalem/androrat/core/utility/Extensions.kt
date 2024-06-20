package com.ierusalem.androrat.core.utility

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

//todo try to open exact permission screen at once
fun Fragment.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", activity?.packageName, null)
    ).also(::startActivity)
}

fun <T> Flow<T>.executeWithLifecycle(
    lifecycle: Lifecycle,
    action: suspend (T) -> Unit
) = flowWithLifecycle(lifecycle)
    .onEach(action)
    .launchIn(lifecycle.coroutineScope)