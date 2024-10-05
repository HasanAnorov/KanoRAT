package com.ierusalem.androrat.features.rtc_features.rtc.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.features.rtc_features.rtc.domain.AndroRtcClient

@Composable
fun RtcClientItem(
    modifier: Modifier = Modifier,
    androRtcClient: AndroRtcClient,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .height(IntrinsicSize.Max)
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .weight(1F)
                .padding(end = 8.dp)
        ) {
            Row {
                Text(
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 16.sp,
                    text = stringResource(R.string.client),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8F)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 16.sp,
                    text = androRtcClient.clientName,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Row(modifier = Modifier.padding(top = 2.dp)) {
                Text(
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 16.sp,
                    text = stringResource(R.string.device),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8F)
                )
                Text(
                    modifier = Modifier.weight(1F),
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 16.sp,
                    text = androRtcClient.deviceName,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Row(modifier = Modifier.padding(top = 2.dp)) {
                Text(
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 16.sp,
                    text = stringResource(R.string.provider),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8F)
                )
                Text(
                    modifier = Modifier.weight(1F),
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 16.sp,
                    text = androRtcClient.provider,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = androRtcClient.lastOnlineTime,
                fontSize = 10.sp,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
            val color = if (androRtcClient.isOnline) Color(0xFF35C47C) else Color.Red
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                val icon = if (androRtcClient.isOnline) R.drawable.wifi else R.drawable.wifi_off
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(4.dp),
                    painter = painterResource(id = icon),
                    tint = Color.White,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
fun ContactItemPreview() {
    AndroRATTheme {
        RtcClientItem(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth(),
            onClick = {},
            androRtcClient = AndroRtcClient(
                clientName = "Hasan",
                deviceName = "POCO F3",
                lastOnlineTime = "12:34",
                isOnline = true,
                provider = "Open Weather"
            )
        )
    }
}

@Preview
@Composable
fun ContactItemPreviewDark() {
    AndroRATTheme(isDarkTheme = true) {
        RtcClientItem(
            onClick = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth(),
            androRtcClient = AndroRtcClient(
                clientName = "Hasan",
                deviceName = "POCO F3",
                lastOnlineTime = "12:34",
                isOnline = false,
                provider = "Google"
            )
        )
    }
}