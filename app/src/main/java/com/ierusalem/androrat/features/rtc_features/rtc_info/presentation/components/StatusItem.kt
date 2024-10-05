package com.ierusalem.androrat.features.rtc_features.rtc_info.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.core.ui.components.baselineHeight
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.core.ui.theme.MontserratFontFamily

@Composable
fun StatusProperty(
    modifier: Modifier = Modifier,
    status: String,
    state: String,
    stateColor: Color = MaterialTheme.colorScheme.onBackground
) {
    Column(
        modifier = modifier.padding(bottom = 8.dp)
    ) {
        Text(
            text = status,
            fontFamily = MontserratFontFamily,
            modifier = Modifier.baselineHeight(16.dp),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = state,
            modifier = Modifier.baselineHeight(20.dp),
            style = MaterialTheme.typography.titleMedium,
            color = stateColor
        )
    }
}

@Preview
@Composable
private fun PreviewPort() {
    AndroRATTheme {
        Surface {
            StatusProperty(
                state = "Xiomi",
                status = "Brand: "
            )
        }
    }
}

@Preview
@Composable
private fun PreviewPortDark() {
    AndroRATTheme(isDarkTheme = true) {
        Surface {
            StatusProperty(
                state = "Xiomi",
                status = "Brand: "
            )
        }
    }
}