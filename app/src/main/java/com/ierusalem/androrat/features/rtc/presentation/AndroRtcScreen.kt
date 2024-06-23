package com.ierusalem.androrat.features.rtc.presentation

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.ui.components.AndroRatAppBar
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroRtcScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            content = {
                AndroRatAppBar(
                    modifier = modifier,
                    onNavIconPressed = {  },
                    title = {
                        Text(
                            text = stringResource(R.string.andro_rtc),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    navIcon = Icons.AutoMirrored.Filled.ArrowBack
                )
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
//            uiState = SettingsState(),
//            eventHandler = {}
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreviewDark() {
    AndroRATTheme(isDarkTheme = true) {
        AndroRtcScreen(
            modifier = Modifier,
//            uiState = SettingsState(),
//            eventHandler = {}
        )
    }
}