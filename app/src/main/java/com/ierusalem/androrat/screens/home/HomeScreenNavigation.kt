package com.ierusalem.androrat.screens.home

sealed interface HomeScreenNavigation {
    data object OpenMessageFragment : HomeScreenNavigation
    //data class OpenGistsFragment(val username: String) : HomeScreenNavigation
}
