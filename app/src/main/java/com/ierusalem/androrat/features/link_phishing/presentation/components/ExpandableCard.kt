package com.ierusalem.androrat.features.link_phishing.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.features.link_phishing.data.LinkedDeviceInfo
import com.ierusalem.androrat.features.rtc_features.rtc_info.presentation.components.StatusProperty

@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    deviceInfo: LinkedDeviceInfo
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier,
                text = "IP Address: ",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                modifier = Modifier.weight(1F),
                text = deviceInfo.ipAddress,
                style = MaterialTheme.typography.titleMedium
            )
        }
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground.copy(0.1F),
                    thickness = 1.dp
                )
                StatusProperty(
                    modifier = Modifier,
                    status = "Browser: ",
                    state = deviceInfo.browser
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.background,
                    thickness = 1.dp
                )
                StatusProperty(
                    modifier = Modifier,
                    status = "Browser Version: ",
                    state = deviceInfo.browserVersion
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.background,
                    thickness = 1.dp
                )
                StatusProperty(
                    modifier = Modifier,
                    status = "OS: ",
                    state = deviceInfo.os
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.background,
                    thickness = 1.dp
                )
                StatusProperty(
                    modifier = Modifier,
                    status = "OS Version: ",
                    state = deviceInfo.osVersion
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.background,
                    thickness = 1.dp
                )
                StatusProperty(
                    modifier = Modifier,
                    status = "Device: ",
                    state = deviceInfo.device
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.background,
                    thickness = 1.dp
                )
                StatusProperty(
                    modifier = Modifier,
                    status = "Is Mobile: ",
                    state = deviceInfo.isMobile.toString()
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.background,
                    thickness = 1.dp
                )
                StatusProperty(
                    modifier = Modifier,
                    status = "Is Tablet: ",
                    state = deviceInfo.isTablet.toString()
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.background,
                    thickness = 1.dp
                )
                StatusProperty(
                    modifier = Modifier,
                    status = "Is Touch Capable: ",
                    state = deviceInfo.isTouchCapable.toString()
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.background,
                    thickness = 1.dp
                )
                StatusProperty(
                    modifier = Modifier,
                    status = "Is PC: ",
                    state = deviceInfo.isPc.toString()
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.background,
                    thickness = 1.dp
                )
                StatusProperty(
                    modifier = Modifier,
                    status = "Is Bot: ",
                    state = deviceInfo.isBot.toString()
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExpandableCard() {
    AndroRATTheme {
        ExpandableCard(
            deviceInfo = LinkedDeviceInfo(
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
    }
}