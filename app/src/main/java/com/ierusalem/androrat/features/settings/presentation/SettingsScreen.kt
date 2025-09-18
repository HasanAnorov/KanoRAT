package com.ierusalem.androrat.features.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.ShapeDefaults
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
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.ui.components.AndroRatAppBar
import com.ierusalem.androrat.core.ui.components.LanguageDialog
import com.ierusalem.androrat.core.ui.components.Switcher
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.core.utils.UiText
import com.ierusalem.androrat.features.settings.domain.SettingsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    uiState: SettingsState,
    eventHandler: (SettingsScreenEvents) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AndroRatAppBar(
                modifier = modifier,
                onNavIconPressed = { eventHandler(SettingsScreenEvents.NavIconClick) },
                title = {
                    Text(
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navIcon = Icons.AutoMirrored.Filled.ArrowBack
            )
        },
        // Exclude ime and navigation bar padding so this can be added by the UserInput composable
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime)
    ) { paddingValues ->
        if (uiState.languageDialogVisibility) {
            LanguageDialog(
                onDismissDialog = {
                    eventHandler(SettingsScreenEvents.OnDismissLanguageDialog)
                },
                languages = uiState.languagesList,
                onLanguageSelected = {
                    eventHandler(SettingsScreenEvents.OnLanguageChange(it))
                },
                selectedLanguage = uiState.selectedLanguage
            )
        }
        GeneralOptionsUI(
            modifier = Modifier.padding(paddingValues),
            eventHandler = eventHandler,
            uiState = uiState
        )
    }
}

@Composable
fun GeneralOptionsUI(eventHandler: (SettingsScreenEvents) -> Unit, uiState: SettingsState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 10.dp)
    ) {
        GeneralSettingsItem(
            modifier = Modifier.padding(top = 1.dp),
            iconStart = R.drawable.language,
            iconEnd = null,
            mainText = stringResource(R.string.language),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            subText = uiState.selectedLanguage.languageRes,
            onClick = {
                eventHandler(SettingsScreenEvents.LanguageCLick)
            }
        )
        GeneralSettingsItemWithSwitch(
            modifier = Modifier.padding(top = 1.dp),
            iconStart = R.drawable.color_palette,
            mainText = stringResource(R.string.app_theme),
            onClick = {
                eventHandler(SettingsScreenEvents.OnThemeChange)
            },
            switchCondition = uiState.appTheme,
            isForTheme = true
        )
        GeneralSettingsItemWithSwitch(
            modifier = Modifier.padding(top = 1.dp),
            iconStart = R.drawable.lock,
            mainText = stringResource(R.string.require_login),
            onClick = {
                eventHandler(SettingsScreenEvents.OnLoginRequiredChange)
            },
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
            switchCondition = uiState.isLoginRequired,
            isForTheme = false
        )
    }
}

@Composable
fun GeneralSettingsItem(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp),
    iconStart: Int? = null,
    iconEnd: Int? = null,
    mainText: String,
    subText: UiText? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = shape,
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 10.dp,
                        horizontal = 14.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1F),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    iconStart?.let {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(shape = ShapeDefaults.Medium)
                                .background(color = MaterialTheme.colorScheme.background.copy(0.6F))
                        ) {
                            Icon(
                                painter = painterResource(id = iconStart),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            modifier = textModifier.weight(1F),
                            color = textColor,
                            text = mainText,
                            style = MaterialTheme.typography.titleMedium
                        )
                        subText?.let {
                            Text(
                                text = subText.asString(),
                                color = MaterialTheme.colorScheme.primary.copy(0.8F),
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }
                }
                iconEnd?.let {
                    Icon(
                        painter = painterResource(id = iconEnd),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun GeneralSettingsItemWithSwitch(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp),
    iconStart: Int? = null,
    mainText: String,
    switchCondition: Boolean,
    onClick: () -> Unit,
    isForTheme:Boolean = false,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { },
        shape = shape,
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 10.dp,
                        horizontal = 14.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1F),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    iconStart?.let {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(shape = ShapeDefaults.Medium)
                                .background(color = MaterialTheme.colorScheme.background.copy(0.6F))
                        ) {
                            Icon(
                                painter = painterResource(id = iconStart),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            modifier = textModifier.weight(1F),
                            color = textColor,
                            text = mainText,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Switcher(
                            switchCondition = switchCondition,
                            size = 32.dp,
                            padding = 5.dp,
                            onClick = { onClick() },
                            isForTheme = isForTheme
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun SettingsScreenPreviewLight() {
    AndroRATTheme {
        SettingsScreen(
            modifier = Modifier,
            uiState = SettingsState(),
            eventHandler = {}
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreviewDark() {
    AndroRATTheme(isDarkTheme = true) {
        SettingsScreen(
            modifier = Modifier,
            uiState = SettingsState(),
            eventHandler = {}
        )
    }
}