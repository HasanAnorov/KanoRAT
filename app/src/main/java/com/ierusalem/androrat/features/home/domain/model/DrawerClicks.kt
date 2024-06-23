package com.ierusalem.androrat.features.home.domain.model

sealed interface DrawerClicks {
    data object NavigateToAndroRtc: DrawerClicks
    data object NavigateToSettings: DrawerClicks
}