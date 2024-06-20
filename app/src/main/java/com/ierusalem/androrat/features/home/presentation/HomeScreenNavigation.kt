package com.ierusalem.androrat.features.home.presentation

sealed interface HomeScreenNavigation {
    data object OpenMessageFragment : HomeScreenNavigation
    //data class OpenGistsFragment(val username: String) : HomeScreenNavigation
}
