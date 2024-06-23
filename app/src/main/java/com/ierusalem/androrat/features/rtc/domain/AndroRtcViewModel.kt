package com.ierusalem.androrat.features.rtc.domain

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.ierusalem.androrat.core.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.NavigationEventDelegate

class AndroRtcViewModel:ViewModel(),
    NavigationEventDelegate<AndroRtcNavigation> by DefaultNavigationEventDelegate() {

}

@Immutable
data class AndroRtcUiState(
    val smth:String  = ""
)