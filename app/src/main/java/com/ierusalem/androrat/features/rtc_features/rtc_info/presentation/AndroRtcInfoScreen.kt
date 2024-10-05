package com.ierusalem.androrat.features.rtc_features.rtc_info.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.core.ui.components.AndroRatAppBar
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.features.rtc_features.rtc_info.domain.AndroRtcInfoUiState
import com.ierusalem.androrat.features.rtc_features.rtc_info.presentation.components.ClientCallProperty
import com.ierusalem.androrat.features.rtc_features.rtc_info.presentation.components.DeviceData
import com.ierusalem.androrat.features.rtc_features.rtc_info.presentation.components.DeviceInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroRtcInfoScreen(
    modifier: Modifier = Modifier,
    onNavIconClick: () -> Unit,
    uiState: AndroRtcInfoUiState,
) {
    Surface(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            content = {
                item {
                    AndroRatAppBar(
                        modifier = modifier,
                        onNavIconPressed = onNavIconClick,
                        title = {
                            Text(
                                text = "Client Name",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        navIcon = Icons.AutoMirrored.Filled.ArrowBack
                    )
                }
                item {
                    ClientCallProperty(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                }
                item {
                    DeviceInfo(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceDim.copy(0.2F))
                    )
                }
                item {
                    DeviceData(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceDim.copy(0.2F))
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreviewLight() {
    AndroRATTheme {
        AndroRtcInfoScreen(
            modifier = Modifier,
            onNavIconClick = {},
            uiState = AndroRtcInfoUiState(),
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreviewDark() {
    AndroRATTheme(isDarkTheme = true) {
        AndroRtcInfoScreen(
            modifier = Modifier,
            onNavIconClick = {},
            uiState = AndroRtcInfoUiState(),
        )
    }
}
