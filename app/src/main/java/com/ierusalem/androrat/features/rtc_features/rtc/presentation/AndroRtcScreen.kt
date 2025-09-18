package com.ierusalem.androrat.features.rtc_features.rtc.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.ui.components.AndroRatAppBar
import com.ierusalem.androrat.core.ui.components.ErrorScreen
import com.ierusalem.androrat.core.ui.components.LoadingScreen
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.core.utils.Resource
import com.ierusalem.androrat.features.rtc_features.rtc.domain.AndroRtcEvents
import com.ierusalem.androrat.features.rtc_features.rtc.domain.AndroRtcUiState
import com.ierusalem.androrat.features.rtc_features.rtc.presentation.components.RtcClientItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroRtcScreen(
    modifier: Modifier = Modifier,
    uiState: AndroRtcUiState,
    eventHandler: (AndroRtcEvents) -> Unit
) {
    Surface(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            content = {
                AndroRatAppBar(
                    modifier = modifier,
                    onNavIconPressed = { eventHandler(AndroRtcEvents.OnNavBackClicked) },
                    title = {
                        Text(
                            text = stringResource(R.string.kano_rtc),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    navIcon = Icons.AutoMirrored.Filled.ArrowBack
                )
                when (uiState.androRtcClients) {
                    is Resource.Loading -> LoadingScreen()
                    is Resource.Success -> {
                        val clients = uiState.androRtcClients.data!!
                        LazyColumn(
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            itemsIndexed(clients) { index, androRtcClient ->
                                RtcClientItem(
                                    androRtcClient = androRtcClient,
                                    onClick = { eventHandler(AndroRtcEvents.OnDeviceClicked) }
                                )
                                if (index < clients.size - 1) {
                                    HorizontalDivider(
                                        Modifier.padding(
                                            horizontal = 8.dp,
                                            vertical = 2.dp
                                        )
                                    )
                                }
                            }
                        }
                    }

                    is Resource.Failure -> ErrorScreen()
                }
            }
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreviewLight() {
    AndroRATTheme {
        AndroRtcScreen(
            modifier = Modifier,
            eventHandler = {},
            uiState = AndroRtcUiState(),
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreviewDark() {
    AndroRATTheme(isDarkTheme = true) {
        AndroRtcScreen(
            modifier = Modifier,
            eventHandler = {},
            uiState = AndroRtcUiState(),
        )
    }
}