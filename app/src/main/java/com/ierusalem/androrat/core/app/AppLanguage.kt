package com.ierusalem.androrat.core.app

import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.utils.UiText

enum class AppLanguage(val languageRes: UiText, var isSelected: Boolean) {
    English(languageRes = UiText.StringResource(R.string.english),  isSelected = false),
    Russian(languageRes = UiText.StringResource(R.string.russian), isSelected =  true),
}
