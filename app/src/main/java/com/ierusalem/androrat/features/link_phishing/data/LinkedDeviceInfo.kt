package com.ierusalem.androrat.features.link_phishing.data


import com.google.gson.annotations.SerializedName

data class LinkedDeviceInfo(
    @SerializedName("browser")
    val browser: String,
    @SerializedName("browser_version")
    val browserVersion: String,
    @SerializedName("device")
    val device: String,
    @SerializedName("ip_address")
    val ipAddress: String,
    @SerializedName("is_bot")
    val isBot: Boolean,
    @SerializedName("is_mobile")
    val isMobile: Boolean,
    @SerializedName("is_pc")
    val isPc: Boolean,
    @SerializedName("is_tablet")
    val isTablet: Boolean,
    @SerializedName("is_touch_capable")
    val isTouchCapable: Boolean,
    @SerializedName("os")
    val os: String,
    @SerializedName("os_version")
    val osVersion: String
)