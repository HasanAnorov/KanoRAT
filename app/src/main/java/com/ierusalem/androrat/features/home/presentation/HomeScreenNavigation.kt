package com.ierusalem.androrat.features.home.presentation

sealed interface HomeScreenNavigation {
    data object NavigateToMessageFragment : HomeScreenNavigation
    data object NavigateToAndroRtcFragment : HomeScreenNavigation
    data object NavigateToLinkPhishingFragment : HomeScreenNavigation
    data object NavigateToSettingsFragment : HomeScreenNavigation
}
