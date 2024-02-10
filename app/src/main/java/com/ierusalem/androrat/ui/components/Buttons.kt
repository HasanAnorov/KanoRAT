package com.ierusalem.androrat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ierusalem.androrat.R
import com.ierusalem.androrat.ui.theme.AndroRATTheme
import com.ierusalem.androrat.ui.theme.dimens


@Composable
fun CommonAndroRatButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.clickable { onClick() },
        content = {
            Text(
                text = text,
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.dimens.spacing16,
                        vertical = MaterialTheme.dimens.spacing26
                    )
                    .fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        },
    )
}

@Preview(showSystemUi = true)
@Composable
private fun CommonJetHubLoginButton_LightPreview() {
    AndroRATTheme(darkTheme = false) {
        CommonAndroRatButton(
            text = stringResource(id = R.string.app_name),
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.spacing16)
                .clip(RoundedCornerShape(MaterialTheme.dimens.spacing12))
                .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f)),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CommonJetHubLoginButton_DarkPreview() {
    AndroRATTheme(darkTheme = true) {
        CommonAndroRatButton(
            text = stringResource(id = R.string.app_name),
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.spacing16)
                .clip(RoundedCornerShape(MaterialTheme.dimens.spacing12))
                .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f)),
        )
    }
}

