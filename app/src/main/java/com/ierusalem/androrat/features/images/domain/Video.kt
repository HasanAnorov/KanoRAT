package com.ierusalem.androrat.features.images.domain

import android.net.Uri

data class Video(
    val id: Long,
    val uri: Uri,
    val displayName: String?,
    val mimeType: String? = null,
    val size: String? = null,
    val dateTaken: String? = null,
    val duration: Long? = null,
    val folderName: String? = null
)