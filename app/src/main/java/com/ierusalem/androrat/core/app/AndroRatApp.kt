package com.ierusalem.androrat.core.app

import android.app.Application
import android.app.LocaleManager
import android.app.UiModeManager
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.ierusalem.androrat.core.data.preferences.DataStorePreferenceRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class AndroRatApp: Application() {

    @Inject
    lateinit var dataStorePreferenceRepository: DataStorePreferenceRepository

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()

        //enable this to prevent app working on emulators
//        val isRunningOnEmulatorDetector = EmulatorDetector.isRunningOnEmulator()
//        if (isRunningOnEmulatorDetector != null && isRunningOnEmulatorDetector) {
//            log("Emulator detected!")
//            throw IllegalStateException("Mobile Device Required!")
//        }

        GlobalScope.launch {
            dataStorePreferenceRepository.getLanguage.collect { languageCode ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    applicationContext.getSystemService(LocaleManager::class.java).applicationLocales =
                        LocaleList.forLanguageTags(languageCode)
                } else {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(languageCode)
                    )
                }
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            dataStorePreferenceRepository.getTheme.collect { isSystemInDarkMode ->
                if (isSystemInDarkMode) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        applicationContext.getSystemService(UiModeManager::class.java)
                            .setApplicationNightMode(UiModeManager.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    dataStorePreferenceRepository.setTheme(isSystemInDarkMode = true)
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        applicationContext.getSystemService(UiModeManager::class.java)
                            .setApplicationNightMode(UiModeManager.MODE_NIGHT_NO)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    dataStorePreferenceRepository.setTheme(isSystemInDarkMode = false)
                }
            }
        }
    }

}