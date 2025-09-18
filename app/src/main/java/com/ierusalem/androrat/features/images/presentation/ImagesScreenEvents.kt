package com.ierusalem.androrat.features.images.presentation

//for now just one object for navigation, might add more events later
sealed interface ImagesScreenEvents {
    data object NavIconClick : ImagesScreenEvents
}