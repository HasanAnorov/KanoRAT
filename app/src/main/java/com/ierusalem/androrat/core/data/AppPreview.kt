package com.ierusalem.androrat.core.data

import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.app.AppLanguage
import com.ierusalem.androrat.features.home.domain.model.Category
import com.ierusalem.androrat.features.home.domain.model.DrawerClicks
import com.ierusalem.androrat.features.home.domain.model.UserProfile

sealed class AppPreview {

    object PreviewSettings {
        val languages: List<AppLanguage> = listOf(
            AppLanguage.English,
            AppLanguage.Russian,
        )
    }

    object PreviewDrawer {
        val userProfile = UserProfile(
            username = "Ierusalem",
            avatarUrl = "https://guide-images.cdn.ifixit.com/igi/v6bM4OpwhDPjSmRd.large"
        )
        val categories: List<Category> = listOf(
            Category(R.string.andro_rtc, R.drawable.controller, DrawerClicks.NavigateToAndroRtc),
            Category(R.string.settings, R.drawable.settings_sharp, DrawerClicks.NavigateToSettings),
        )
    }
}