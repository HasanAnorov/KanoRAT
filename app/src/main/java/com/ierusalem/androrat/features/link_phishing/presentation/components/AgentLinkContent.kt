package com.ierusalem.androrat.features.link_phishing.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme

@Composable
fun AgentLinkContent(
    url: String,
    modifier: Modifier = Modifier
) {
    val clipboard = LocalClipboardManager.current
    val uriHandler = LocalUriHandler.current

    Box(modifier = modifier) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = url,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { runCatching { uriHandler.openUri(url) } },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Button(
                    onClick = {
                        clipboard.setText(AnnotatedString(url))
                    },
                    shape = MaterialTheme.shapes.large
                ) {
                    Text("Copy")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AgentLinkContentPreview_Light() {
    AndroRATTheme(isDarkTheme = false) {
        AgentLinkContent(
            url = "https://example.com/my-page",
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AgentLinkContentPreview_Dark() {
    AndroRATTheme(isDarkTheme = true) {
        AgentLinkContent(
            url = "https://example.com/my-page",
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        )
    }
}

