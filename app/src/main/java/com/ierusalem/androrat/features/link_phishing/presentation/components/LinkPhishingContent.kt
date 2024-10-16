package com.ierusalem.androrat.features.link_phishing.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.core.ui.components.ErrorScreen
import com.ierusalem.androrat.core.ui.components.LoadingScreen
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.core.utils.Resource
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
            Column {
                Box(
                    modifier = Modifier
                        .clickable { }
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceDim.copy(0.5F)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .weight(1F),
                            text = "Link Events",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp)
                ) {
                    itemsIndexed(devices.data!!) { index, deviceInfo ->
                        ExpandableCard(
                            modifier = Modifier
                                .padding(horizontal = 8.dp),
                            deviceInfo = deviceInfo.toLinkedDeviceInfo()
                        )
                        if (index < devices.data.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                color = MaterialTheme.colorScheme.outline,
                                thickness = 0.5.dp
                            )
                        }
                    }
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
//                    devices = Resource.Success(
//                        data = FakeData.linedDeviceList
//                    )
                    devices = Resource.Loading()
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
//                    devices = Resource.Success(
//                        data = listOf(
//                            LinkedDeviceInfo(
//                                browser = "Yandex",
//                                browserVersion = "131.0",
//                                device = "Xiaomi",
//                                ipAddress = "192.168.0.1",
//                                isBot = false,
//                                isMobile = true,
//                                isPc = false,
//                                isTablet = false,
//                                isTouchCapable = true,
//                                os = "Android",
//                                osVersion = "13.0"
//                            )
//                        )
//                    )
                    devices = Resource.Loading()
                )
            )
        }
    }
}