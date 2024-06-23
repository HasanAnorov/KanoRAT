package com.ierusalem.androrat.features.home.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category (
    @StringRes val name:Int,
    @DrawableRes val icon:Int,
    val navigation: DrawerClicks,
)