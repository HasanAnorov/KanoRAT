package com.ierusalem.androrat.features.images.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ierusalem.androrat.features.home.domain.Image

class ImageViewModel: ViewModel() {

    var images by mutableStateOf(emptyList<Image>())
        private set

    fun updateImages(images: List<Image>){
        this.images = images
    }


}