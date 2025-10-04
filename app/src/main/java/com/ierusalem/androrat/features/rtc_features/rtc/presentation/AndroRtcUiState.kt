package com.ierusalem.androrat.features.rtc_features.rtc.presentation

import androidx.compose.runtime.Immutable
import com.ierusalem.androrat.core.data.AppPreview
import com.ierusalem.androrat.core.utils.Resource
import com.ierusalem.androrat.features.rtc_features.rtc.domain.AndroRtcClient

@Immutable
data class AndroRtcUiState(
    val androRtcClients: Resource<List<AndroRtcClient>> = Resource.Success(AppPreview.PreviewAndroRtc.androRtcClients)
)