package com.ierusalem.androrat.features.link_phishing.presentation

sealed interface LinkPhishingEvents {
    data object OnNavIconClick: LinkPhishingEvents
}