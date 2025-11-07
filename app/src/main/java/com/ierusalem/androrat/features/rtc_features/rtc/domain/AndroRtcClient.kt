package com.ierusalem.androrat.features.rtc_features.rtc.domain

data class AndroRtcClient(
    val clientName:String,
    val deviceName:String,
    val lastOnlineTime:String,
    val isOnline:Boolean,
    val provider:String,
)