package com.ierusalem.androrat.core.ui.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.features.home.domain.model.Category
import com.ierusalem.androrat.features.home.domain.model.DrawerClicks
import com.ierusalem.androrat.features.home.domain.model.UserProfile

/**
 * AndroChatDrawer
 *
 * @author A.H.I "andro" on 07/03/2024
 */

@Composable
fun AndroRatAppDrawer(
    drawerState: DrawerState = rememberDrawerState(initialValue = Closed),
    onDrawerItemClick: (DrawerClicks) -> Unit,
    userProfile: UserProfile,
    categories: List<Category>,
    content: @Composable () -> Unit
) {
    AndroRATTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    AndroChatDrawerContent(
                        onDrawerItemClick = onDrawerItemClick,
                        userProfile = userProfile,
                        categories = categories
                    )
                }
            },
            content = content
        )
    }
}
