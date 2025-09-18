package com.ierusalem.androrat.features.rtc_features.rtc.domain

sealed interface AndroRtcNavigation {
    data object OnNavBackClicked: AndroRtcNavigation
    data object OnDeviceClicked: AndroRtcNavigation
}