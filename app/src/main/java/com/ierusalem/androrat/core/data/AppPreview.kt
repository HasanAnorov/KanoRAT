package com.ierusalem.androrat.core.data

import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.app.AppLanguage
import com.ierusalem.androrat.features.home.domain.model.Category
import com.ierusalem.androrat.features.home.domain.model.DrawerClicks
import com.ierusalem.androrat.features.home.domain.model.UserProfile
import com.ierusalem.androrat.features.rtc_features.rtc.domain.AndroRtcClient

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
            Category(R.string.kano_rtc, R.drawable.controller, DrawerClicks.NavigateToAndroRtc),
            Category(R.string.link_logs, R.drawable.link_alt, DrawerClicks.NavigateToLinkPhishing),
            Category(R.string.settings, R.drawable.settings_sharp, DrawerClicks.NavigateToSettings),
        )
    }

    object PreviewAndroRtc{
        val androRtcClients = listOf(
            AndroRtcClient(
                clientName = "Gleb",
                deviceName = "Pixel 8",
                lastOnlineTime = "7:34",
                isOnline = false,
                provider = "Weather App"
            ),
            AndroRtcClient(
                clientName = "Jack",
                deviceName = "Xiomi 10T",
                lastOnlineTime = "10:01",
                isOnline = true,
                provider = "Music app"
            ),
            AndroRtcClient(
                clientName = "Carl",
                deviceName = "Samsung A56",
                lastOnlineTime = "12:34",
                isOnline = false,
                provider = "PDF File"
            )
        )
    }

}