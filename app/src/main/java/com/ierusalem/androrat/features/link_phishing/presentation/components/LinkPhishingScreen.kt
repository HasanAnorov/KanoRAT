package com.ierusalem.androrat.features.link_phishing.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
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
fun LinkPhishingScreen(
    modifier: Modifier = Modifier,
    eventHandler: (LinkPhishingEvents) -> Unit = {},
) {
    Surface {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            item {
                AndroRatAppBar(
                    modifier = modifier,
                    onNavIconPressed = { eventHandler(LinkPhishingEvents.OnNavIconClick) },
                    title = {
                        Text(
                            text = stringResource(R.string.link_phishin),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    navIcon = Icons.AutoMirrored.Filled.ArrowBack
                )
            }
        }
    }
}

@Preview
@Composable
private fun LinkPhishingScreen_Light() {
    AndroRATTheme {
        LinkPhishingScreen()
    }
}

@Preview
@Composable
private fun LinkPhishing_Dark() {
    AndroRATTheme(isDarkTheme = true) {
        LinkPhishingScreen()
    }
}