package com.ierusalem.androrat.features.rtc_features.rtc.domain

sealed interface AndroRtcEvents{
    data object OnNavBackClicked: AndroRtcEvents
    data object OnDeviceClicked: AndroRtcEvents
}
