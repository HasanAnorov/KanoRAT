package com.ierusalem.androrat.features.link_phishing.domain

import androidx.lifecycle.ViewModel
import com.ierusalem.androrat.core.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.NavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.emitNavigation
import com.ierusalem.androrat.features.link_phishing.presentation.LinkPhishingEvents
import com.ierusalem.androrat.features.link_phishing.presentation.LinkPhishingNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LinkPhishingViewModel: ViewModel(),
    NavigationEventDelegate<LinkPhishingNavigation> by DefaultNavigationEventDelegate() {

    private val _uiState = MutableStateFlow(LinkPhishingUiState())
    val uiState: StateFlow<LinkPhishingUiState> = _uiState.asStateFlow()

    fun onEvent(event: LinkPhishingEvents) {
        when(event) {
            LinkPhishingEvents.OnNavIconClick -> {
                emitNavigation(LinkPhishingNavigation.OnNavIconClick)
            }
        }
    }
}

data class LinkPhishingUiState(
    val devices: List<String> = listOf()
)