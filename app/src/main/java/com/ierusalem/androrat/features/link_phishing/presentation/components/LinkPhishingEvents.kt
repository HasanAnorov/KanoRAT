package com.ierusalem.androrat.features.link_phishing.presentation.components

sealed interface LinkPhishingEvents {
    data object OnNavIconClick: LinkPhishingEvents
}