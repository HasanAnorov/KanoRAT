package com.ierusalem.androrat.features.home.domain.model

import android.net.Uri

data class Image(
    val id: Long,
    val uri: Uri,
    val displayName: String?,
    val mimeType: String? = null,
    val size: String? = null,
    val dateTaken: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val folderName: String? = null,
    val relativePath: String? = null,
    val author: String? = null,
    val description: String? = null,
    val orientation: Int? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val cameraModel: String? = null,
    val iso: String? = null,
    val aperture: String? = null,
    val exposureTime: String? = null
)