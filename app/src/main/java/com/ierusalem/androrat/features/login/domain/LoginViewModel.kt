package com.ierusalem.androrat.features.login.domain

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.ierusalem.androrat.core.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.NavigationEventDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel:ViewModel(),
    NavigationEventDelegate<LoginScreenNavigation> by DefaultNavigationEventDelegate() {

    private val _state: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

}

@Immutable
data class LoginUiState(
    val username:String = "",
    val usernameError:String? = null
)