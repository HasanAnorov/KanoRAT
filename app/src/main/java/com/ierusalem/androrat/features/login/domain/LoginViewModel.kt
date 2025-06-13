package com.ierusalem.androrat.features.login.domain

import androidx.compose.runtime.Immutable
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ierusalem.androrat.core.data.preferences.DataStorePreferenceRepository
import com.ierusalem.androrat.core.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.NavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.emitNavigation
import com.ierusalem.androrat.core.utils.FieldValidator
import com.ierusalem.androrat.core.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStorePreferenceRepository: DataStorePreferenceRepository
) : ViewModel(), DefaultLifecycleObserver,
    NavigationEventDelegate<LoginNavigation> by DefaultNavigationEventDelegate() {

    private val _state: MutableStateFlow<LoginScreenState> = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()

    fun handleEvents(event: LoginFormEvents) {
        when (event) {
            LoginFormEvents.Login -> loginUser()
            is LoginFormEvents.UsernameChanged -> {
                _state.update {
                    it.copy(
                        username = event.username
                    )
                }
            }

            is LoginFormEvents.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            LoginFormEvents.PasswordVisibilityChanged -> {
                _state.update {
                    it.copy(
                        passwordVisibility = !state.value.passwordVisibility
                    )
                }
            }
        }
    }

    private fun loginUser() {

        val deviceLogin: String = runBlocking(Dispatchers.IO) {
            dataStorePreferenceRepository.getLogin.first()
        }

        val devicePassword: String = runBlocking(Dispatchers.IO) {
            dataStorePreferenceRepository.getPassword.first()
        }

        val validator = FieldValidator()
        val usernameResult = validator.validateUsername(
            loginEntry = state.value.username,
            deviceLogin = deviceLogin
        )
        val passwordResult = validator.validatePassword(
            passwordEntry = state.value.password,
            devicePassword = devicePassword
        )

        val hasError = listOf(
            usernameResult,
            passwordResult,
        ).any {
            !it.successful
        }
        if (hasError) {
            _state.update {
                it.copy(
                    usernameError = usernameResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                )
            }
            return
        }
        _state.update {
            it.copy(
                usernameError = null,
                passwordError = null,
            )
        }
        viewModelScope.launch {
            emitNavigation(LoginNavigation.ToHome)
        }
    }



}

@Immutable
data class LoginScreenState(
    val username: String = "",
    val usernameError: UiText.StringResource? = null,
    val password: String = "",
    val passwordError: UiText.StringResource? = null,
    val passwordVisibility: Boolean = false,
)
