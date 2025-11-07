package com.ierusalem.androrat.features.rtc_features.rtc_info.domain

/**
 * Currently app display these information
 *
 * "Brand: ${Build.BRAND} \n" +
 *             "DeviceID: ${
 *                 Settings.Secure.getString(
 *                     this.contentResolver,
 *                     Settings.Secure.ANDROID_ID
 *                 )
 *             } \n" +
 *             "Model: ${Build.MODEL} \n" +
 *             "ID: ${Build.ID} \n" +
 *             "SDK: ${Build.VERSION.SDK_INT} \n" +
 *             "Manufacture: ${Build.MANUFACTURER} \n" +
 *             "Hardware: ${Build.HARDWARE} \n" +
 *             "Bootloader: ${Build.BOOTLOADER} \n" +
 *             "User: ${Build.USER} \n" +
 *             "Type: ${Build.TYPE} \n" +
 *             "Base: ${Build.VERSION_CODES.BASE} \n" +
 *             "Incremental: ${Build.VERSION.INCREMENTAL} \n" +
 *             "Board: ${Build.BOARD} \n" +
 *             "Host: ${Build.HOST} \n" +
 *             "FingerPrint: ${Build.FINGERPRINT} \n" +
 *             "Display: ${Build.DISPLAY} \n" +
 *             "IMEI: $imei \n" +
 *             "Version Code: ${Build.VERSION.RELEASE}"
 */

data class DeviceInfo(
    val brand: String,
    val deviceID: String,
    val model: String,
    val id: String,
    val sdk: String,
    val manufacture: String,
    val hardware: String,
    val bootloader: String,
    val user: String,
    val type: String,
    val base: String,
    val incremental: String,
    val board: String,
    val host: String,
    val fingerprint: String,
    val display: String,
    val imei: String,
    val versionCode: String
)