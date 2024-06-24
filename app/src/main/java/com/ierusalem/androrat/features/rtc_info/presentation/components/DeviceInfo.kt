package com.ierusalem.androrat.features.rtc_info.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.ui.components.baselineHeight
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.core.ui.theme.MontserratFontFamily

@Composable
fun DeviceInfo() {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceDim.copy(0.2F)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceDim.copy(0.5F)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                text = "Device information details",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.background,
            thickness = 1.dp
        )
        StatusProperty(
            modifier = Modifier.padding(horizontal = 10.dp),
            status = "Something",
            state = R.string.andro_rtc
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.background,
            thickness = 1.dp
        )
        StatusProperty(
            modifier = Modifier.padding(horizontal = 10.dp),
            status = "Something",
            state = R.string.andro_rtc
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.background, thickness = 1.dp)
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Server Address",
                fontFamily = MontserratFontFamily,
                modifier = Modifier.baselineHeight(20.dp),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "state groupOwnerAddress",
                modifier = Modifier.baselineHeight(24.dp),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
fun DeviceInfoPreview(modifier: Modifier = Modifier) {
    AndroRATTheme {
        Surface {
            DeviceInfo()
        }
    }
}

@Preview
@Composable
fun DeviceInfoPreviewDark(modifier: Modifier = Modifier) {
    AndroRATTheme(isDarkTheme = true) {
        Surface {
            DeviceInfo()
        }
    }
}