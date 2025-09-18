package com.ierusalem.androrat.features.rtc_features.rtc_info.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.core.ui.theme.MontserratFontFamily

@Composable
fun DeviceData(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
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
                text = "Acquired Device Data",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.background,
            thickness = 1.dp
        )
        DeviceDataItem(
            icon = R.drawable.map,
            value = "(31 locations)",
            state = R.string.location,
            onClick = {}
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.background,
            thickness = 1.dp
        )
        DeviceDataItem(
            icon = R.drawable.folder_cloud,
            value = "(192 files)",
            state = R.string.files,
            onClick = {}
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.background,
            thickness = 1.dp
        )
        DeviceDataItem(
            icon = R.drawable.messages,
            value = "(12 messages)",
            state = R.string.messages,
            onClick = {}
        )
    }
}

@Composable
fun DeviceDataItem(
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.map,
    @StringRes state: Int,
    value: String = "",
    onClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onClick() }
    ) {
        IconButton(onClick = { /*TODO*/ }, enabled = false) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Text(
            modifier = Modifier,
            text = stringResource(id = state),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier
                .weight(1F)
                .padding(start = 4.dp),
            text = value,
            fontFamily = MontserratFontFamily,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.outline
        )
        Icon(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun DeviceDataItemPreview(modifier: Modifier = Modifier) {
    AndroRATTheme {
        Surface {
            DeviceDataItem(
                state = R.string.location,
                value = "(31 locations)"
            )
        }
    }
}

@Preview
@Composable
fun DeviceDataItemPreviewDark(modifier: Modifier = Modifier) {
    AndroRATTheme(isDarkTheme = true) {
        Surface {
            DeviceDataItem(
                state = R.string.location,
                value = ""
            )
        }
    }
}


@Preview
@Composable
fun DeviceDataPreview(modifier: Modifier = Modifier) {
    AndroRATTheme {
        Surface {
            DeviceData()
        }
    }
}

@Preview
@Composable
fun DeviceDataPreviewDark(modifier: Modifier = Modifier) {
    AndroRATTheme(isDarkTheme = true) {
        Surface {
            DeviceData()
        }
    }
}