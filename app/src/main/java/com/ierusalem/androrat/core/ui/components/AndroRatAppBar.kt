package com.ierusalem.androrat.core.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme

/**
 * AndroChatAppBar
 *
 * @author A.H.I "andro" on 07/03/2024
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroRatAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavIconPressed: () -> Unit = { },
    navIcon: ImageVector = Icons.Default.Menu,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        actions = actions,
        title = title,
        colors = colors,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(
                onClick = onNavIconPressed,
                content = {
                    Icon(
                        imageVector = navIcon,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = null
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AndroChatAppBarPreview() {
    AndroRATTheme {
        AndroRatAppBar(title = { Text("Preview!") })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AndroChatAppBarPreviewDark() {
    AndroRATTheme(isDarkTheme = true) {
        AndroRatAppBar(title = { Text("Preview!") })
    }
}
