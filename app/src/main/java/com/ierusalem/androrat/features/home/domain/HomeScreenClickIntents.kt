package com.ierusalem.androrat.features.home.domain

sealed interface HomeScreenClickIntents {
    data object OpenDrawer:HomeScreenClickIntents
    data object NavigateToMessageFragment:HomeScreenClickIntents
    data object SaveScreenshotClick:HomeScreenClickIntents
    data object NavigateToImagesAndVideos:HomeScreenClickIntents
}