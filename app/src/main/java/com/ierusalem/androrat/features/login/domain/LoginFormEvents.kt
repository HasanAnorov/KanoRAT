package com.ierusalem.androrat.features.login.domain


sealed class LoginFormEvents {
    data class UsernameChanged(val username: String) : LoginFormEvents()
    data class PasswordChanged(val password: String) : LoginFormEvents()
    data object Login : LoginFormEvents()
    data object PasswordVisibilityChanged: LoginFormEvents()
}