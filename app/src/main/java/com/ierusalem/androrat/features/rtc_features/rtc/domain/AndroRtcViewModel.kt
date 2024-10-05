package com.ierusalem.androrat.features.rtc_features.rtc.domain

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.ierusalem.androrat.core.data.AppPreview
import com.ierusalem.androrat.core.data.preferences.DataStorePreferenceRepository
import com.ierusalem.androrat.core.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.androrat.core.ui.navigation.NavigationEventDelegate
import com.ierusalem.androrat.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AndroRtcViewModel @Inject constructor(
    private val dataStorePreferenceRepository: DataStorePreferenceRepository
) : ViewModel(),
    NavigationEventDelegate<AndroRtcNavigation> by DefaultNavigationEventDelegate() {

    private val _state: MutableStateFlow<AndroRtcUiState> = MutableStateFlow(AndroRtcUiState())
    val state = _state.asStateFlow()


}

@Immutable
data class AndroRtcUiState(
    val androRtcClients: Resource<List<AndroRtcClient>> = Resource.Success(AppPreview.PreviewAndroRtc.androRtcClients)
)

data class AndroRtcClient(
    val clientName:String,
    val deviceName:String,
    val lastOnlineTime:String,
    val isOnline:Boolean,
    val provider:String,
)