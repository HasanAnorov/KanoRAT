package com.ierusalem.androrat.features.rtc_features.domain

import androidx.lifecycle.ViewModel
import com.ierusalem.androrat.core.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.NavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.emitNavigation
import com.ierusalem.androrat.features.rtc_features.rtc.domain.AndroRtcEvents
import com.ierusalem.androrat.features.rtc_features.rtc.domain.AndroRtcNavigation
import com.ierusalem.androrat.features.rtc_features.rtc.presentation.AndroRtcUiState
import com.ierusalem.androrat.features.rtc_features.rtc_info.presentation.AndroRtcInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AndroRtcViewModel @Inject constructor() : ViewModel(),
    NavigationEventDelegate<AndroRtcNavigation> by DefaultNavigationEventDelegate() {

    private val _uiState: MutableStateFlow<AndroRtcUiState> = MutableStateFlow(AndroRtcUiState())
    val uiState = _uiState.asStateFlow()

    private val _infoUiState: MutableStateFlow<AndroRtcInfoUiState> = MutableStateFlow(AndroRtcInfoUiState())
    val infoUiState = _infoUiState.asStateFlow()

    fun handleEvents(event: AndroRtcEvents){
        when(event){
            AndroRtcEvents.OnDeviceClicked -> {
                emitNavigation(AndroRtcNavigation.OnDeviceClicked)
            }
            AndroRtcEvents.OnNavBackClicked -> {
                emitNavigation(AndroRtcNavigation.OnNavBackClicked)
            }
        }
    }

}