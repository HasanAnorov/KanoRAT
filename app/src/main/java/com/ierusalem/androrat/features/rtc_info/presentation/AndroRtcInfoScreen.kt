package com.ierusalem.androrat.features.rtc_info.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ierusalem.androrat.core.ui.components.AndroRatAppBar
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.features.rtc_info.domain.AndroRtcInfoUiState
import com.ierusalem.androrat.features.rtc_info.presentation.components.DeviceInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroRtcInfoScreen(
    modifier: Modifier = Modifier,
    onNavIconClick: () -> Unit,
    uiState: AndroRtcInfoUiState,
) {
    Surface(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            content = {
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
                DeviceInfo()
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