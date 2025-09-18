package com.ierusalem.androrat.features.rtc_features.rtc_info.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme

@Composable
fun DeviceInfo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    text = "Device information details",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium
                )
                Icon(
                    modifier = Modifier.padding(end = 16.dp),
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.background,
            thickness = 1.dp
        )
        StatusProperty(
            modifier = Modifier.padding(horizontal = 10.dp),
            status = "Brand: ",
            state = "Pixel"
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.background,
            thickness = 1.dp
        )
        StatusProperty(
            modifier = Modifier.padding(horizontal = 10.dp),
            status = "DeviceID: ",
            state = "7d6d8sds8878sdd88ss"
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.background,
            thickness = 1.dp
        )
        StatusProperty(
            modifier = Modifier.padding(horizontal = 10.dp),
            status = "Model: ",
            state = "Pixel 8"
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.background,
            thickness = 1.dp
        )
        StatusProperty(
            modifier = Modifier.padding(horizontal = 10.dp),
            status = "ID: ",
            state = "OPM1.112334434"
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun DeviceInfoPreview() {
    AndroRATTheme {
        Surface {
            DeviceInfo()
        }
    }
}

@Preview
@Composable
fun DeviceInfoPreviewDark() {
    AndroRATTheme(isDarkTheme = true) {
        Surface {
            DeviceInfo()
        }
    }
}