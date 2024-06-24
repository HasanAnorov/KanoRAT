package com.ierusalem.androrat.features.rtc_info.domain

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.ierusalem.androrat.core.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.NavigationEventDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AndroRtcInfoViewModel @Inject constructor() : ViewModel(),
    NavigationEventDelegate<AndroRtcInfoNavigation> by DefaultNavigationEventDelegate() {

    private val _state: MutableStateFlow<AndroRtcInfoUiState> = MutableStateFlow(AndroRtcInfoUiState())
    val state = _state.asStateFlow()



}

@Immutable
data class AndroRtcInfoUiState(
    val isOnline:Boolean = false
)