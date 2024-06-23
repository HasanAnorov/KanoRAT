package com.ierusalem.androrat.features.settings.presentation

sealed interface SettingsScreenNavigation {
    data object NavIconClick: SettingsScreenNavigation
}