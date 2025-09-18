package com.ierusalem.androrat.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.app.AppLanguage
import com.ierusalem.androrat.core.data.AppPreview
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme

@Composable
fun LanguageDialog(
    modifier: Modifier = Modifier,
    languages: List<AppLanguage>,
    onDismissDialog: () -> Unit,
    selectedLanguage: AppLanguage,
    onLanguageSelected: (language: AppLanguage) -> Unit
) {

    val options by rememberSaveable {
        mutableStateOf(languages)
    }
    var tempSelectedLanguage by rememberSaveable {
        mutableStateOf(selectedLanguage)
    }

    Dialog(
        onDismissRequest = { onDismissDialog() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.select_app_language),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Column(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    options.forEach { language ->
                        SelectionItem(
                            language = language,
                            onClick = {
                                tempSelectedLanguage = language
                            },
                            isSelected = language == tempSelectedLanguage
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .weight(1F)
                            .padding(start = 16.dp)
                            .padding(bottom = 16.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(color = MaterialTheme.colorScheme.primary)
                            .clickable { onDismissDialog() },
                        content = {
                            Text(
                                text = stringResource(R.string.cancel),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 16.dp
                                    ),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center
                            )
                        },
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .weight(1F)
                            .padding(end = 16.dp)
                            .padding(bottom = 16.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(color = MaterialTheme.colorScheme.primary)
                            .clickable {
                                onLanguageSelected(tempSelectedLanguage)
                                onDismissDialog()
                            },
                        content = {
                            Text(
                                text = stringResource(R.string.save),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 16.dp
                                    ),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center
                            )
                        },
                    )
                }
            }
        }
    )
}

@Composable
fun SelectionItem(
    modifier: Modifier = Modifier,
    language: AppLanguage,
    onClick: () -> Unit,
    isSelected: Boolean,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(0.2F)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        onClick = {
            onClick()
        }
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .weight(1F)
                    .padding(vertical = 18.dp),
                text = language.languageRes.asString(),
                fontSize = 14.sp,
                style = MaterialTheme.typography.labelSmall
            )
            if (isSelected) {
                IconToggleButton(checked = true, onCheckedChange = {}) {
                    Icon(
                        painter = painterResource(R.drawable.subtract),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    AndroRATTheme {
        LanguageDialog(
            languages = AppPreview.PreviewSettings.languages,
            onDismissDialog = {},
            onLanguageSelected = {},
            selectedLanguage = AppLanguage.Russian
        )
    }
}

@Preview
@Composable
private fun PreviewDark() {
    AndroRATTheme(isDarkTheme = true) {
        LanguageDialog(
            languages = AppPreview.PreviewSettings.languages,
            onDismissDialog = {},
            onLanguageSelected = {},
            selectedLanguage = AppLanguage.Russian
        )
    }
}
