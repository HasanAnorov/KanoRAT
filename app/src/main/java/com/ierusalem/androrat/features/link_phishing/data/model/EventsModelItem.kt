package com.ierusalem.androrat.features.link_phishing.data.model


import com.google.gson.annotations.SerializedName
import com.ierusalem.androrat.features.link_phishing.data.LinkedDeviceInfo

data class EventsModelItem(
    @SerializedName("browser")
    val browser: String,
    @SerializedName("browser_version")
    val browserVersion: String,
    @SerializedName("created")
    val created: String,
    @SerializedName("device")
    val device: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("ip")
    val ip: String,
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
    val osVersion: String,
    @SerializedName("updated")
    val updated: String
){
    fun toLinkedDeviceInfo(): LinkedDeviceInfo {
        return LinkedDeviceInfo(
            browser = browser,
            browserVersion = browserVersion,
            device = device,
            ipAddress = ip,
            isBot = isBot,
            isMobile = isMobile,
            isPc = isPc,
            isTablet = isTablet,
            isTouchCapable = isTouchCapable,
            os = os,
            osVersion = osVersion
        )
    }
}