package com.ierusalem.androrat.features.rtc_features.rtc_info.presentation

import androidx.compose.runtime.Immutable
import com.ierusalem.androrat.core.data.AppPreview
import com.ierusalem.androrat.core.utils.Resource
import com.ierusalem.androrat.features.rtc_features.rtc_info.domain.AndroRtcClientInfo

@Immutable
data class AndroRtcInfoUiState(
    val isOnline:Boolean = false,

//    loading content
//    val androRtcClientInfo: Resource<AndroRtcClientInfo> = Resource.Loading(),

//    success content
    val androRtcClientInfo: Resource<AndroRtcClientInfo> = Resource.Success(AppPreview.PreviewAndroRtcInfo.androRtcClientInfo)

//    failure content, todo - fix hardcore message
//    val androRtcClientInfo: Resource<AndroRtcClientInfo> = Resource.Failure("Something went wrong")

)