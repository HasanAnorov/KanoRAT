package com.ierusalem.androrat.core.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.math.log10
import kotlin.math.pow

fun Long?.toReadableDate(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // For API 26+ (Android 8.0+)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        Instant.ofEpochMilli(this ?: 0L)
            .atZone(ZoneId.systemDefault())
            .format(formatter)
    } else {
        // For older versions (Android < 8.0)
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        formatter.format(Date(this ?: 0L))
    }
}

fun Long?.toReadableFileSize(): String {
    if (this == null || this <= 0) return "0 B"
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (log10(this.toDouble()) / log10(1024.0)).toInt()
    return String.format(
        Locale.getDefault(),
        "%.1f %s",
        this / 1024.0.pow(digitGroups.toDouble()),
        units[digitGroups]
    )
}


