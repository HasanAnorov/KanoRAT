package com.ierusalem.androrat.features.images.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ImageViewModel: ViewModel() {

    var images by mutableStateOf(emptyList<Image>())
        private set

    var videos by mutableStateOf(emptyList<Video>())
        private set

    fun updateVideos(videos: List<Video>){
        this.videos = videos
    }

    fun updateImages(images: List<Image>){
        this.images = images
    }

}

