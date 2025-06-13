package com.ierusalem.androrat.features.login.domain

sealed interface LoginNavigation {
    data object ToHome: LoginNavigation
}