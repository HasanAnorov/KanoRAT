package com.ierusalem.androrat.features.home.domain.model

import android.net.Uri

data class Image(
    val id: Long,
    val author: Long,
    val displayName: String,
    val data: String,
    val dataTaken: Long,
    val description: Long,
    val uri: Uri
)