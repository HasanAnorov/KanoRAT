package com.ierusalem.androrat.features.rtc_features.rtc_info.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.core.ui.theme.MontserratFontFamily

@Composable
fun WebRtcProperty(modifier: Modifier = Modifier, targetName: String) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceDim.copy(0.4F)
        )
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row (
                    modifier = Modifier.weight(1F)
                ) {
                    Text(
                        text = "Target: ",
                        fontFamily = MontserratFontFamily,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 16.sp,
                        text = targetName,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.mic),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.video),
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
                modifier = Modifier,
                status = "Peer Status: ",
                state = "Waiting for connection ..."
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    AndroRATTheme {
        WebRtcProperty(
            targetName = "Gleb"
        )
    }
}

@Preview
@Composable
private fun PreviewDark() {
    AndroRATTheme(isDarkTheme = true) {
        WebRtcProperty(
            targetName = "Gleb"
        )
    }
}