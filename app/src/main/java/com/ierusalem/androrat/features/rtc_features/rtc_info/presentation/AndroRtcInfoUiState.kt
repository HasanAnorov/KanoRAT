package com.ierusalem.androrat.features.rtc_features.rtc_info.presentation

import androidx.compose.runtime.Immutable
import com.ierusalem.androrat.core.utils.Resource
import com.ierusalem.androrat.features.rtc_features.rtc_info.domain.AndroRtcClientInfo

@Immutable
data class AndroRtcInfoUiState(
    val isOnline:Boolean = false,
    val androRtcClientInfo: Resource<AndroRtcClientInfo> = Resource.Loading()
)